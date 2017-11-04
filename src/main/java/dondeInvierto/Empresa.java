package dondeInvierto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries(value = { @NamedQuery(name = "Empresa.getAll", query = "SELECT b FROM Empresa b") })
@Table(name = "empresa")
public class Empresa implements Comparable<Empresa> {
	@Id
	@GeneratedValue
	@NotNull
	@Column(name = "ID")
	private Long id;
	@Column(name = "NOMBRE")
	private String nombre;
	@Column(name = "ANTIGUEDAD")
	private int antiguedad;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id")
	private List<Cuenta> cuentas;

	public Empresa(String nombre) {
		this.nombre = nombre;
		this.cuentas = new ArrayList<Cuenta>();//new HashSet<Cuenta>();
	}

	public Empresa() {
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getAntiguedad() {
		return this.antiguedad;
	}

	public void setAntiguedad(int antiguedad) {
		this.antiguedad = antiguedad;
	}

	// redefine criterio de comparacion para ordenamiento de arrays
	@Override
	public int compareTo(Empresa o) {
		if (antiguedad > o.antiguedad) {
			return -1;
		}
		if (antiguedad < o.antiguedad) {
			return 1;
		}
		return 0;
	}

	public List<Cuenta> getCuentas() {
		if (this.cuentas != null) {
			return this.cuentas;
		} else {
			List<Cuenta> cuenta = new ArrayList<Cuenta>();//new HashSet<Cuenta>();
			this.cuentas = cuenta;
			return this.cuentas;
		}
	}

	public Cuenta getCuenta(String tipo, String periodo) throws ParseException {
		return getCuentas().stream().filter(c -> (tipo.equals(c.getTipo())) && (periodo.equals(c.getPeriodoAsString())))
				.findFirst().orElse(null);
	}

	public boolean containsCuenta(String tipo, String periodo) throws ParseException {
		return this.getCuentas().stream()
				.anyMatch(c -> (tipo.equals(c.getTipo())) && (periodo.equals(c.getPeriodoAsString())));
	}

	public void addCuenta(Cuenta cuenta) throws ParseException {
		if (!this.containsCuenta(cuenta.getTipo(), cuenta.getPeriodoAsString())) {
			this.getCuentas().add(cuenta);
		}
	}
}