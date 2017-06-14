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
	
	public int containsCuenta(Cuenta cuenta) {
		int foundFlag = -1;
		
		for (int i = 0; i < this.getCuentas().size(); i++) {
			if (( cuenta.getTipo().equals(this.getCuentas().get(i).getTipo()) ) && 
					( cuenta.getPeriodo().equals(this.getCuentas().get(i).getPeriodo()) )) {
					foundFlag = i;
					break;
			}
		}
		
		return foundFlag;

	}
	
	public void addCuenta(Cuenta cuenta) {
		if (this.containsCuenta(cuenta) == -1) {
			this.cuentas.add(cuenta);
		} else {
			System.out.println("Ya existe una cuenta para esta empresa para el tipo y periodo especificado.");
		}
	}

}
