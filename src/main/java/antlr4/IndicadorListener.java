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
	 * Enter a parse tree produced by the {@code parentesis}
	 * labeled alternative in {@link IndicadorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParentesis(IndicadorParser.ParentesisContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parentesis}
	 * labeled alternative in {@link IndicadorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParentesis(IndicadorParser.ParentesisContext ctx);
	/**
	 * Enter a parse tree produced by the {@code num}
	 * labeled alternative in {@link IndicadorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNum(IndicadorParser.NumContext ctx);
	/**
	 * Exit a parse tree produced by the {@code num}
	 * labeled alternative in {@link IndicadorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNum(IndicadorParser.NumContext ctx);
	/**
	 * Enter a parse tree produced by the {@code valor}
	 * labeled alternative in {@link IndicadorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterValor(IndicadorParser.ValorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code valor}
	 * labeled alternative in {@link IndicadorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitValor(IndicadorParser.ValorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SumaResta}
	 * labeled alternative in {@link IndicadorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSumaResta(IndicadorParser.SumaRestaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SumaResta}
	 * labeled alternative in {@link IndicadorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSumaResta(IndicadorParser.SumaRestaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MultDiv}
	 * labeled alternative in {@link IndicadorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMultDiv(IndicadorParser.MultDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MultDiv}
	 * labeled alternative in {@link IndicadorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMultDiv(IndicadorParser.MultDivContext ctx);
}