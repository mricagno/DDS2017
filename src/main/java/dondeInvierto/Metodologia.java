package dondeInvierto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries(value = { @NamedQuery(name = "Metodologia.getAll", query = "SELECT b FROM Metodologia b") })
@Table(name = "metodologia")
public class Metodologia {
	@Id
	@GeneratedValue
	@NotNull
	@Column(name = "ID")
	private Long id;
	@Column(name = "NOMBRE")
	private String nombre;
	@ManyToMany(cascade=CascadeType.ALL)
	//@JoinColumn(name = "metodologia_id")
    @JoinTable(name = "metodologia_Filtro", joinColumns = @JoinColumn(name = "metodologia_id"),
    inverseJoinColumns = @JoinColumn(name = "condicionFiltro_id"))
	private Set<CondicionFiltro> condicionesFiltro;
	@ManyToMany(cascade=CascadeType.ALL)
//	@JoinColumn(name = "metodologia_id")
    @JoinTable(name = "metodologia_Ordenamiento", joinColumns = @JoinColumn(name = "metodologia_id"),
    inverseJoinColumns = @JoinColumn(name = "condicionOrdenamiento_id"))
	private Set<CondicionOrdenamiento> condicionesOrdenamiento;
	@Transient
	private List<ResultadoCondicionado> listaFiltradaUOrdenada = new ArrayList<>();
	@Transient
	private List<ResultadoCondicionado> listaOrdenaUnaCondicion = new ArrayList<>();

	public Metodologia(String nombre, Set<CondicionFiltro> condicionesFiltro,
			Set<CondicionOrdenamiento> condicionesOrdenamiento) {
		this.nombre = nombre;
		this.condicionesFiltro = condicionesFiltro;
		this.condicionesOrdenamiento = condicionesOrdenamiento;
	}

	public Metodologia() {
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
	public Set<CondicionFiltro> getCondicionesFiltro() {
		return this.condicionesFiltro;
	}

	/**
	 * Devuelve la tupla de comparado y valor a comparar de la metodologia.
	 */
	public Set<CondicionOrdenamiento> getCondicionesOrdenamiento() {
		return this.condicionesOrdenamiento;
	}
	
	/**
	 * Devuelve la tupla de comparado y valor a comparar de la metodologia.
	 */
	public void setCondicionesFiltro(Set<CondicionFiltro> condiciones) {
		this.condicionesFiltro = condiciones;
	}
	
	/**
	 * Devuelve la tupla de comparado y valor a comparar de la metodologia.
	 */
	public void setCondicionesOrdenamiento(Set<CondicionOrdenamiento> condiciones) {
		this.condicionesOrdenamiento = condiciones;
	}

	/**
	 * Calcula el valor de una metodologia para una determinada empresa, en un
	 * periodo dado.
	 */
	public void calcularMetodologia(Metodologia metodologia) {

		System.out.println("calcular metodologia");

		for (CondicionFiltro condicion : metodologia.getCondicionesFiltro()) {
			listaFiltradaUOrdenada = condicion.evaluarCondicion(condicion);
		}

		for (CondicionOrdenamiento condicion : metodologia.getCondicionesOrdenamiento()) {
			listaOrdenaUnaCondicion = condicion.evaluarCondicion(condicion, listaFiltradaUOrdenada);
			for (int i = 0; i < listaOrdenaUnaCondicion.size(); i++) {
				listaOrdenaUnaCondicion.get(i).setPosicionPonderable(i);
			}
		}

		Collections.sort(listaFiltradaUOrdenada);

		for (int i = 0; i < listaFiltradaUOrdenada.size(); i++) {
			System.out.println(listaFiltradaUOrdenada.get(i).getNombre());
			System.out.println(listaFiltradaUOrdenada.get(i).getResultadoIndicador());

		}
		// volver a poner en cero los ponderable??
	}
}