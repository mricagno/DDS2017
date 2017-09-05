package fileManagement;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dondeInvierto.Empresa;
import dondeInvierto.Cuenta;
import dondeInvierto.MercadoBursatil;
import fileManagement.FileHandler;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TestFileUpload extends FileHandler {
	private String filepath = ".//files//cuentas.json";

	private MercadoBursatil mercado = MercadoBursatil.INSTANCE;
	List<CuentaFromFile> listaArchivo;
	CuentaFromFile cuentaActual;

	Empresa facebook = new Empresa("Facebook Inc.");
	Empresa tesla = new Empresa("Tesla Inc.");
	Empresa twitter = new Empresa("Twitter Inc.");

	@Before
	public void loadCuentas() throws ParseException {
		listaArchivo = dispatchParser(readFile(filepath));

		for (int i = 0; i < listaArchivo.size(); i++) {
			cuentaActual = listaArchivo.get(i);

			mercado.addCuenta(cuentaActual.getNombre(), cuentaActual.getTipo(), cuentaActual.getPeriodo(),
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
	public void TestCuentasFacebook() throws ParseException {
		assertTrue(mercado.getEmpresa(facebook.getNombre()).getCuentas().size() == 3);
	}

	@Test
	public void TestEmpresaTesla() {
		assertTrue(mercado.containsEmpresa(tesla.getNombre()));
	}

	@Test
	public void TestCuentaTwitterTipo() throws ParseException {
		// assertTrue(mercado.getEmpresa(twitter.getNombre()).getCuentas().get(0).getTipo().equals("EBITDA"));
		Set<Cuenta> cuentas = new HashSet<Cuenta>();
		cuentas = mercado.getEmpresa(twitter.getNombre()).getCuentas();
		for (Iterator<Cuenta> cu = cuentas.iterator(); cu.hasNext();) {
			Cuenta f = cu.next();
			assertTrue((f.getTipo().equals("EBITDA")));
		}
	}

	@Test
	public void TestCuentaTwitterValor() throws ParseException {
		// assertEquals(mercado.getEmpresa(twitter.getNombre()).getCuentas().get(0).getValor(),
		// 751.0, 0);
		Set<Cuenta> cuentas = new HashSet<Cuenta>();
		cuentas = mercado.getEmpresa(twitter.getNombre()).getCuentas();
		for (Iterator<Cuenta> cu = cuentas.iterator(); cu.hasNext();) {
			Cuenta f = cu.next();
			assertEquals(f.getValor(), 751.0, 0);
		}
	}

}
