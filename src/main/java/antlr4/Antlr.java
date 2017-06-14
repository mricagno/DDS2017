package antlr4;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class Antlr {

	public static void parseString(String string) throws Exception {
		CharStream input = CharStreams.fromString(string); 
		IndicadorLexer lexer = new IndicadorLexer(input);
		IndicadorParser parser = new IndicadorParser(new CommonTokenStream(lexer));
		parser.removeErrorListeners();
		parser.addErrorListener(new BaseErrorListener () {
			@Override
			public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
					int charPositionInLine, String msg, RecognitionException e) {
				throw new IllegalStateException("Falló el parser en la línea " + line + " debido a: " + msg, e);
			}
		});
		try {
			parser.asign();
			System.out.println("La expresión cumple con el formato establecido.");
		} catch (IllegalStateException e) {
			System.err.println("<<ERROR>> " + e.getMessage());
			System.err.println("Se produjo un error al intentar parsear la expresión ingresada. Por favor, revísela e intente nuevamente.");
		}
	}
}
