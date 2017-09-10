package dondeInvierto;

import java.util.ArrayList;
import java.util.List;

public class Condicion {
	
	private String nombre;
	private String comparador;
	private int valor;
	
	/**
	 * Constructor de la condición.
	 */
	public Condicion(String nombre, String comparador, int valor) {
		this.nombre = nombre;
		this.comparador = comparador;
		this.valor = valor;
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
	public int getValor() {
		return this.valor;
	}	

	
	public boolean evaluarCondicion(double resultadoIndicador, Condicion condicion){
	
		boolean resultadoCondicionado=true;
		switch(condicion.getComparador()){
		
		case "<":
			resultadoCondicionado = resultadoIndicador < condicion.getValor();
			break;
			
		case ">":
			resultadoCondicionado = resultadoIndicador > condicion.getValor();
			break;
			
		case "==":
			resultadoCondicionado = resultadoIndicador == condicion.getValor();
			break;
			
		case "<=":
			resultadoCondicionado = resultadoIndicador <= condicion.getValor();
			break;
			
		case ">=":
			resultadoCondicionado = resultadoIndicador >= condicion.getValor();
			break;
		
		}
		return resultadoCondicionado;
		
	}
	
}
