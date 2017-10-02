package dondeInvierto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Metodologia{
	private String nombre;
	private Set<CondicionFiltro> condicionesFiltro;
	private Set<CondicionOrdenamiento> condicionesOrdenamiento;
	private List<ResultadoCondicionado> listaFiltradaUOrdenada=new ArrayList<>();
	private List<ResultadoCondicionado> listaOrdenaUnaCondicion=new ArrayList<>();

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
		int posicion=0;
		ResultadoCondicionado resultado;

		for(CondicionFiltro condicion : metodologia.getCondicionesFiltro()){
			listaFiltradaUOrdenada=condicion.evaluarCondicion(condicion);
		}
		
		List<String> listaNombres=new ArrayList<>();
		for (int j=0;j<listaFiltradaUOrdenada.size();j++) {
			listaNombres.add(listaFiltradaUOrdenada.get(j).getNombre());
		}
		
		for(CondicionOrdenamiento condicion : metodologia.getCondicionesOrdenamiento()){		
			listaOrdenaUnaCondicion=condicion.evaluarCondicion(condicion,listaFiltradaUOrdenada);		
			for(int i=0;i<listaOrdenaUnaCondicion.size();i++) {					
				resultado=listaOrdenaUnaCondicion.get(i);				
				posicion=listaNombres.indexOf(resultado.getNombre());
				listaFiltradaUOrdenada.get(posicion).setPosicionPonderable(i);				
			}
		}
		
		Collections.sort(listaFiltradaUOrdenada);		
		System.out.println("Resultado aplicacion de metodología: ");
		for(int i=0; i<listaFiltradaUOrdenada.size();i++)			
		{
			System.out.println(i+1+"-"+listaFiltradaUOrdenada.get(i).getNombre());			
			//System.out.println(listaFiltradaUOrdenada.get(i).getPosicionPonderable());
			listaFiltradaUOrdenada.get(i).setPosicionPonderableEmpty();	
		}		
	
	}
}
	
	
	
	
