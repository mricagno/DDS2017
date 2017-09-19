package dondeInvierto;


public class CondicionFiltro extends Condicion {

	public CondicionFiltro(String nombre, String comparador, int valor, Indicador indicador) {
		super(nombre, comparador, valor, indicador);
		// TODO Auto-generated constructor stub
	}
	
	public ResultadoCondicionado[] getResultadoCondicion(){
		return this.resultadoCondicion;
	}
	
	//Se define nuevamente evaluarCondicion para las condiciones de filtro
	@Override
	public ResultadoCondicionado[] evaluarCondicion(Condicion condicion){
		double resultadoIndicador;
		System.out.println("evaluarCondicion FILTRO");
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
			return resultadoCondicion;
			
			
		case ">":
			for(Empresa empresa : empresas){
				System.out.println("FILTRO CASE EMPRESAS");
				for(Cuenta cuenta : empresa.getCuentas()){
					System.out.println("FILTRO CASE CUENTAS");
					resultadoIndicador = condicion.getIndicador().getValorFor(empresa,cuenta.getPeriodoAsString());
					
					if(resultadoIndicador > condicion.getValor()){
						resultadoCondicion[i].setNombre(empresa.getNombre());
						resultadoCondicion[i].setResultadoIndicador(resultadoIndicador);
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
						resultadoCondicion[i].setNombre(empresa.getNombre());
						resultadoCondicion[i].setResultadoIndicador(resultadoIndicador);
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
						resultadoCondicion[i].setNombre(empresa.getNombre());
						resultadoCondicion[i].setResultadoIndicador(resultadoIndicador);
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
						resultadoCondicion[i].setNombre(empresa.getNombre());
						resultadoCondicion[i].setResultadoIndicador(resultadoIndicador);
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
						resultadoCondicion[i].setNombre(empresa.getNombre());
						resultadoCondicion[i].setResultadoIndicador(resultadoIndicador);
						i++;
					}					
				}			
			}
			return resultadoCondicion;
			
		case "filtrarAntiguedadMenor":
			for(Empresa empresa : empresas){
				if(empresa.getAntiguedad() < condicion.getValor()){
						resultadoCondicion[i].setNombre(empresa.getNombre());
						resultadoCondicion[i].setResultadoIndicador((double)empresa.getAntiguedad());
						i++;
				}			
			}
			return resultadoCondicion;
			
		case "filtrarAntiguedadMayor":
			for(Empresa empresa : empresas){
				if(empresa.getAntiguedad() > condicion.getValor()){
					resultadoCondicion[i].setNombre(empresa.getNombre());
					resultadoCondicion[i].setResultadoIndicador((double)empresa.getAntiguedad());
					i++;
				}			
			}
			return resultadoCondicion;
			
		//case "anios":
			
		}
		return resultadoCondicion;
	}
}
