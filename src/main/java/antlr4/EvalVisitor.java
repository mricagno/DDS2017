package antlr4;

import java.util.HashMap;
import java.util.Map;

public class EvalVisitor extends IndicadorBaseVisitor<Integer> {
	Map<String, Integer> memoria = new HashMap<String, Integer>();
	
	@Override
	public Integer visitAsign(IndicadorParser.AsignContext contexto) {
		String ind = contexto.ind().getText();
		int valor = visit(contexto.expr());
		memoria.put(ind, valor);
				
		return valor;
	}
	
	@Override
	public Integer visitInd(IndicadorParser.IndContext contexto) {
		Integer valor = visit(contexto.ID());
		System.out.println(valor);
		
		return 0;
	}
	
	@Override
	public Integer visitMultDiv(IndicadorParser.MultDivContext contexto) {
		int left = visit(contexto.expr(0));
		int right = visit(contexto.expr(1));
		
		return 0;
	}
}
