package antlr4;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import dondeInvierto.Empresa;
import dondeInvierto.MercadoBursatil;

public class EvalVisitor extends IndicadorBaseVisitor<Double> {
	private Map<String, Double> memoria;
	private MercadoBursatil mercado;
	private Empresa empresa;
	private String periodo;
	
	public EvalVisitor(Empresa empresa, String periodo) {
		this.memoria = new HashMap<String, Double>();
		this.mercado = MercadoBursatil.INSTANCE;
		this.empresa = empresa;
		this.periodo = periodo;
	}
	
	@Override
	public Double visitAsign(IndicadorParser.AsignContext contexto) {
		String indicador = contexto.ind().getText();
		Double valor = visit(contexto.expr());
		memoria.put(indicador, valor);
		return valor;
	}

	@Override
	public Double visitValor(IndicadorParser.ValorContext contexto) {
		double valor = 0;
		
		try {
			if (!mercado.containsIndicador(contexto.ID().getText())) { 
				if (!mercado.getEmpresa(empresa.getNombre()).
						containsCuenta(contexto.ID().getText(), periodo)) {
					throw new IllegalArgumentException("La palabra " + contexto.ID().getText() + " no identifica a una cuenta o indicador.");
				} else {
					valor = mercado.getEmpresa(empresa.getNombre()).getCuenta(contexto.ID().getText(), periodo).getValor();
				} 
			} else {
				valor = mercado.getIndicador(contexto.ID().getText()).getValorFor(empresa, periodo);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		return valor;
	}
	
	@Override
	public Double visitNum(IndicadorParser.NumContext contexto) {
		return Double.valueOf(contexto.NUMERO().getText());
	}
	
	@Override
	public Double visitMultDiv(IndicadorParser.MultDivContext contexto) {
		double izq = visit(contexto.expr(0));
		double der = visit(contexto.expr(1));
		
		if (contexto.op.getType() == IndicadorParser.MUL ) {
			return izq * der;
		} else {
			if (der == 0) {
				throw new ArithmeticException("El argumento " + contexto.expr(1).getText() + " es cero. No es posible realizar una división por cero.");
			} else {
				return izq / der;
			}		
		}
	}
	
	@Override
	public Double visitSumaResta(IndicadorParser.SumaRestaContext contexto) {
		double izq = visit(contexto.expr(0));
		double der = visit(contexto.expr(1));
		
		if (contexto.op.getType() == IndicadorParser.SUM ) {
			return izq + der;
		} else {
			return izq - der;		
		}
	}
	
	@Override
	public Double visitParentesis(IndicadorParser.ParentesisContext contexto) {
		return visit(contexto.expr());
	}

}
