package dondeInvierto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Metodologia{
	private String nombre;
	private Set<CondicionFiltro> condicionesFiltro;
	private CondicionOrdenamiento condicionOrdenamiento;
	List<ResultadoCondicionado> listaFiltrada=new ArrayList<>();
	
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
	

	/**
	 * Calcula el valor de una metodologia para una determinada empresa, en un periodo dado.
	 */
	public void calcularMetodologia(Metodologia metodologia){
		
		System.out.println("calcular metodologia");
		
		for(CondicionFiltro condicion : metodologia.getCondicionesFiltro()){
			listaFiltrada=condicion.evaluarCondicion(condicion);
		}
		
		metodologia.getCondicionOrdenamiento().evaluarCondicion2(listaFiltrada,metodologia.getCondicionOrdenamiento());
		
		
			
		//pasar de a 1 las condiciones de filtro
		/*Iterator<CondicionFiltro> iter = metodologia.getCondicionesFiltro().iterator();
		int i = 0;
		System.out.println("condicione filtro cargadas "+ metodologia.getCondicionesFiltro().size());
		while (iter.hasNext()) {
			evaluarCondicion(iter.next());
			i++;
			if(i <= 1){
				System.out.println("calcular metodologia - condiciones filtro");
			}
		}
				
		//por ultimo pasar la condicion de ordenamiento
		evaluarCondicion(metodologia.getCondicionOrdenamiento());
		*/
		
	}
	
	
}
	
	
	
	
