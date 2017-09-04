package dondeInvierto;

import javax.persistence.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empresa")
public class Empresa {
    @Id
	private String nombre;
	@OneToMany//(mappedBy = "empresa",cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="empresa_id")
	private List<Cuenta> cuentas;
	
	public Empresa(String nombre) {
		this.nombre = nombre;
		this.cuentas = new ArrayList<Cuenta>();
	}

	@SuppressWarnings("unused")
	private Empresa() {
	}

	@Column(name = "NOMBRE")
	public String getNombre() {
		return this.nombre;
	}	
	
	public List<Cuenta> getCuentas() {
		return this.cuentas;
	}
	
	public boolean containsCuenta(String tipo, String periodo) throws ParseException {		
		return this.getCuentas().stream().
				anyMatch(c -> (tipo.equals(c.getTipo()))
						&& (periodo.equals(c.getPeriodoAsString())));
	}
	
	public Cuenta getCuenta(String tipo, String periodo) throws ParseException {
		return getCuentas().stream().
				filter(c -> (tipo.equals(c.getTipo()))
						&& (periodo.equals(c.getPeriodoAsString()))).
				findFirst().orElse(null);
	}
	
	public void addCuenta(Cuenta cuenta) throws ParseException {
		if (!this.containsCuenta(cuenta.getTipo(), cuenta.getPeriodoAsString())) {
			this.getCuentas().add(cuenta);
		}
	}
}