package antlr4;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestIndicadorPrimerParse {

	@Test
	public void testIndicadorIngresoNeto() throws Exception {
		assertTrue(Antlr.parseString("Ingreso Neto = Ingreso Neto En Operaciones Continuas + "
				+ "Ingreso Neto En Operaciones Discontinuadas"));
	}
	
	@Test
	public void testIndicadorRetornoSobreCapitalTotal() throws Exception {
		assertTrue(Antlr.parseString("Retorno sobre capital total = (Ingreso Neto - Dividendos) "
				+ "/ Capital Total"));
	}
	
	@Test(expected = IllegalStateException.class)
	public void testFail() throws Exception {
		Antlr.parseString("AA = BB +- CC */ A");	
	}
}
