package dondeInvierto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class CondicionOrdenamiento extends Condicion {

	
	public CondicionOrdenamiento(String nombre, String comparador, double valor, Indicador indicador) {
		super(nombre, comparador, valor, indicador);
		// TODO Auto-generated constructor stub
	}
	
	public List<ResultadoCondicionado> getResultadoCondicion(){
		return this.resultadoCondicion;
	}	

	//Se define nuevamente evaluarCondicion para las condiciones de ordenamiento
	@Override
	public List<ResultadoCondicionado> evaluarCondicion(Condicion condicion){
		
		Collections.sort(resultadoCondicion);
		for(int i=0;i<resultadoCondicion.size();i++){
			System.out.println(resultadoCondicion);	
		}
		
		
		if(condicion.getComparador() == "descendente"){
		Collections.reverse(resultadoCondicion);
		}
		return resultadoCondicion;
	}
	public List<ResultadoCondicionado> evaluarCondicion(Condicion condicion,List<ResultadoCondicionado> resultadoCondicionado){
		
		Collections.sort(resultadoCondicionado);
	
		if(condicion.getComparador() == "descendente"){
		Collections.reverse(resultadoCondicionado);
		}
		return resultadoCondicionado;
		
	}
	public static void reverse(List<ResultadoCondicionado> resultadoCondicion) {
		if(resultadoCondicion == null || resultadoCondicion.size() <= 1){
			return;
		}
		for(int i = 0; i < resultadoCondicion.size() / 2; i++){
			ResultadoCondicionado temp = resultadoCondicion.get(i);
			resultadoCondicion.set(i, resultadoCondicion.get(resultadoCondicion.size() - 1 - i));
			resultadoCondicion.set(resultadoCondicion.size() - 1 - i, temp);
		}
	}
		
}
