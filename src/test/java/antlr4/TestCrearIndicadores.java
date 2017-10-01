package antlr4;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import dondeInvierto.Cuenta;
import dondeInvierto.Empresa;
import dondeInvierto.MercadoBursatil;

public class TestCrearIndicadores {
	List<Cuenta> listaArchivo = new ArrayList<Cuenta>();
	static MercadoBursatil mercado = MercadoBursatil.INSTANCE;
	Empresa facebook = new Empresa("Facebook Inc.");
	
	@BeforeClass
	public static void inicializar() throws Exception {	
		mercado.init();
		
		mercado.addIndicador("Ingreso Neto", "A = BB + CC");
		mercado.addIndicador("Ingreso Neto", "A = BB -/+ CC");
		mercado.addIndicador("Indicador", "Indicador = EBITDA + FCF");
	}
	
	@Test
	public void testCantidadDeIndicadores() {
		assertEquals(3, mercado.getIndicadores().size());
	}
	
	@Test
	public void testCalculoIndicador() throws Exception {		
		double resultado = mercado.getIndicador("Indicador").getValorFor(facebook, "20151231");
		assertEquals(8165.99, resultado, 0.01);
	}

}
