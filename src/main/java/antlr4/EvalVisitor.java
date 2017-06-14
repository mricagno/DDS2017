package antlr4;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dondeInvierto.Cuenta;
import dondeInvierto.Empresa;
import dondeInvierto.MercadoBursatil;

public class EvalVisitor extends IndicadorBaseVisitor<Double> {
	private Map<String, Double> memoria;
	private MercadoBursatil mercado;
	private Empresa empresa;
	private Date periodo;
	
	public EvalVisitor(MercadoBursatil mercado, Empresa empresa, Date periodo) {
		this.memoria = new HashMap<String, Double>();
		this.mercado = mercado;
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
		double valor;
		
		int i = mercado.containsIndicador(contexto.ID().getText()); 
		if (i == -1) {
			i = mercado.getEmpresas().get(mercado.containsEmpresa(empresa)).containsCuenta(new Cuenta(contexto.ID().getText(), periodo, 0));
			 
			if (i == -1) {
				throw new IllegalArgumentException("La palabra " + contexto.ID().getText() + " no identifica a una cuenta o indicador.");
			} else {
				valor = mercado.getEmpresas().get(mercado.containsEmpresa(empresa)).getCuentas().get(i).getValor();
			} 
		} else {
			valor = mercado.getIndicadores().get(i).getValorFor(mercado, empresa, periodo);
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
