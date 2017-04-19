package dondeInvierto;

import java.util.Date;

public class Cuenta {
	private String tipo;
	private Date periodo;
	private double valor;

	public Cuenta(String tipo, Date periodo, double valor) {
		this.tipo = tipo;
		this.periodo = periodo;
		this.valor = valor;
	}
	
	public String getTipo() {
		return this.tipo;
	}
	
	public Date getPeriodo() {
		return this.periodo;
	}
	
	public double getValor() {
		return this.valor;
	}
}
