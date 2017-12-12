package dondeInvierto;

import javax.persistence.*;
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
    @Column(name = "INDICADOR_ID")
    private Long indicador_id;
    @Column(name = "EMPRESA_ID")
    private Long empresa_id;
    @Column(name = "PERIODO")
    private Date periodo;
    @Column(name = "VALOR")
    private double valor;

    public IndicadorCalculado() {
    }

    public IndicadorCalculado(Long indicador_id, Long empresa_id, String periodo, Double valor) throws ParseException {
        this.indicador_id = indicador_id;
        this.empresa_id = empresa_id;
        this.periodo = new SimpleDateFormat("yyyyMMdd").parse(periodo);
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public Long getIndicador() {
        return indicador_id;
    }

    public Long getEmpresa() {
        return empresa_id;
    }

    public Date getPeriodo() {
        return periodo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIndicador(Long indicador_id) {
        this.indicador_id = indicador_id;
    }

    public void setEmpresa(Long empresa_id) {
        this.empresa_id = empresa_id;
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

    public String getPeriodoAsString() {
        return new SimpleDateFormat("yyyyMMdd").format(this.periodo);
    }
}