package dondeInvierto;

import javax.persistence.*;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Entity
@DiscriminatorValue(value="Ordenamiento")
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
