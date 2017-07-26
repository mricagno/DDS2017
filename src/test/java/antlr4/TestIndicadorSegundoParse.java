package antlr4;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import dondeInvierto.Empresa;
import dondeInvierto.MercadoBursatil;

import fileManagement.FileHandler;

public class TestIndicadorSegundoParse extends FileHandler {

	String filepath = "C:\\Users\\Patito\\workspace\\DDS_2017-Grupo_8\\files\\cuentas.json";
	MercadoBursatil mercado = MercadoBursatil.INSTANCE;
	Empresa facebook = new Empresa("Facebook Inc.");
	
	@Before
	public void inicializar() {
		mercado.init();
		mercado.addIndicador("Indicador de prueba", "Indicador de prueba = EBITDA + FCF");
	}
	
	@Test
	public void testSumaComun() throws ParseException {
		double resultado = Antlr.calculate("Indicador = 20 + 50", facebook, "20161231");
		System.out.println(resultado);
		assertEquals(70.0, resultado, 0.01);
	}
	
	@Test
	public void testRestaComun() throws ParseException {
		double resultado = Antlr.calculate("Indicador = 50 - 20", facebook, "20161231");
		System.out.println(resultado);
		assertEquals(30.0, resultado, 0.01);
	}
	
	@Test
	public void testMutiplicacionComun() throws ParseException {
		double resultado = Antlr.calculate("Indicador = 10 * 2", facebook, "20161231");
		System.out.println(resultado);
		assertEquals(20.0, resultado, 0.01);
	}
	
	@Test
	public void testDivisionComun() throws ParseException {
		double resultado = Antlr.calculate("Indicador = 10 / 2", facebook, "20161231");
		System.out.println(resultado);
		assertEquals(5.0, resultado, 0.01);
	}
	
	@Test
	public void testCuentasFacebookSuma() throws ParseException {
		double resultado = Antlr.calculate("Indicador = EBITDA + FCF", facebook, "20151231");
		System.out.println(resultado);
		assertEquals(8165.99, resultado, 0.01);
	}
	
	@Test
	public void testCuentasFacebookResta() throws ParseException {
		double resultado = Antlr.calculate("Indicador = EBITDA - EBITDA", facebook, "20151231");
		System.out.println(resultado);
		assertEquals(0.0, resultado, 0.01);
	}
	
	@Test
	public void testCuentasFacebookMultiplicacion() throws ParseException {
		double resultado = Antlr.calculate("Indicador = (2 * EBITDA) - EBITDA", facebook, "20151231");
		System.out.println(resultado);
		assertEquals(8162.0, resultado, 0.01);
	}
	
	@Test
	public void testFormulaConIndicador() throws ParseException {
		double resultado = Antlr.calculate("Indicador = Indicador de prueba - EBITDA", facebook, "20151231");
		System.out.println(resultado);
		assertEquals(3.99, resultado, 0.01);
	}
	
	/*
	@Test
	public void testFormulaConIndicador2() throws ParseException {
		mercado.addIndicador(indicadorDePrueba);
		double resultado = Antlr.calculate("Indicador2 = Indicadoreses - EBITDA", mercado, facebook, new SimpleDateFormat("yyyyMMdd").parse("20151231"));
		System.out.println(resultado);
		assertEquals(3.99, resultado, 0.01);
	}
	*/

}
