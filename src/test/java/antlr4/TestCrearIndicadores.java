package antlr4;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import dondeInvierto.Empresa;
import dondeInvierto.MercadoBursatil;

import fileManagement.FileHandler;

public class TestCrearIndicadores extends FileHandler {
	static MercadoBursatil mercado = MercadoBursatil.INSTANCE;
	Empresa facebook = new Empresa("Facebook Inc.");
	
	@BeforeClass
	public static void inicializar() throws Exception {	
		mercado.init();
		
		mercado.addIndicador("Ingreso Neto", "A = BB + CC","mricagno");
		mercado.addIndicador("Ingreso Neto", "A = BB -/+ CC","mricagno");
		mercado.addIndicador("Indicador", "Indicador = EBITDA + FCF","mricagno");
	}
	
	@Test
	public void testCantidadDeIndicadores() {
		assertEquals(18, mercado.getIndicadores().size());
	}
	
	@Test
	public void testCalculoIndicador() throws Exception {		
		double resultado = mercado.getIndicador("Indicador").getValorFor(facebook, "20151231");
		assertEquals(8165.99, resultado, 0.01);
	}

}
