package ajax.servlet;

import java.io.*;
import java.sql.*;

import javax.naming.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;


/**
 * Controlador para ejemplo de autosugerencias
 */
public class Controlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DataSource dsbdfotogramas;
	
	private ServletContext sc;
	  
    /**
     * Constructor por defecto
     */
    public Controlador() {
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
	    	setDsBdfotogramas((DataSource) contexto.lookup(sc.getInitParameter("ds")));
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
	 * Método destructor del servlet
	 * @see Servlet#destroy()
	 */
	public void destroy() {
	    sc = null;
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
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String dato=request.getParameter("texto");
        System.out.println(dato);
        String sentenciaSQL = "select titPelicula from fotogramas where titPelicula like'"+dato+"%' order by titPelicula";
        try {
        	conBD = dsbdfotogramas.getConnection();
        	st = conBD.createStatement();
        	rs = st.executeQuery(sentenciaSQL);
        	if (rs.next())
        	{
        		out.println(rs.getString("titPelicula"));
        		System.out.println(rs.getString("titPelicula"));
        	}
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

	/**
	 * método setter que permite establecer el valor de la propiedad dsbdfotogramas
	 * @param dsbdfotogramas --> el valor que se desea guardar
	 */
	public void setDsBdfotogramas(DataSource dsbdfotogramas) {
		this.dsbdfotogramas = dsbdfotogramas;
	}

	/**
	 * método getter que permite obtenener el valor de la propiedad dsbdfotogramas
	 * @return --> el valor de dsbdfotogramas
	 */
	public DataSource getDsbdfotogramas() {
		return dsbdfotogramas;
	}

}
