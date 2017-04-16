package dondeInvierto;

import java.util.ArrayList;
import java.util.List;

public class Empresa {
	private String nombre;
	private String sigla;
	private List<Cuenta> cuentas;
	
	public Empresa(String nombre, String sigla) {
		this.nombre = nombre;
		this.sigla = sigla;
		this.cuentas = new ArrayList<Cuenta>();
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getSigla() {
		return this.sigla;
	}
	
	public List<Cuenta> getCuentas() {
		return this.cuentas;
	}
}
