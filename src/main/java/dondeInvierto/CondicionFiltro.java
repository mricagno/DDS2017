package dondeInvierto;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(value = "Filtro")
public class CondicionFiltro extends Condicion {
	public CondicionFiltro(String nombre, String comparador, double valor, Indicador indicador) {
		super(nombre, comparador, valor, indicador);
		// TODO Auto-generated constructor stub
	}

	public List<ResultadoCondicionado> getResultadoCondicion(){
		return this.resultadoCondicion;
	}
	
	//Se define nuevamente evaluarCondicion para las condiciones de filtro
	@Override
	public List<ResultadoCondicionado> evaluarCondicion(Condicion condicion){
		double resultadoIndicador;
		switch(condicion.getComparador()){	
		case "<":
			for(Empresa empresa : empresas){
				List<String> listaPeriodos=new ArrayList<>();
				for(Cuenta cuenta : empresa.getCuentas()){
					if (!listaPeriodos.contains(cuenta.getPeriodoAsString())){
						listaPeriodos.add(cuenta.getPeriodoAsString());
						resultadoIndicador = condicion.getIndicador().getValorFor(empresa,cuenta.getPeriodoAsString());					
						if(resultadoIndicador < condicion.getValor()){
							resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(),resultadoIndicador));
						}						
					}										
				}			
			}
			return resultadoCondicion;
			
			
		case ">":			
			for(Empresa empresa : empresas){
				List<String> listaPeriodos=new ArrayList<>();
				for(Cuenta cuenta : empresa.getCuentas()){
					if (!listaPeriodos.contains(cuenta.getPeriodoAsString())){
						listaPeriodos.add(cuenta.getPeriodoAsString());
						resultadoIndicador = condicion.getIndicador().getValorFor(empresa,cuenta.getPeriodoAsString());					
						if(resultadoIndicador > condicion.getValor()){
							resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(),resultadoIndicador));
						}						
					}										
				}			
			}
			return resultadoCondicion;
			
		case "==":
			double valorCondicion = (double)condicion.getValor();
			for(Empresa empresa : empresas){
				List<String> listaPeriodos=new ArrayList<>();
				for(Cuenta cuenta : empresa.getCuentas()){
					if (!listaPeriodos.contains(cuenta.getPeriodoAsString())){
						listaPeriodos.add(cuenta.getPeriodoAsString());
						resultadoIndicador = condicion.getIndicador().getValorFor(empresa,cuenta.getPeriodoAsString());					
						if(resultadoIndicador == valorCondicion){
							resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(),resultadoIndicador));
						}						
					}										
				}			
			}
			return resultadoCondicion;
		
		case "<=":
			for(Empresa empresa : empresas){
				List<String> listaPeriodos=new ArrayList<>();
				for(Cuenta cuenta : empresa.getCuentas()){
					if (!listaPeriodos.contains(cuenta.getPeriodoAsString())){
						listaPeriodos.add(cuenta.getPeriodoAsString());
						resultadoIndicador = condicion.getIndicador().getValorFor(empresa,cuenta.getPeriodoAsString());					
						if(resultadoIndicador <= condicion.getValor()){
						resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(),resultadoIndicador));
						}					
					}
				}			
			}
			return resultadoCondicion;
		
		case ">=":
			for(Empresa empresa : empresas){
				List<String> listaPeriodos=new ArrayList<>();
				for(Cuenta cuenta : empresa.getCuentas()){
					if (!listaPeriodos.contains(cuenta.getPeriodoAsString())){
						listaPeriodos.add(cuenta.getPeriodoAsString());
						resultadoIndicador = condicion.getIndicador().getValorFor(empresa,cuenta.getPeriodoAsString());					
						if(resultadoIndicador >= condicion.getValor()){
							resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(),resultadoIndicador));
						}						
					}										
				}			
			}
			return resultadoCondicion;
	
		case "!=":
			for(Empresa empresa : empresas){
				List<String> listaPeriodos=new ArrayList<>();
				for(Cuenta cuenta : empresa.getCuentas()){
					if (!listaPeriodos.contains(cuenta.getPeriodoAsString())){
						listaPeriodos.add(cuenta.getPeriodoAsString());
						resultadoIndicador = condicion.getIndicador().getValorFor(empresa,cuenta.getPeriodoAsString());					
						if(resultadoIndicador != condicion.getValor()){
							resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(),resultadoIndicador));
						}						
					}										
				}			
			}
			return resultadoCondicion;
			
		case "filtrarAntiguedadMenor":
			for(Empresa empresa : empresas){
				if(empresa.getAntiguedad() < condicion.getValor()){
					resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(),empresa.getAntiguedad()));
				}			
			}
			return resultadoCondicion;
			
		case "filtrarAntiguedadMayor":
			for(Empresa empresa : empresas){
				if(empresa.getAntiguedad() > condicion.getValor()){
					resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(),empresa.getAntiguedad()));
				}			
			}
			return resultadoCondicion;
		
		case "margenCrecienteUltimosAnios":
			double x = 0;
			for(Empresa empresa: empresas){
				int flag = 0;
				List<String> listaPeriodos=new ArrayList<>();
				//asumiendo que las cuentas ingresan ordenadas de mas nueva a mas antigua por periodo
				List<Cuenta> cuentasOrdenadasDescendientemente = (List<Cuenta>) empresa.getCuentas();
				//Collections.sort(cuentasOrdenadasDescendientemente);
				for(Cuenta cuenta : cuentasOrdenadasDescendientemente){
					if (!listaPeriodos.contains(cuenta.getPeriodoAsString())){
						listaPeriodos.add(cuenta.getPeriodoAsString());
						//resultadoIndicador = condicion.getIndicador().getValorFor(empresa,cuenta.getPeriodoAsString());					
						//if(resultadoIndicador != condicion.getValor()){
							//resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(),resultadoIndicador));
											
						 if(x == 0){
								x = condicion.getIndicador().getValorFor(empresa, cuenta.getPeriodoAsString());
							}
							else{
								if(x < condicion.getIndicador().getValorFor(empresa, cuenta.getPeriodoAsString())){
									flag = 0;
								}
								else{
							 flag = 1;
									break;
								}
							}
						 //}
					}
					
				}
				if(flag ==0){
					System.out.println("El margen de "+empresa.getNombre()+"es consistentemente creciente en los ultimos años");
				}
				else{
					System.out.println("El margen de "+empresa.getNombre()+"no es consistentemente creciente en los ultimos años");
				}
				
				
			}
			
		}
		return resultadoCondicion;
	}
}