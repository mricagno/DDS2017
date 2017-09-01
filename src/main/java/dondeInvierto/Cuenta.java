package dondeInvierto;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * La cuenta está definida por un tipo, un periodo y un valor
 */
@Entity
@Table(name = "cuenta")
public class Cuenta {
	@Id @GeneratedValue
	private int cuentaID;
	private String tipo;
	private Date periodo;
	private double valor;

	/**
	 * Constructor de la cuenta.
	 */
	public Cuenta (String tipo, String periodo, String valor) throws ParseException {
		this.tipo = tipo;
		this.periodo = new SimpleDateFormat("yyyyMMdd").parse(periodo);
		this.valor = Double.parseDouble(valor);
	}
	
	/**
	 * Devuelve el tipo de la cuenta.
	 */
	@Column(name = "TIPO")
	public String getTipo() {
		return this.tipo;
	}
	
	/**
	 * Devuelve el periodo de la cuenta.
	 */
	@Column(name = "PERIODO")
	public Date getPeriodo() {
		return this.periodo;
	}
	
	/**
	 * Devuelve el periodo de la cuenta como un string.
	 */
	public String getPeriodoAsString() {
		return new SimpleDateFormat("yyyyMMdd").format(this.periodo);
	}
	
	/**
	 * Devuelve el valor de la cuenta.
	 */
	@Column(name = "VALOR")
	public double getValor() {
		return this.valor;
	}
	
	public int getId() {
		return cuentaID;
	}
}
