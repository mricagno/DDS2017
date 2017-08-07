package dondeInvierto;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * La empresa está definida por un nombre y una lista de cuentas asociadas a esa empresa.
 */
public class Empresa {
	private String nombre;
	private List<Cuenta> cuentas;
	
	/**
	 * Constructor de la empresa.
	 */
	public Empresa(String nombre) {
		this.nombre = nombre;
		this.cuentas = new ArrayList<Cuenta>();
	}
	
	/**
	 * Devuelve el nombre de la empresa.
	 */
	public String getNombre() {
		return this.nombre;
	}	
	
	/**
	 * Devuelve la lista de cuentas de la empresa.
	 */
	public List<Cuenta> getCuentas() {
		return this.cuentas;
	}
	
	/**
	 * Devuelve true si la empresa tiene la cuenta del tipo buscado, para el periodo buscado.
	 * @throws ParseException 
	 */
	public boolean containsCuenta(String tipo, String periodo) throws ParseException {		
		return this.getCuentas().stream().
				anyMatch(c -> (tipo.equals(c.getTipo()))
						&& (periodo.equals(c.getPeriodoAsString())));
	}
	
	/**
	 * Devuelve la cuenta del tipo buscado, para el periodo buscado.
	 * @throws ParseException 
	 */
	public Cuenta getCuenta(String tipo, String periodo) throws ParseException {
		return getCuentas().stream().
				filter(c -> (tipo.equals(c.getTipo()))
						&& (periodo.equals(c.getPeriodoAsString()))).
				findFirst().orElse(null);
	}
	
	/**
	 * Agrega la cuenta en la lista de cuentas de la empresa.
	 * @throws ParseException 
	 */
	public void addCuenta(Cuenta cuenta) throws ParseException {
		if (!this.containsCuenta(cuenta.getTipo(), cuenta.getPeriodoAsString())) {
			this.getCuentas().add(cuenta);
		}
	}

}
