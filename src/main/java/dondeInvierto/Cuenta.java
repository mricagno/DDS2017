package dondeInvierto;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@NamedQueries(value = {
	    @NamedQuery(name = "Cuenta.getAll", query = "SELECT b FROM Cuenta b")
	})
@Table(name = "cuenta")
public class Cuenta {
	@Id
	@GeneratedValue
	private Long cuentaID;
	private String tipo;
	private Date periodo;
	private double valor;

	public Cuenta(String tipo, String periodo, String valor) throws ParseException {
		this.tipo = tipo;
		this.periodo = new SimpleDateFormat("yyyyMMdd").parse(periodo);
		this.valor = Double.parseDouble(valor);
	}

	public Cuenta() {
	};

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

	public Long getId() {
		return this.cuentaID;
	}

	public void setId(Long id) {
		this.cuentaID = id;
	}
	
	public void setValor(Double valor) {
		this.valor = valor;
	};

}
