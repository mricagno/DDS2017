package dondeInvierto;

import javax.persistence.*;
import java.util.List;
import java.text.ParseException;

@Entity
@DiscriminatorValue(value="Filtro")
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
		System.out.println("evaluarCondicion FILTRO");
		int i=0;
		switch(condicion.getComparador()){
	
		case "<":
			for(Empresa empresa : empresas){
				for(Cuenta cuenta : empresa.getCuentas()){
					resultadoIndicador = condicion.getIndicador().getValorFor(empresa,cuenta.getPeriodoAsString());
					
					if(resultadoIndicador < condicion.getValor()){
						resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(),resultadoIndicador));
						
						i++;
					}					
				}			
			}
			return resultadoCondicion;
			
			
		case ">":
			
			for(Empresa empresa : empresas){
				System.out.println("FILTRO CASE EMPRESAS");
				for(Cuenta cuenta : empresa.getCuentas()){
					System.out.println("FILTRO CASE CUENTAS");
					resultadoIndicador = condicion.getIndicador().getValorFor(empresa,cuenta.getPeriodoAsString());
					System.out.println(" "+condicion.getIndicador().getValorFor(empresa,cuenta.getPeriodoAsString()));
					if(resultadoIndicador > condicion.getValor()){
						resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(),resultadoIndicador));
						System.out.println(" "+empresa.getNombre());
						i++;
					}					
				}			
			}
			return resultadoCondicion;
			
		case "==":
			double valorCondicion = (double)condicion.getValor();
			for(Empresa empresa : empresas){
				for(Cuenta cuenta : empresa.getCuentas()){
					resultadoIndicador = condicion.getIndicador().getValorFor(empresa,cuenta.getPeriodoAsString());
					
					if(resultadoIndicador == valorCondicion){
						resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(),resultadoIndicador));
						i++;
					}					
				}			
			}
			return resultadoCondicion;
		
		case "<=":
			for(Empresa empresa : empresas){
				for(Cuenta cuenta : empresa.getCuentas()){
					resultadoIndicador = condicion.getIndicador().getValorFor(empresa,cuenta.getPeriodoAsString());
					
					if(resultadoIndicador <= condicion.getValor()){
						resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(),resultadoIndicador));
						i++;
					}					
				}			
			}
			return resultadoCondicion;
		
		case ">=":
			for(Empresa empresa : empresas){
				for(Cuenta cuenta : empresa.getCuentas()){
					resultadoIndicador = condicion.getIndicador().getValorFor(empresa,cuenta.getPeriodoAsString());
					
					if(resultadoIndicador >= condicion.getValor()){
						resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(),resultadoIndicador));
						i++;
					}					
				}			
			}
			return resultadoCondicion;
	
		case "!=":
			for(Empresa empresa : empresas){
				for(Cuenta cuenta : empresa.getCuentas()){
					resultadoIndicador = condicion.getIndicador().getValorFor(empresa,cuenta.getPeriodoAsString());
					
					if(resultadoIndicador != condicion.getValor()){
						resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(),resultadoIndicador));
						i++;
					}					
				}			
			}
			return resultadoCondicion;
			
		case "filtrarAntiguedadMenor":
			for(Empresa empresa : empresas){
				if(empresa.getAntiguedad() < condicion.getValor()){
					resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(),empresa.getAntiguedad()));

						i++;
				}			
			}
			return resultadoCondicion;
			
		case "filtrarAntiguedadMayor":
			for(Empresa empresa : empresas){
				if(empresa.getAntiguedad() > condicion.getValor()){
					resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(),empresa.getAntiguedad()));
					i++;
				}			
			}
			return resultadoCondicion;
			
		//case "anios":
			
		}
		return resultadoCondicion;
	}

}
