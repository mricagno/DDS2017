package antlr4;

import static org.junit.Assert.*;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.junit.Test;

public class TestIndicadorPrimerParse {

	@Test
	public void testIndicadorIngresoNeto() throws Exception {
		CharStream input = CharStreams.fromString("Ingreso Neto = Ingreso Neto En Operaciones Continuas + "
				+ "Ingreso Neto En Operaciones Discontinuadas"); 
		IndicadorLexer lexer = new IndicadorLexer(input);
		IndicadorParser parser = new IndicadorParser(new CommonTokenStream(lexer));
		parser.addErrorListener(new BaseErrorListener () {
			@Override
			public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
					int charPositionInLine, String msg, RecognitionException e) {
				throw new IllegalStateException("Falló el parser en la línea " + line + "debido a " + msg, e);
			}
		});
		parser.asign();
	}
	
	@Test
	public void testFail() throws Exception {
		CharStream input = CharStreams.fromString("Retorno sobre capital total = (Ingreso Neto - Dividendos) "
				+ "/ Capital Total"); 
		IndicadorLexer lexer = new IndicadorLexer(input);
		IndicadorParser parser = new IndicadorParser(new CommonTokenStream(lexer));
		parser.addErrorListener(new BaseErrorListener () {
			@Override
			public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
					int charPositionInLine, String msg, RecognitionException e) {
				throw new IllegalStateException("Falló el parser en la línea " + line + "debido a " + msg, e);
			}
		});
		parser.asign();
	}

}
