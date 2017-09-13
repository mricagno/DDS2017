package dondeInvierto;


public class CondicionFiltro extends Condicion {

	public CondicionFiltro(String nombre, String comparador, int valor, Indicador indicador) {
		super(nombre, comparador, valor, indicador);
		// TODO Auto-generated constructor stub
	}
	
	//Se define nuevamente evaluarCondicion para las condiciones de filtro
	@Override
	public void evaluarCondicion(Condicion condicion){
		double resultadoIndicador;
		int i=0;
		switch(condicion.getComparador()){
	
		case "<":
			for(Empresa empresa : empresas){
				for(Cuenta cuenta : empresa.getCuentas()){
					resultadoIndicador = condicion.getIndicador().getValorFor(empresa,cuenta.getPeriodoAsString());
					
					if(resultadoIndicador < condicion.getValor()){
						resultadoCondicion[i].setNombre(empresa.getNombre());
						resultadoCondicion[i].setResultadoIndicador(resultadoIndicador);
						i++;
					}					
				}			
			}
			break;
			
		case ">":
			for(Empresa empresa : empresas){
				for(Cuenta cuenta : empresa.getCuentas()){
					resultadoIndicador = condicion.getIndicador().getValorFor(empresa,cuenta.getPeriodoAsString());
					
					if(resultadoIndicador > condicion.getValor()){
						resultadoCondicion[i].setNombre(empresa.getNombre());
						resultadoCondicion[i].setResultadoIndicador(resultadoIndicador);
						i++;
					}					
				}			
			}
			break;
			
		case "==":
			double valorCondicion = (double)condicion.getValor();
			for(Empresa empresa : empresas){
				for(Cuenta cuenta : empresa.getCuentas()){
					resultadoIndicador = condicion.getIndicador().getValorFor(empresa,cuenta.getPeriodoAsString());
					
					if(resultadoIndicador == valorCondicion){
						resultadoCondicion[i].setNombre(empresa.getNombre());
						resultadoCondicion[i].setResultadoIndicador(resultadoIndicador);
						i++;
					}					
				}			
			}
			break;
		
		case "<=":
			for(Empresa empresa : empresas){
				for(Cuenta cuenta : empresa.getCuentas()){
					resultadoIndicador = condicion.getIndicador().getValorFor(empresa,cuenta.getPeriodoAsString());
					
					if(resultadoIndicador <= condicion.getValor()){
						resultadoCondicion[i].setNombre(empresa.getNombre());
						resultadoCondicion[i].setResultadoIndicador(resultadoIndicador);
						i++;
					}					
				}			
			}
			break;
		
		case ">=":
			for(Empresa empresa : empresas){
				for(Cuenta cuenta : empresa.getCuentas()){
					resultadoIndicador = condicion.getIndicador().getValorFor(empresa,cuenta.getPeriodoAsString());
					
					if(resultadoIndicador >= condicion.getValor()){
						resultadoCondicion[i].setNombre(empresa.getNombre());
						resultadoCondicion[i].setResultadoIndicador(resultadoIndicador);
						i++;
					}					
				}			
			}
			break;
	
		case "!=":
			for(Empresa empresa : empresas){
				for(Cuenta cuenta : empresa.getCuentas()){
					resultadoIndicador = condicion.getIndicador().getValorFor(empresa,cuenta.getPeriodoAsString());
					
					if(resultadoIndicador != condicion.getValor()){
						resultadoCondicion[i].setNombre(empresa.getNombre());
						resultadoCondicion[i].setResultadoIndicador(resultadoIndicador);
						i++;
					}					
				}			
			}
			break;
			
		case "filtrarAntiguedadMenor":
			for(Empresa empresa : empresas){
				if(empresa.getAntiguedad() < condicion.getValor()){
						resultadoCondicion[i].setNombre(empresa.getNombre());
						resultadoCondicion[i].setResultadoIndicador((double)empresa.getAntiguedad());
						i++;
				}			
			}
			break;
			
		case "filtrarAntiguedadMayor":
			for(Empresa empresa : empresas){
				if(empresa.getAntiguedad() > condicion.getValor()){
					resultadoCondicion[i].setNombre(empresa.getNombre());
					resultadoCondicion[i].setResultadoIndicador((double)empresa.getAntiguedad());
					i++;
				}			
			}
			break;
			
		//case "anios":
			
		}
	}
}
