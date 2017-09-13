package dondeInvierto;

import java.util.Arrays;


public class CondicionOrdenamiento extends Condicion {

	public CondicionOrdenamiento(String nombre, String comparador, int valor, Indicador indicador) {
		super(nombre, comparador, valor, indicador);
		// TODO Auto-generated constructor stub
	}
	
	//Se define nuevamente evaluarCondicion para las condiciones de ordenamiento
	@Override
	public void evaluarCondicion(Condicion condicion){
		
		Arrays.sort(resultadoCondicion);
		
		if(condicion.getComparador() == "descendente"){
		reverse(resultadoCondicion);
		}
		
	}

	public static void reverse(ResultadoCondicionado[] resultadoCondicion) {
		if(resultadoCondicion == null || resultadoCondicion.length <= 1){
			return;
		}
		for(int i = 0; i < resultadoCondicion.length / 2; i++){
			ResultadoCondicionado temp = resultadoCondicion[i];
			resultadoCondicion[i] = resultadoCondicion[resultadoCondicion.length - 1 - i];
			resultadoCondicion[resultadoCondicion.length - 1 - i] = temp;
		}
	}
	

	
	
}
