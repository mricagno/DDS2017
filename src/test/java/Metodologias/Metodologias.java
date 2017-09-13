package Metodologias;

import static org.junit.Assert.*;

import org.junit.Test;

import dondeInvierto.Condicion;
import dondeInvierto.Empresa;
import dondeInvierto.MercadoBursatil;


public class Metodologias {

	static MercadoBursatil mercado = MercadoBursatil.INSTANCE;
	Empresa facebook = new Empresa("Facebook Inc.");
	
	@BeforeClass
	public static void inicializar() throws Exception {	
		mercado.init();
		
		mercado.addIndicador("Ingreso Neto", "A = BB + CC");
		mercado.addIndicador("Ingreso Neto", "A = BB -/+ CC");
		mercado.addIndicador("Indicador", "Indicador = EBITDA + FCF");
		mercado.addIndicador("Ingreso Neto","Ingreso Neto = Ingreso Neto En Operaciones Continuas + "
			+ "Ingreso Neto En Operaciones Discontinuadas");
		Condicion MayorA5 = new Condicion("MayorA5", "<", 5);
		
		
		mercado.addMetodologia("metodologia1", getValorFor(), MayorA5);
	
	}
	@Test
	

	
	public void test() {
		fail("Not yet implemented");
	}

}
