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
		Antlr.parseString("Ingreso Neto = Ingreso Neto En Operaciones Continuas + "
				+ "Ingreso Neto En Operaciones Discontinuadas");
	}
	
	@Test
	public void testIndicadorRetornoSobreCapitalTotal() throws Exception {
		Antlr.parseString("Retorno sobre capital total = (Ingreso Neto - Dividendos) "
				+ "/ Capital Total");
	}
	
	@Test
	public void testFail() throws Exception {
		Antlr.parseString("AA = BB +- CC */ A");
	}

}
