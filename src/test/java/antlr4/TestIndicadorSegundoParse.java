package antlr4;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import dondeInvierto.Empresa;
import dondeInvierto.MercadoBursatil;

import fileManagement.FileHandler;

public class TestIndicadorSegundoParse extends FileHandler {
	private MercadoBursatil mercado = MercadoBursatil.INSTANCE;
	private Empresa facebook;
	
	@Before
	public void inicializar() throws Exception {
		try {
			mercado.init();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mercado.addIndicador("Indicador de prueba", "Indicador de prueba = EBITDA + FCF", "MRICAGNO");
		facebook = mercado.getEmpresa("FACEBOOK INC.");
	}
	
	@Test
	public void testSumaComun() throws ParseException {
		double resultado = Antlr.calculate("Indicador = 20 + 50", facebook, "20161231");
		assertEquals(70.0, resultado, 0.01);
	}
	
	@Test
	public void testRestaComun() throws ParseException {
		double resultado = Antlr.calculate("Indicador = 50 - 20", facebook, "20161231");
		assertEquals(30.0, resultado, 0.01);
	}
	
	@Test
	public void testMutiplicacionComun() throws ParseException {
		double resultado = Antlr.calculate("Indicador = 10 * 2", facebook, "20161231");
		assertEquals(20.0, resultado, 0.01);
	}
	
	@Test
	public void testDivisionComun() throws ParseException {
		double resultado = Antlr.calculate("Indicador = 10 / 2", facebook, "20161231");
		assertEquals(5.0, resultado, 0.01);
	}
	
	@Test
	public void testCuentasFacebookSuma() throws ParseException {
		double resultado = Antlr.calculate("Indicador = EBITDA + FCF", facebook, "20151231");
		assertEquals(8165.99, resultado, 0.01);
	}
	
	@Test
	public void testCuentasFacebookResta() throws ParseException {
		double resultado = Antlr.calculate("Indicador = EBITDA - EBITDA", facebook, "20151231");
		assertEquals(0.0, resultado, 0.01);
	}
	
	@Test
	public void testCuentasFacebookMultiplicacion() throws ParseException {
		double resultado = Antlr.calculate("Indicador = (2 * EBITDA) - EBITDA", facebook, "20151231");
		assertEquals(8162.0, resultado, 0.01);
	}
	
	@Test
	public void testFormulaConIndicador() throws ParseException {
		double resultado = Antlr.calculate("Indicador = Indicador de prueba - EBITDA", facebook, "20151231");
		assertEquals(3.99, resultado, 0.01);
	}
}
