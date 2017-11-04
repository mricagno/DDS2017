package fileManagement;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dondeInvierto.Empresa;
import dondeInvierto.MercadoBursatil;
import fileManagement.FileHandler;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;


public class TestFileUpload extends FileHandler {
	private String filepath = ".//files//cuentas.json";
	
	private MercadoBursatil mercado = MercadoBursatil.INSTANCE;
	List<CuentaFromFile> listaArchivo;
	CuentaFromFile cuentaActual;
	
	Empresa facebook = new Empresa("Facebook Inc.");
	Empresa tesla = new Empresa("Tesla Inc.");
	Empresa twitter = new Empresa("Twitter Inc.");
	
	@Before
	public void loadCuentas() throws ParseException, FileNotFoundException {
		listaArchivo = dispatchParser(readFile(filepath));
		
		for (int i = 0; i < listaArchivo.size(); i++) {
			cuentaActual = listaArchivo.get(i);
			
			mercado.addCuenta(cuentaActual.getNombre(),
					cuentaActual.getTipo(),
					cuentaActual.getPeriodo(),
					cuentaActual.getValor());
		}
	}
	
	@Test
	public void TestCuentasLeidasDeArchivo() {
		assertTrue(listaArchivo.size() == 6);
	}
	
	@Test
	public void TestEmpresasEnMercado() {
		assertTrue(mercado.getEmpresas().size() == 3);
	}
	
	@Test
	public void TestEmpresaFacebook() {
		assertTrue(mercado.containsEmpresa(facebook.getNombre()));
	}
	
	@Test
	public void TestCuentasFacebook() {
		assertTrue(mercado.getEmpresa(facebook.getNombre()).getCuentas().size() == 3);
	}
	
	@Test
	public void TestEmpresaTesla() {
		assertTrue(mercado.containsEmpresa(tesla.getNombre()));
	}
	
	@Test
	public void TestCuentaTwitterTipo() {
		assertTrue(mercado.getEmpresa(twitter.getNombre()).getCuentas().get(0).getTipo().equals("EBITDA"));
	}
	
	@Test
	public void TestCuentaTwitterValor() {
		assertEquals(mercado.getEmpresa(twitter.getNombre()).getCuentas().get(0).getValor(), 751.0, 0);
	}

}