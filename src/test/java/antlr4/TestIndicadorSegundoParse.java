package antlr4;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dondeInvierto.Empresa;
import dondeInvierto.MercadoBursatil;

import fileManagement.FileHandler;
import fileManagement.CuentaFromFile;
import fileManagement.GSONParser;

public class TestIndicadorSegundoParse extends FileHandler {

	String filepath = "C:\\Users\\Patito\\workspace\\DDS_2017-Grupo_8\\files\\cuentas.json";
	List<Empresa> listaArchivo = new ArrayList<Empresa>();
	MercadoBursatil mercado = new MercadoBursatil(listaArchivo);
	Empresa facebook = new Empresa("Facebook Inc.", "FB");
	
	@Before
	public void determineFile() {
		listaArchivo = dispatchParser(readFile(filepath));	
		for (int i = 0; i < listaArchivo.size(); i++) {
			mercado.addCuenta(listaArchivo.get(i));
		}
	}
	
	@Test
	public void testSumaComun() throws ParseException {
		double resultado = Antlr.calculate("Indicador = 20 + 50", mercado, facebook, new SimpleDateFormat("yyyyMMdd").parse("20161231"));
		System.out.println(resultado);
		assertEquals(70.0, resultado, 0.01);
	}
	
	@Test
	public void testRestaComun() throws ParseException {
		double resultado = Antlr.calculate("Indicador = 50 - 20", mercado, facebook, new SimpleDateFormat("yyyyMMdd").parse("20161231"));
		System.out.println(resultado);
		assertEquals(30.0, resultado, 0.01);
	}
	
	@Test
	public void testMutiplicacionComun() throws ParseException {
		double resultado = Antlr.calculate("Indicador = 10 * 2", mercado, facebook, new SimpleDateFormat("yyyyMMdd").parse("20161231"));
		System.out.println(resultado);
		assertEquals(20.0, resultado, 0.01);
	}
	
	@Test
	public void testDivisionComun() throws ParseException {
		double resultado = Antlr.calculate("Indicador = 10 / 2", mercado, facebook, new SimpleDateFormat("yyyyMMdd").parse("20161231"));
		System.out.println(resultado);
		assertEquals(5.0, resultado, 0.01);
	}
	
	@Test
	public void testCuentasFacebookSuma() throws ParseException {
		double resultado = Antlr.calculate("Indicador = EBITDA + FCF", mercado, facebook, new SimpleDateFormat("yyyyMMdd").parse("20151231"));
		System.out.println(resultado);
		assertEquals(8165.99, resultado, 0.01);
	}
	
	@Test
	public void testCuentasFacebookResta() throws ParseException {
		double resultado = Antlr.calculate("Indicador = EBITDA - EBITDA", mercado, facebook, new SimpleDateFormat("yyyyMMdd").parse("20151231"));
		System.out.println(resultado);
		assertEquals(0.0, resultado, 0.01);
	}
	
	@Test
	public void testCuentasFacebookMultiplicacion() throws ParseException {
		double resultado = Antlr.calculate("Indicador = (2 * EBITDA) - EBITDA", mercado, facebook, new SimpleDateFormat("yyyyMMdd").parse("20151231"));
		System.out.println(resultado);
		assertEquals(8162.0, resultado, 0.01);
	}
	

}
