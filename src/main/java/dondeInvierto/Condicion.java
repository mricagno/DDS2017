package dondeInvierto;

import javax.persistence.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "condicion")
@DiscriminatorColumn(name = "tipo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Condicion {
	@Id
	private String nombre;

	private String comparador;
	private double valor;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Indicador indicador;
	MercadoBursatil mercado = MercadoBursatil.INSTANCE;
	@Transient
	List<Empresa> empresas = mercado.getEmpresas();
	@Transient
	List<ResultadoCondicionado> resultadoCondicion = new ArrayList<>();
	

	// Constructor de la condición.
	public Condicion(String nombre, String comparador, double valor, Indicador indicador) {
		this.nombre = nombre;
		this.comparador = comparador;
		this.valor = valor;
		this.indicador = indicador;
	}


	public List<ResultadoCondicionado> getVectorCondicion() {
		return resultadoCondicion;
	}

	/**
	 * Devuelve el nombre de la condicion.
	 */
	@Column(name = "NOMBRE")
	public String getNombre() {
		return this.nombre;
	}

	/**
	 * Devuelve el comparador de la condicion.
	 */
	@Column(name = "COMPARADOR")
	public String getComparador() {
		return this.comparador;
	}

	/**
	 * Devuelve el valor a comparar de la condicion.
	 */
	@Column(name = "VALOR")
	public double getValor() {
		return this.valor;
	}

	/**
	 * Devuelve el valor a comparar de la condicion.
	 */
	@Column(name = "INDICADOR")
	public Indicador getIndicador() {
		return this.indicador;
	}

	public List<ResultadoCondicionado> evaluarCondicion(Condicion condicion) {
		return null;
	}

	public List<ResultadoCondicionado> getResultadoCondicion() {
		return null;
	}

}
