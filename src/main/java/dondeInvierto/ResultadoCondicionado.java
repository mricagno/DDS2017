package dondeInvierto;

public class ResultadoCondicionado implements Comparable<ResultadoCondicionado> {
		
	private String nombre;
	private double resultadoIndicador;
	
	
	/**
	 * Constructor del resultado luego de aplicar una condición.
	 */
	public ResultadoCondicionado(String nombre, double resultadoIndicador){
		this.nombre = nombre;
		this.resultadoIndicador = resultadoIndicador;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public double getResultadoIndicador() {
		return resultadoIndicador;
	}


	public void setResultadoIndicador(double resultadoIndicador) {
		this.resultadoIndicador = resultadoIndicador;
	}
	
	
	@Override	
	public int compareTo(ResultadoCondicionado o) {
		if (resultadoIndicador > o.resultadoIndicador) {
			return -1;
		}
		if (resultadoIndicador < o.resultadoIndicador) {
			return 1;
		}
		return 0;
	}

	

}