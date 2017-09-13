package dondeInvierto;
import java.util.Set;


public class Metodologia{
	private String nombre;
	private Set<CondicionFiltro> condicionesFiltro;
	private CondicionOrdenamiento condicionOrdenamiento;
	
	public Metodologia(String nombre, Set<CondicionFiltro> condicionesFiltro, CondicionOrdenamiento condicionOrdenamiento) {
		this.nombre = nombre;
		this.condicionesFiltro = condicionesFiltro;
		this.condicionOrdenamiento = condicionOrdenamiento;
	}
	
	/**
	 * Devuelve el nombre de la metodologia.
	 */
	public String getNombre() {
		return this.nombre;
	}
	
	/**
	 * Devuelve el indicador de la metodologia.
	 */
	public Set<CondicionFiltro> getCondicionesFiltro() {
		return this.condicionesFiltro;
	}
	
	/**
	 * Devuelve la tupla de comparado y valor a comparar de la metodologia.
	 */
	public CondicionOrdenamiento getCondicionOrdenamiento() {
		return this.condicionOrdenamiento;
	}
	
	
}
	










	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
