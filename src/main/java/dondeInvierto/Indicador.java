package dondeInvierto;

import javax.persistence.*;

import antlr4.Antlr;

@Entity
@NamedQueries(value = {@NamedQuery(name = "Indicador.getAll", query = "SELECT b FROM Indicador b")})
@Table(name = "indicador")
public class Indicador {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "FORMULA")
    private String formula;
    @Column(name = "CREADOR")
    private String creadoPor;


    public Indicador(String nombre, String formula, String creador) throws IllegalStateException {
        if (Antlr.parseString(formula)) {
            this.nombre = nombre;
            this.formula = formula;
            this.creadoPor = creador;
        }
    }

    public Indicador() {
    }


    public String getNombre() {
        return this.nombre;
    }

    public String getCreador() {
        return this.creadoPor;
    }

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
