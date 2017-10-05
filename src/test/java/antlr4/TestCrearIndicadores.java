package antlr4;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import dondeInvierto.Cuenta;
import dondeInvierto.Empresa;
import dondeInvierto.MercadoBursatil;

import fileManagement.FileHandler;

public class TestCrearIndicadores extends FileHandler {
	String filepath = "C:\\Users\\Patito\\workspace\\DDS_2017-Grupo_8\\files\\cuentas.json";
	List<Cuenta> listaArchivo = new ArrayList<Cuenta>();
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
		assertEquals(3, mercado.getIndicadores().size());
	}
	
	@Test
	public void testCalculoIndicador() throws Exception {		
		double resultado = mercado.getIndicador("Indicador").getValorFor(facebook, "20151231");
		assertEquals(8165.99, resultado, 0.01);
	}

}
