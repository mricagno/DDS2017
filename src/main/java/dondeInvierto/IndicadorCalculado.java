package dondeInvierto;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@NamedQueries(value = {@NamedQuery(name = "IndicadorCalculado.getAll", query = "SELECT b FROM IndicadorCalculado b")})
@Table(name = "indicadorCalculado")
public class IndicadorCalculado {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    private Indicador indicador;
    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    private Empresa empresa;
    @Column(name = "PERIODO")
    private Date periodo;
    @Column(name = "VALOR")
    private double valor;

    public IndicadorCalculado() {
    }

    public IndicadorCalculado(Indicador indicador, Empresa empresa, String periodo, Double valor) throws ParseException {
        this.indicador = indicador;
        this.empresa = empresa;
        this.periodo = new SimpleDateFormat("yyyyMMdd").parse(periodo);
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public Indicador getIndicador() {
        return indicador;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Date getPeriodo() {
        return periodo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIndicador(Indicador indicador) {
        this.indicador = indicador;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void setPeriodo(Date periodo) {
        this.periodo = periodo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
