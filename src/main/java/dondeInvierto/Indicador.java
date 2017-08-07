package dondeInvierto;

import antlr4.Antlr;

/**
 * El indicador está definido por un nombre y la fórmula para calcularlo.
 */
public class Indicador {
	private String nombre;
	private String formula;
	
	/**
	 * Constructor del indicador. Valida que la fórmula tenga una estructura aritmética
	 * lógica; de no ser así, devuelve un error.
	 * 
	 * @throws Exception 
	 */
	public Indicador(String nombre, String formula) throws Exception {
		if (Antlr.parseString(formula)) {
			this.nombre = nombre;
			this.formula = formula;
		} else {
			System.err.println("No se ha creado el indicador.");
		}	
	}
	
	/**
	 * Devuelve el nombre del indicador.
	 */
	public String getNombre() {
		return this.nombre;
	}
	
	/**
	 * Devuelve la fórmula del indicador.
	 */
	public String getFormula() {
		return this.formula;
	}	
	
	/**
	 * Calcula el valor de un indicador para una determinada empresa, en un periodo dado.
	 */
	public Double getValorFor(Empresa empresa, String periodo) {
		return Antlr.calculate(this.getFormula(), empresa, periodo);
	}
}
