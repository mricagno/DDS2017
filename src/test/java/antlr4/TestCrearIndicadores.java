package antlr4;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dondeInvierto.Empresa;
import dondeInvierto.Indicador;
import dondeInvierto.MercadoBursatil;

import fileManagement.FileHandler;

public class TestCrearIndicadores extends FileHandler {

	String filepath = "C:\\Users\\Patito\\workspace\\DDS_2017-Grupo_8\\files\\cuentas.json";
	List<Empresa> listaArchivo = new ArrayList<Empresa>();
	MercadoBursatil mercado = new MercadoBursatil(listaArchivo);
	Empresa facebook = new Empresa("Facebook Inc.", "FB");
	
	@Before
	public void init() throws Exception {
		listaArchivo = dispatchParser(readFile(filepath));	
		
		for (int i = 0; i < listaArchivo.size(); i++) {
			mercado.addCuenta(listaArchivo.get(i));
		}
		
		Indicador.crearIndicador("Ingreso Neto", "Ingreso Neto = Ingreso Neto En Operaciones Continuas + "
				+ "Ingreso Neto En Operaciones Discontinuadas", mercado);
		Indicador.crearIndicador("Retorno sobre capital total", "Retorno sobre capital total = (Ingreso Neto - Dividendos) "
				+ "/ Capital Total", mercado);
		Indicador.crearIndicador("Ingreso Neto", "A = BB + CC", mercado);
		Indicador.crearIndicador("Ingreso Neto", "A = BB -/+ CC", mercado);
		Indicador.crearIndicador("Indicador", "Indicador = EBITDA + FCF", mercado);
	}
	
	@Test
	public void testCantidadDeIndicadores() {
		assertEquals(3, mercado.getIndicadores().size());
	}
	
	@Test
	public void testCalculoIndicador() throws Exception {
		double resultado = mercado.getIndicadores().get(mercado.containsIndicador("Indicador")).getValorFor(mercado, facebook, new SimpleDateFormat("yyyyMMdd").parse("20151231"));
		assertEquals(8165.99, resultado, 0.01);
	}

}
