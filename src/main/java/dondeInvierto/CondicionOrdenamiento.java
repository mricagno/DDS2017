package dondeInvierto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class CondicionOrdenamiento extends Condicion {
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	LocalDate localDate = LocalDate.now();
	double resultadoIndicador;

	
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
		Calendar calendar =new GregorianCalendar();
		double contador = 0;
		
		if (condicion.getValor()==0)
		{
			String empresaNombre;
			Empresa empresa = null;
			for(int i=0;i<resultadoCondicionado.size();i++){
				empresaNombre=resultadoCondicionado.get(i).getNombre();
				empresa=mercado.getEmpresa(empresaNombre);
				for(Cuenta cuenta : empresa.getCuentas()){
						resultadoIndicador = condicion.getIndicador().getValorFor(empresa,cuenta.getPeriodoAsString());	
						contador+=resultadoIndicador;
				}
				resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(),contador));

			}
		}
		else
		{
			String empresaNombre;
			Empresa empresa;
			for(int i=0;i<resultadoCondicionado.size();i++){				
				empresaNombre=resultadoCondicionado.get(i).getNombre();
				empresa=mercado.getEmpresa(empresaNombre);
				for(Cuenta cuenta : empresa.getCuentas()){
					calendar.setTime(cuenta.getPeriodo());
					if (localDate.getYear()-calendar.get(Calendar.YEAR) <=condicion.getValor()){
						resultadoIndicador = condicion.getIndicador().getValorFor(empresa,cuenta.getPeriodoAsString());	
						contador+=resultadoIndicador;
					}
				}
				resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(),contador));
			}
		}
	
		
		Collections.sort(resultadoCondicion);
	
		if(condicion.getComparador() == "descendente"){
		Collections.reverse(resultadoCondicion);
		}
		return resultadoCondicion;
		
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
