// Generated from Indicador.g4 by ANTLR 4.7

package antlr4;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link IndicadorParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface IndicadorVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link IndicadorParser#asign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsign(IndicadorParser.AsignContext ctx);
	/**
	 * Visit a parse tree produced by {@link IndicadorParser#ind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInd(IndicadorParser.IndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parentesis}
	 * labeled alternative in {@link IndicadorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParentesis(IndicadorParser.ParentesisContext ctx);
	/**
	 * Visit a parse tree produced by the {@code num}
	 * labeled alternative in {@link IndicadorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNum(IndicadorParser.NumContext ctx);
	/**
	 * Visit a parse tree produced by the {@code valor}
	 * labeled alternative in {@link IndicadorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValor(IndicadorParser.ValorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SumaResta}
	 * labeled alternative in {@link IndicadorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSumaResta(IndicadorParser.SumaRestaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MultDiv}
	 * labeled alternative in {@link IndicadorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultDiv(IndicadorParser.MultDivContext ctx);
}