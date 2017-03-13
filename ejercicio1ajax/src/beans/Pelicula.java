package beans;
/**
 * Clase que encapsula los datos de una Pelicula de la BD
 * @author  Luis Villagomez
 */
public class Pelicula {
	// Propiedades de pelicula
	private String archivo;
	private String titPelicula;
	private int anyoEstreno;
	private String director;
	private String genero;
	
	/**
	 * Constructor por defecto
	 */
	public Pelicula(){
		this.archivo = "";
		this.titPelicula = "";
		this.anyoEstreno = 0;
		this.director = "";
		this.genero = "";
	}
	public Pelicula(String archivo, String titPelicula, int anyoEstreno, 
			String director, String genero){
		this.archivo = archivo;
		this.titPelicula = titPelicula;
		this.anyoEstreno = anyoEstreno;
		this.director = director;
		this.genero = genero;
	}
	
	/**
	 * Metodos getter y setter
	 */
	public String getArchivo() {
		return archivo;
	}
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
	public String getTitPelicula() {
		return titPelicula;
	}
	public void setTitPelicula(String titPelicula) {
		this.titPelicula = titPelicula;
	}
	public int getAnyoEstreno() {
		return anyoEstreno;
	}
	public void setAnyoEstreno(int anyoEstreno) {
		this.anyoEstreno = anyoEstreno;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	
	public String toString(){
		String cadena = "\nPELICULA: Fichero:"+this.archivo+" | titulo:"+this.titPelicula+" | Director:"+this.director+" | Año:"+this.anyoEstreno+" | Genero:"+this.genero;
		return (cadena);
	}

}
