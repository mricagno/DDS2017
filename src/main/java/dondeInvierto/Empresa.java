package dondeInvierto;

import javax.persistence.*;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "empresa")
public class Empresa {
	@Id
	@GeneratedValue
	private Long id;
	private String nombre;
	@OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Cuenta> cuentas;

	public Empresa(String nombre) {
		this.nombre = nombre;
		this.cuentas = new HashSet<Cuenta>();// new ArrayList<Cuenta>();
	}

	public Empresa() {
	};

	@Column(name = "NOMBRE")
	public String getNombre() {
		return this.nombre;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Cuenta> getCuentas() {
		if (this.cuentas != null) {
			return this.cuentas;
		} else {
			Set<Cuenta> cuenta = new HashSet<Cuenta>();// Collections.emptySet();
			this.cuentas = cuenta;
			return this.cuentas;
		}
	}

	public boolean containsCuenta(String tipo, String periodo) throws ParseException {
		return this.getCuentas().stream()
				.anyMatch(c -> (tipo.equals(c.getTipo())) && (periodo.equals(c.getPeriodoAsString())));
	}

	public Cuenta getCuenta(String tipo, String periodo) throws ParseException {
		return getCuentas().stream().filter(c -> (tipo.equals(c.getTipo())) && (periodo.equals(c.getPeriodoAsString())))
				.findFirst().orElse(null);
	}

	public void addCuenta(Cuenta cuenta) throws ParseException {
		if (!this.containsCuenta(cuenta.getTipo(), cuenta.getPeriodoAsString())) {
			this.getCuentas().add(cuenta);
		}
	}
}