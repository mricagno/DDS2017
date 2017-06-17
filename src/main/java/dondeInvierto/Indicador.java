package dondeInvierto;

import java.util.Date;

import antlr4.Antlr;

public class Indicador {
	private String nombre;
	private String formula;
	private MercadoBursatil mercado;
	
	public Indicador(String nombre, String formula, MercadoBursatil mercado) {
		this.nombre = nombre;
		this.formula = formula;
		this.mercado = mercado;
	}
	
	public static void crearIndicador(String nombre, String formula, MercadoBursatil mercado) throws Exception {
		if (Antlr.parseString(formula)) {
			mercado.addIndicador(new Indicador(nombre, formula, mercado));
		} else {
			System.err.println("No se ha creado el indicador.");
		}	
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getFormula() {
		return this.formula;
	}
	
	public MercadoBursatil getMercado() {
		return this.mercado;
	}
	
	public Double getValorFor(MercadoBursatil mercado, Empresa empresa, Date periodo) {
		return Antlr.calculate(this.getFormula(), mercado, empresa, periodo);
	}
}