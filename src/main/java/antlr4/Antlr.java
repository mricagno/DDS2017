package antlr4;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ParseTree;

import dondeInvierto.Empresa;
import dondeInvierto.MercadoBursatil;

/**
 * Contiene los métodos necesarios para analizar léxica y sintácticamente una expresión
 * y resolverla.
 */
public class Antlr {

	/**
	 * Verifica que el argumento tenga una estructura léxica y sintáctica válida.
	 */
	public static boolean parseString(String string) throws IllegalStateException {
		CharStream input = CharStreams.fromString(string); 
		IndicadorLexer lexer = new IndicadorLexer(input);
		IndicadorParser parser = new IndicadorParser(new CommonTokenStream(lexer));
		boolean result = false;
		
		parser.removeErrorListeners();
		parser.addErrorListener(new BaseErrorListener () {
			@Override
			public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
					int charPositionInLine, String msg, RecognitionException e) {
				throw new IllegalStateException("Falló el parser en la línea " + line + " debido a: " + msg, e);
			}
		});
		
		parser.asign();
		result = true;
		System.out.println("[INFO] (ANTLR) La expresión cumple con el formato establecido.");
		return result;
	}
	
	/**
	 * Reemplaza los valores en la fórmula y resuelve la expresión.
	 */
	public static Double calculate(String string, Empresa empresa, String periodo) {
		CharStream input = CharStreams.fromString(string); 
		IndicadorLexer lexer = new IndicadorLexer(input);
		IndicadorParser parser = new IndicadorParser(new CommonTokenStream(lexer));
		boolean lv_noreturn = false;
		ParseTree arbol = parser.asign();
		
		Double resultado = 0.0;
		
		if (!MercadoBursatil.INSTANCE.containsEmpresa(empresa.getNombre())) {
			System.err.println("[ERROR] (ANTLR) No existe la empresa especificada.");
		} else {
			EvalVisitor evaluador = new EvalVisitor(empresa, periodo);
			try {
				resultado = evaluador.visit(arbol);
			} catch (Exception e) {
				lv_noreturn = true;
				resultado = (double) (0);
				System.out.println(e.getMessage().toString());
			}
		}
		if (lv_noreturn == true) {
			return resultado = (double) (0);
		} else {
			return resultado;
		}

	}
}
