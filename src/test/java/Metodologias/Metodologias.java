package Metodologias;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.BeforeClass;
import org.junit.Test;

import dondeInvierto.Condicion;
import dondeInvierto.CondicionFiltro;
import dondeInvierto.CondicionOrdenamiento;
import dondeInvierto.Empresa;
import dondeInvierto.Indicador;
import dondeInvierto.MercadoBursatil;
import dondeInvierto.Metodologia;
import dondeInvierto.ResultadoCondicionado;



public class Metodologias {

	static MercadoBursatil mercado = MercadoBursatil.INSTANCE;
	Empresa facebook = new Empresa("Facebook Inc.");
	
	
	@BeforeClass
	public static void inicializar() throws Exception {	
		//mercado.init();
		
		mercado.addEmpresa("Facebook Inc.");
		mercado.addEmpresa("Tesla Inc.");
		mercado.addEmpresa("Twitter Inc.");
		
		mercado.addCuenta("Facebook Inc.", "EBITDA", "20151231", "8162");
		mercado.addCuenta("Facebook Inc.", "FCF", "20151231", "3.99");
		
		mercado.addCuenta("Facebook Inc.", "EBITDA", "20161231", "14870");
		mercado.addCuenta("Facebook Inc.", "FCF", "20161231", "170");
		
		mercado.addCuenta("Tesla Inc.", "EBITDA", "20151231", "213");
		mercado.addCuenta("Tesla Inc.", "FCF", "20151231", "230");
		
		mercado.addCuenta("Tesla Inc.", "EBITDA", "20161231", "630");
		mercado.addCuenta("Tesla Inc.", "FCF", "20161231", "6340");

		
		mercado.addCuenta("Twitter Inc.", "EBITDA", "20151231", "751");
		mercado.addCuenta("Twitter Inc.", "FCF", "20151231", "1751");
		
		//mercado.addIndicador("Ingreso Neto", "A = BB + CC");
		//mercado.addIndicador("Ingreso Neto", "A = BB -/+ CC");
		mercado.addIndicador("Indicador", "Indicador = EBITDA + FCF");
				
		//mercado.addIndicador("Ingreso Neto", "Ingreso Neto= ");
		//mercado.addIndicador("Ingreso Neto","Ingreso Neto = Ingreso Neto En Operaciones Continuas + " + "Ingreso Neto En Operaciones Discontinuadas");
		
		Indicador indicador = mercado.getIndicador("Indicador");
		CondicionFiltro filtro = new CondicionFiltro("CondFiltroIngNeto", ">", 1.00, indicador);
		
		CondicionOrdenamiento orden = new CondicionOrdenamiento("CondOrdIngNet","ascendente",0,indicador);
		
		
		
		
		Set<CondicionFiltro> condicionesFiltro = new HashSet<>();
		Set<CondicionOrdenamiento> condicionesOrden = new HashSet<>();

		condicionesFiltro.add(filtro);
		condicionesOrden.add(orden);
		
		mercado.addMetodologia("metodologia1", condicionesFiltro , condicionesOrden);
		//Metodologia metodologia1 = new Metodologia("metodologia1", condicionesFiltro , orden);
		
		
	
	}
	
	
	@Test
	public void test() {
		//fail("Not yet implemented");
		
		Metodologia metodologia=mercado.getMetodologia("metodologia1");
		metodologia.calcularMetodologia(mercado.getMetodologia("metodologia1"));
		
		
		//System.out.println(" "+mercado.getMetodologia("metodologia1").getCondicionOrdenamiento().getVectorCondicion().size() );
		
		//mercado.getMetodologia("metodologia1").getCondicionOrdenamiento().vectorCondicion()[1].setNombre("gonzalo");
		
		//for(ResultadoCondicionado empresaResultante : mercado.getMetodologia("metodologia1").getCondicionOrdenamiento().getResultadoCondicion()){
		//	System.out.println(" "+empresaResultante.getNombre());	
		//}
		
				
	/*	for(int i=0; i< 10; i++){
			System.out.println(" "+metodologia.getCondicionOrdenamiento().getResultadoCondicion()[i].getNombre());
		}
	*/
		
		
		
		
		//double x = mercado.getIndicador("Ingreso Neto").getValorFor(mercado.getEmpresa("Facebook Inc."),"20151231");
		//System.out.println(x);
		
		
	}

}