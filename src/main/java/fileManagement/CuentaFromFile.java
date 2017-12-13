package fileManagement;

public class CuentaFromFile {
	private String nombre;
	private String sigla;
	private String tipo;
	private String periodo;
	private String valor;

	public String getNombre() {
		return this.nombre;
	}

	public String getSigla() {
		return this.sigla;
	}

	public String getTipo() {
		return this.tipo;
	}

	public String getPeriodo() {
		return this.periodo;
	}

	public String getValor() {
		return this.valor;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}
