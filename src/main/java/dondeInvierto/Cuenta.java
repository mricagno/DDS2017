package dondeInvierto;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "cuenta")
public class Cuenta {
	@Id @GeneratedValue
	private int cuentaID;
	private String tipo;
	private Date periodo;
	private double valor;
    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "empresa_id")
    //private Empresa empresa;
    
	public Cuenta (String tipo, String periodo, String valor) throws ParseException {
		this.tipo = tipo;
		this.periodo = new SimpleDateFormat("yyyyMMdd").parse(periodo);
		this.valor = Double.parseDouble(valor);
	}
	
	@Column(name = "TIPO")
	public String getTipo() {
		return this.tipo;
	}
	
	@Column(name = "PERIODO")
	public Date getPeriodo() {
		return this.periodo;
	}
	
	public String getPeriodoAsString() {
		return new SimpleDateFormat("yyyyMMdd").format(this.periodo);
	}
	
	@Column(name = "VALOR")
	public double getValor() {
		return this.valor;
	}
	
	public int getId() {
		return cuentaID;
	}
}
