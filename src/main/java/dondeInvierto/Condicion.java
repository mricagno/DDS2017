package dondeInvierto;

import java.util.ArrayList;
import java.util.List;
import dondeInvierto.CondicionFiltro;
import dondeInvierto.CondicionOrdenamiento;
import dondeInvierto.Empresa;
import dondeInvierto.Indicador;
import dondeInvierto.MercadoBursatil;
import dondeInvierto.Metodologia;

public abstract class Condicion {
	
	private String nombre;
	private String comparador;
	private double valor;
	private Indicador indicador;
	
	/**
	 * Constructor de la condición.
	 */
	public Condicion(String nombre, String comparador, double valor, Indicador indicador) {
		this.nombre = nombre;
		this.comparador = comparador;
		this.valor = valor;
		this.indicador = indicador;
	}
	
	MercadoBursatil mercado = MercadoBursatil.INSTANCE;
	
	List<Empresa> empresas = mercado.getEmpresas();
	List<ResultadoCondicionado> resultadoCondicion = new ArrayList<>();
	
	
	public List <ResultadoCondicionado> getVectorCondicion(){
		
		return resultadoCondicion;
		
	}
		
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
	public double getValor() {
		return this.valor;
	}
	
	/**
	 * Devuelve el valor a comparar de la condicion.
	 */
	public Indicador getIndicador() {
		return this.indicador;
	}

	
	public abstract List<ResultadoCondicionado> evaluarCondicion(Condicion condicion);
	
	public abstract List<ResultadoCondicionado> getResultadoCondicion();
	
}
