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
	
	
	
	
	///FIELD//
	
	/*
	public List<ResultadoCondicionado> evaluarCondicion2(List<ResultadoCondicionado> lista,Condicion condicion){
		ResultadoCondicionado elementoLista, auxiliar;
		
		for(int i=0; i<lista.size();i++)			
		{
			elementoLista=lista.get(i);
			for (int j=0; j<lista.size()-1;j++) {
				
				switch(condicion.getComparador()){				
				case "ascendente":
						if (elementoLista.getResultadoIndicador()<lista.get(j).getResultadoIndicador()) {
							auxiliar=elementoLista;
							lista.set(i, lista.get(j));
							lista.set(j, auxiliar);
						}				
					break;
				case "descendente":
					if (elementoLista.getResultadoIndicador()>lista.get(j).getResultadoIndicador()) {
						auxiliar=elementoLista;
						lista.set(i, lista.get(j));
						lista.set(j, auxiliar);
					}	
				}
				break;
			}
		}
		
		return lista;
		
	}
	*/
	
	
	
	/////
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
