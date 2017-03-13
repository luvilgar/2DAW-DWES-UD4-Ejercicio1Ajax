package ajax.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import beans.Pelicula;

/**
 * Servlet implementation class Mostrar
 */
public class Mostrar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DataSource dsbdfotogramas;
	
	private ServletContext sc;
	
    /**
     * Constructor por defecto
     */	
    public Mostrar() {
        super();
    }

	/**
	 * Método que inicializa el servlet
	 */
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    try {
	    	sc = config.getServletContext();
	    	Context contexto = new InitialContext();
	    	setDsbdfotogramas((DataSource) contexto.lookup(sc.getInitParameter("ds")));
	    	if (getDsbdfotogramas()==null)
	    		System.out.println("dsbdfotogramas es null.");
	    } 
	    catch(NamingException ne)
	    {
	        // Si no se pudiera recuperar el recurso JNDI asociado al datasource,
	        // se envia un mensaje de error hacia la salida de log del servidor
	        // de aplicaciones.
	        System.out.println("Error: Metodo init(). tipo NamingException.");
	    } 	
	}

	/**
	 * Método doGet. Trataremos todas las peticiones como POST.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * Método doPost para atender las peticiones recibidas.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Connection conBD = null;
		Statement st = null;
		ResultSet rs = null;
		String textoXML;
		String texto = request.getParameter("texto");
		
		// Si ha escrito el titulo de pelicula a buscar
		if (texto!=""){
			Pelicula pelicula = new Pelicula();
			response.setContentType("text/xml;charset=UTF-8");
			PrintWriter out = response.getWriter();
			String dato=request.getParameter("texto");
			System.out.println(dato);
			String sentenciaSQL = "select * from fotogramas where titPelicula ='"+dato+"' order by titPelicula";
			try {
				conBD = dsbdfotogramas.getConnection();
				st = conBD.createStatement();
				rs = st.executeQuery(sentenciaSQL);
				if (rs.next())
				{
					pelicula.setArchivo(rs.getString("archivo"));
					pelicula.setTitPelicula(rs.getString("titPelicula"));
					pelicula.setAnyoEstreno(rs.getInt("anyoEstreno"));
					pelicula.setDirector(rs.getString("director"));
					pelicula.setGenero(rs.getString("genero"));
        		
				}
				// Preparo la cadena XML para mostrar
				textoXML = cadenaXML(pelicula);
				out.println(textoXML);
				System.out.println(pelicula.toString());
				
			}
			catch(Exception excepcion) {
				excepcion.printStackTrace();
			}
			finally {
				if (conBD!=null)
					try {
						if(rs != null)
							rs.close();
						if(st != null)
							st.close();
						conBD.close();
					}
					catch(SQLException sqle)
					{
						System.out.println(sqle);
					}
			}
			out.close();
		}
	}
	
	/**
	 * Metodo que prepara la cadena de respuesta XML que se envia a la pagina html 
	 * @param pelicula --> datos de la película a mostrar 
	 * @return
	 */
	private String cadenaXML(Pelicula pelicula) {
		String textoXML="<?xml version='1.0'?>";
		textoXML+="<pelicula>";
		textoXML+="<titPelicula>"+pelicula.getTitPelicula()+"</titPelicula>";
		textoXML+="<director>"+pelicula.getDirector()+"</director>";
		textoXML+="<anyo>"+pelicula.getAnyoEstreno()+"</anyo>";
		textoXML+="<genero>"+pelicula.getGenero()+"</genero>";
		textoXML+="<fotograma>"+pelicula.getArchivo()+"</fotograma>";		
		textoXML+="</pelicula>";

		return textoXML;
	}

	/**
	 * método setter que permite establecer el valor de la propiedad dsbdfotogramas
	 * @param dsbdfotogramas --> el valor que se desea guardar
	 */
	public void setDsbdfotogramas(DataSource dsBdfotogramas) {
		this.dsbdfotogramas = dsBdfotogramas;
	}

	/**
	 * método getter que permite obtenener el valor de la propiedad dsbdfotogramas
	 * @return --> el valor de dsbdfotogramas
	 */
	public DataSource getDsbdfotogramas() {
		return dsbdfotogramas;
	}

}
