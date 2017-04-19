package fileManagement;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dondeInvierto.Empresa;
import dondeInvierto.MercadoBursatil;
import fileManagement.FileHandler;

import java.util.ArrayList;
import java.util.List;


public class TestFileUpload extends FileHandler {
	String filepath = "C:\\Users\\Patito\\workspace\\DDS_2017-Grupo_8\\files\\cuentas.json";
	
	List<Empresa> listaArchivo = new ArrayList<Empresa>();
	MercadoBursatil mercado = new MercadoBursatil(listaArchivo);
	
	Empresa facebook = new Empresa("Facebook Inc.", "FB");
	Empresa tesla = new Empresa("Tesla Inc.", "TSLA");
	Empresa twitter = new Empresa("Twitter Inc.", "TWTR");
	
	
	@Before
	public void determineFile() {
		listaArchivo = dispatchParser(readFile(filepath));	
		for (int i = 0; i < listaArchivo.size(); i++) {
			mercado.addCuenta(listaArchivo.get(i));
		}
	}
	
	@Test
	public void TestCuentasLeidasDeArchivo() {
		assertTrue(listaArchivo.size() == 6);
	}
	
	@Test
	public void TestCuentasEnMercado() {
		assertTrue(mercado.getEmpresas().size() == 3);
	}
	
	@Test
	public void TestEmpresaFacebook() {
		assertTrue(mercado.containsEmpresa(facebook) != -1);
	}
	
	@Test
	public void TestCuentasFacebook() {
		assertTrue(mercado.getEmpresas().get(mercado.containsEmpresa(facebook)).getCuentas().size() == 3);
	}
	
	@Test
	public void TestEmpresaTesla() {
		assertTrue(mercado.containsEmpresa(tesla) != -1);
	}
	
	@Test
	public void TestCuentaTwitterTipo() {
		assertTrue(mercado.getEmpresas().get(mercado.containsEmpresa(twitter)).getCuentas().get(0).getTipo().equals("EBITDA"));
	}
	
	@Test
	public void TestCuentaTwitterValor() {
		assertEquals(mercado.getEmpresas().get(mercado.containsEmpresa(twitter)).getCuentas().get(0).getValor(), 751.0, 0);
	}

}
