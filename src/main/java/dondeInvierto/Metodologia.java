package dondeInvierto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Metodologia{
	private String nombre;
	private Set<CondicionFiltro> condicionesFiltro;
	private Set<CondicionOrdenamiento> condicionesOrdenamiento;
	private List<ResultadoCondicionado> listaFiltradaUOrdenada=new ArrayList<>();
	private ResultadoCondicionado elementoListaFiltradaUOrdenada,auxiliar;
	
	public Metodologia(String nombre, Set<CondicionFiltro> condicionesFiltro, Set<CondicionOrdenamiento> condicionesOrdenamiento) {
		this.nombre = nombre;
		this.condicionesFiltro = condicionesFiltro;
		this.condicionesOrdenamiento = condicionesOrdenamiento;
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
	public Set<CondicionOrdenamiento> getCondicionesOrdenamiento() {
		return this.condicionesOrdenamiento;
	}
	

	/**
	 * Calcula el valor de una metodologia para una determinada empresa, en un periodo dado.
	 */
	public void calcularMetodologia(Metodologia metodologia){
		
		System.out.println("calcular metodologia");
		
		for(CondicionFiltro condicion : metodologia.getCondicionesFiltro()){
			listaFiltradaUOrdenada=condicion.evaluarCondicion(condicion);
		}
		
		for(CondicionOrdenamiento condicion : metodologia.getCondicionesOrdenamiento()){
			List<ResultadoCondicionado> listaOrdenaUnaCondicion=new ArrayList<>();
			listaOrdenaUnaCondicion=condicion.evaluarCondicion(condicion);
			for(int i=0;i<listaOrdenaUnaCondicion.size();i++) {
				listaOrdenaUnaCondicion.get(i).setPosicionPonderable(i);
			}
		}
		
		for(int i=0; i<listaFiltradaUOrdenada.size();i++)			
		{
			elementoListaFiltradaUOrdenada=listaFiltradaUOrdenada.get(i);
			
			for (int j=0; j<listaFiltradaUOrdenada.size()-1;j++) {
				if (elementoListaFiltradaUOrdenada.getPosicionPonderable()<=listaFiltradaUOrdenada.get(j).getPosicionPonderable()) {
							auxiliar=elementoListaFiltradaUOrdenada;
							listaFiltradaUOrdenada.set(i, listaFiltradaUOrdenada.get(j));
							listaFiltradaUOrdenada.set(j, auxiliar);
				}				
			}
		}		
		
		for(int i=0; i<listaFiltradaUOrdenada.size();i++)			
		{
			System.out.println(listaFiltradaUOrdenada.get(i));			
		}
	
		
		
		//metodologia.getCondicionOrdenamiento().evaluarCondicion(listaFiltradaUOrdenada,metodologia.getCondicionOrdenamiento());
			
			
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
	
	
	
	
