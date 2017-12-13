package dondeInvierto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "condicion")
@NamedQueries(value = { @NamedQuery(name = "Condicion.getAll", query = "SELECT b FROM Condicion b") })
@DiscriminatorColumn(name = "tipo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Condicion {
	@Id
	@GeneratedValue
	@NotNull
	@Column(name = "ID")
	private Long id;
	@Column(name = "NOMBRE")
	private String nombre;
	@Column(name = "COMPARADOR")
	private String comparador;
	@Column(name = "VALOR")
	private double valor;
	//@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	//private Indicador indicador;
	@Column(name = "INDICADOR")
	Long indicador;
	@Transient
	MercadoBursatil mercado = MercadoBursatil.INSTANCE;
	@Transient
	List<Empresa> empresas; //= mercado.getEmpresas();
	@Transient
	List<ResultadoCondicionado> resultadoCondicion = new ArrayList<>();
	@Column(insertable = false, updatable = false) 
	private String tipo;  //Para poder filtrar por la columna discriminadora

	// Constructor de la condición.
	public Condicion(String nombre, String comparador, double valor, Long indicador) {
		this.nombre = nombre;
		this.comparador = comparador;
		this.valor = valor;
		this.indicador = indicador;
		this.empresas = mercado.getEmpresas();
	}

	public Condicion() {
	}

	public List<ResultadoCondicionado> getVectorCondicion() {
		return resultadoCondicion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getComparador() {
		return this.comparador;
	}

	public double getValor() {
		return this.valor;
	}

	public Long getIndicador() {
		return this.indicador;
	}
	
	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}
	
	public void setResultadoCondicion(List<ResultadoCondicionado> resultadoCondicion) {
		this.resultadoCondicion = resultadoCondicion;
	}

	public List<ResultadoCondicionado> evaluarCondicion(Condicion condicion) {
		return null;
	}

	public List<ResultadoCondicionado> getResultadoCondicion() {
		return null;
	}	

}
