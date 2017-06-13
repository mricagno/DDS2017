// Generated from Indicador.g4 by ANTLR 4.7

package antlr4;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link IndicadorParser}.
 */
public interface IndicadorListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link IndicadorParser#asign}.
	 * @param ctx the parse tree
	 */
	void enterAsign(IndicadorParser.AsignContext ctx);
	/**
	 * Exit a parse tree produced by {@link IndicadorParser#asign}.
	 * @param ctx the parse tree
	 */
	void exitAsign(IndicadorParser.AsignContext ctx);
	/**
	 * Enter a parse tree produced by {@link IndicadorParser#ind}.
	 * @param ctx the parse tree
	 */
	void enterInd(IndicadorParser.IndContext ctx);
	/**
	 * Exit a parse tree produced by {@link IndicadorParser#ind}.
	 * @param ctx the parse tree
	 */
	void exitInd(IndicadorParser.IndContext ctx);
	/**
	 * Enter a parse tree produced by {@link IndicadorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(IndicadorParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link IndicadorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(IndicadorParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link IndicadorParser#val}.
	 * @param ctx the parse tree
	 */
	void enterVal(IndicadorParser.ValContext ctx);
	/**
	 * Exit a parse tree produced by {@link IndicadorParser#val}.
	 * @param ctx the parse tree
	 */
	void exitVal(IndicadorParser.ValContext ctx);
	/**
	 * Enter a parse tree produced by {@link IndicadorParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterConstant(IndicadorParser.ConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link IndicadorParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitConstant(IndicadorParser.ConstantContext ctx);
}