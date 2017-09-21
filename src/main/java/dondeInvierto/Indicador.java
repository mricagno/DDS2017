package dondeInvierto;

import javax.persistence.*;

import antlr4.Antlr;

@Entity
@NamedQueries(value = {
	    @NamedQuery(name = "Indicador.getAll", query = "SELECT b FROM Indicador b")
	})
@Table(name = "indicador")
public class Indicador {
	@Id
	@GeneratedValue
	private Long id;
	
	private String nombre;
	private String formula;

	public Indicador(String nombre, String formula) throws IllegalStateException {
		if (Antlr.parseString(formula)) {
			this.nombre = nombre;
			this.formula = formula;
		}
	}

	@Column(name = "NOMBRE")
	public String getNombre() {
		return this.nombre;
	}

	@Column(name = "FORMULA")
	public String getFormula() {
		return this.formula;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValorFor(Empresa empresa, String periodo) {
		return Antlr.calculate(this.getFormula(), empresa, periodo);
	}
}
