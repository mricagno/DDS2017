package fileMangement;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;
import fileManagement.GSONFileParser;
import fileManagement.ImportedCuenta;

public class TestGSONFileParser extends GSONFileParser {

	List<ImportedCuenta> cuentas = readFile("C:/Users/Patito/workspace/DDS_2017-Grupo_8/files/cuentas.json");
	
	@Test
	public void TestJSONLeidoCorrectamente() {
				
		assertNotNull(cuentas);
		assertEquals(6, cuentas.size());
		
	}
	
	@Test
	public void TestCuenta1Leida() {
		
		ImportedCuenta cuenta1 = cuentas.get(0);
		
		assertEquals("Facebook Inc.", cuenta1.getNombre());
		assertEquals("FB", cuenta1.getSigla());
		assertEquals("EBITDA", cuenta1.getTipo());
		assertEquals("20151231", cuenta1.getPeriodo());
		assertEquals("8162", cuenta1.getValor());	
	}
	
	@Test
	public void TestCuenta2Leida() {
		
		ImportedCuenta cuenta2 = cuentas.get(1);
		
		assertEquals("Facebook Inc.", cuenta2.getNombre());
		assertEquals("FB", cuenta2.getSigla());
		assertEquals("EBITDA", cuenta2.getTipo());
		assertEquals("20161231", cuenta2.getPeriodo());
		assertEquals("14870", cuenta2.getValor());
	}
	
	@Test
	public void TestCuenta3Leida() {
		
		ImportedCuenta cuenta3 = cuentas.get(2);
		
		assertEquals("Facebook Inc.", cuenta3.getNombre());
		assertEquals("FB", cuenta3.getSigla());
		assertEquals("FCF", cuenta3.getTipo());
		assertEquals("20151231", cuenta3.getPeriodo());
		assertEquals("3.99", cuenta3.getValor());
	}
	
	@Test
	public void TestCuenta4Leida() {
	
		ImportedCuenta cuenta4 = cuentas.get(3);
		
		assertEquals("Tesla Inc.", cuenta4.getNombre());
		assertEquals("TSLA", cuenta4.getSigla());
		assertEquals("EBITDA", cuenta4.getTipo());
		assertEquals("20151231", cuenta4.getPeriodo());
		assertEquals("213", cuenta4.getValor());
	}
	
	@Test
	public void TestCuenta5Leida() {
		
		ImportedCuenta cuenta5 = cuentas.get(4);
		
		assertEquals("Tesla Inc.", cuenta5.getNombre());
		assertEquals("TSLA", cuenta5.getSigla());
		assertEquals("EBITDA", cuenta5.getTipo());
		assertEquals("20161231", cuenta5.getPeriodo());
		assertEquals("630", cuenta5.getValor());
	}
	
	@Test
	public void TestCuenta6Leida() {
		
		ImportedCuenta cuenta6 = cuentas.get(5);
		
		assertEquals("Twitter Inc.", cuenta6.getNombre());
		assertEquals("TWTR", cuenta6.getSigla());
		assertEquals("EBITDA", cuenta6.getTipo());
		assertEquals("20161231", cuenta6.getPeriodo());
		assertEquals("751", cuenta6.getValor());
	}
}
