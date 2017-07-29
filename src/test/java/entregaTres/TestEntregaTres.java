package entregaTres;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import antlr4.Antlr;
import dondeInvierto.Empresa;
import dondeInvierto.MercadoBursatil;

import fileManagement.FileHandler;

public class TestEntregaTres extends FileHandler {

	String filepath = "C:\\Usuarios\\Gon\\Escritorio\\TP DDS 2017\\DDS_2017-Grupo_8\\files\\cuentas.json";
	MercadoBursatil mercado = MercadoBursatil.INSTANCE;
	Empresa facebook = new Empresa("Facebook Inc.", 11);
	List<Empresa> empresas = MercadoBursatil.INSTANCE.getEmpresas();
	
	
	@Before
	public void inicializar() {
		mercado.init();
		mercado.addIndicador("Indicador de prueba", "Indicador de prueba = EBITDA + FCF");
		mercado.addIndicador("Ingreso Neto","Ingreso Neto = 5");
		mercado.addIndicador("Dividendos","Dividendos = 3");
		mercado.addIndicador("Capital Total", "Capital Total = 10");
		mercado.addIndicador("ROE", "ROE = ( Ingreso Neto - Dividendos ) / Capital Total");
		mercado.addIndicador("Proporcion De Deuda", "Proporcion De Deuda = Pasivo / Acciones");
	}
	
	@Test
	public void testCalcularROE() throws ParseException {
		double resultado = Antlr.calculate("ROE = ( Ingreso Neto - Dividendos ) / Capital Total", facebook, "20151231");
		System.out.println(resultado);
		assertEquals(3.99, resultado, 0.01);
	}
	
}
