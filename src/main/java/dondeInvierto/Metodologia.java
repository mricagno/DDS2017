package dondeInvierto;
import java.lang.Math;
import java.util.Arrays;

import antlr4.Antlr;


public class Metodologia{
	private String nombre;
	private Indicador indicador;
	private Condicion condicion;
	
	public Metodologia(String nombre, Indicador indicador, Condicion condicion) {
		this.nombre = nombre;
		this.indicador = indicador;
		this.condicion = condicion;
	}
	
	/**
	 * Devuelve el nombre de la metodologia.
	 */
	public String getNombre() {
		return this.nombre;
	}
	
	/**
	 * Devuelve el indicador de la metodologia.
	 */
	public Indicador getIndicador() {
		return this.indicador;
	}
	
	/**
	 * Devuelve la tupla de comparado y valor a comparar de la metodologia.
	 */
	public Condicion getCondicion() {
		return this.condicion;
	}
	
	
}
	














/**
 * Calcula el valor de una metodologia para una determinada empresa, en un periodo dado.

public Double getValorFor(Empresa empresa, String periodo) {
	return Antlr.calculate(this.getFormula(), empresa, periodo);
}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*private String nombre;
	private String rutaRegla;
	
	public Metodologia(String nombre, String rutaRegla) {
		this.nombre = nombre;
		this.rutaRegla = rutaRegla;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public String getRutaRegla() {
		return this.rutaRegla;
	}
	
	public void evaluarMetodologia(MercadoBursatil mercado) {
		KieServices kieServices = KieServices.Factory.get();
		KieContainer kContainer = kieServices.getKieClasspathContainer();
		
		KieSession kSession = kContainer.newKieSession();
				
		Indicador indicador = new Indicador("Prueba", "1+2", mercado);
	
		kSession.fireAllRules();

	}
}*/
