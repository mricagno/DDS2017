package dondeInvierto;

import java.util.List;

public class Condicion {
	
	private String nombre;
	private String comparador;
	private int valor;
	private Indicador indicador;
	
	/**
	 * Constructor de la condición.
	 */
	public Condicion(String nombre, String comparador, int valor, Indicador indicador) {
		this.nombre = nombre;
		this.comparador = comparador;
		this.valor = valor;
		this.indicador = indicador;
	}
	
	MercadoBursatil mercado = MercadoBursatil.INSTANCE;
	
	List<Empresa> empresas = mercado.getEmpresas();
	public ResultadoCondicionado[] resultadoCondicion = new ResultadoCondicionado[mercado.getEmpresas().size()];
		
	/**
	 * Devuelve el nombre de la condicion.
	 */
	
	public String getNombre(){
		return this.nombre;
	}
	/**
	 * Devuelve el comparador de la condicion.
	 */
	public String getComparador() {
		return this.comparador;
	}	
	
	/**
	 * Devuelve el valor a comparar de la condicion.
	 */
	public int getValor() {
		return this.valor;
	}
	
	/**
	 * Devuelve el valor a comparar de la condicion.
	 */
	public Indicador getIndicador() {
		return this.indicador;
	}

	
	public void evaluarCondicion(Condicion condicion){
	}
	
}
