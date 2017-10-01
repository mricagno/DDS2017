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
	Empresa twitter = new Empresa("Twitter Inc.");
	Empresa tesla = new Empresa("Tesla Inc.");
	
	
	@BeforeClass
	public static void inicializar() throws Exception {	
		mercado.init();
		
		mercado.addEmpresa("Facebook Inc.");
		mercado.addEmpresa("Tesla Inc.");
		mercado.addEmpresa("Twitter Inc.");
		
		mercado.addCuenta("Facebook Inc.", "EBITDA", "20151231", "8162");
		mercado.addCuenta("Facebook Inc.", "EBITDA", "20161231", "14870");
		mercado.addCuenta("Facebook Inc.", "FCF", "20151231", "3.99");
		mercado.addCuenta("Tesla Inc.", "EBITDA", "20151231", "213");
		mercado.addCuenta("Tesla Inc.", "EBITDA", "20161231", "630");
		mercado.addCuenta("Tesla Inc.", "FCF", "20151231", "230");
		mercado.addCuenta("Twitter Inc.", "EBITDA", "20151231", "751");
		mercado.addCuenta("Twitter Inc.", "FCF", "20151231", "1751");
		
		mercado.addIndicador("Ingreso Neto", "A = BB + CC");
		mercado.addIndicador("Ingreso Neto", "A = BB -/+ CC");
		mercado.addIndicador("Indicador", "Indicador = EBITDA + FCF");
		mercado.addIndicador("Ingreso Neto En Operaciones Continuas", "Ingreso Neto En Operaciones Continuas = EBITDA ");
		mercado.addIndicador("Ingreso Neto En Operaciones Discontinuadas", "Ingreso Neto En Operaciones Discontinuas = FCF");
		mercado.addIndicador("Ingreso Neto","Ingreso Neto = Ingreso Neto En Operaciones Continuas + "
				+ "Ingreso Neto En Operaciones Discontinuadas");
		mercado.addIndicador("Dividendos", "Dividendos = EBITDA - FCF");
		mercado.addIndicador("Capital Total", "Capital Total = EBITDA + FCF");
		mercado.addIndicador("Roe", "Roe = ( Ingreso Neto - Dividendos) / Capital Total");
		mercado.addIndicador("Proporcion De Deuda", "Proporcion De Deuda = Dividendos / ( Capital Total - Dividendos )");
		mercado.addIndicador("Margen", "Margen = Capital Total - Dividendos");
		mercado.addIndicador("Indicador Vacio", "Indicador Vacio = 0");
		
		
		//Indicador indicador = mercado.getIndicador("Indicador");
		//CondicionFiltro filtro = new CondicionFiltro("CondFiltroIngNeto", ">", 1.00, indicador);
		//CondicionOrdenamiento orden = new CondicionOrdenamiento("CondOrdIngNet","ascendente",0,indicador);
		
		Set<CondicionFiltro> condicionesFiltro = new HashSet<>();
		Set<CondicionOrdenamiento> condicionesOrdenamiento = new HashSet<>();
		
		CondicionFiltro filtro1 = new CondicionFiltro("CondFiltroRoe", ">", 1.00, mercado.getIndicador("Roe"));
		CondicionFiltro filtro2 = new CondicionFiltro("CondFiltroPropDeuda", ">", 1.00, mercado.getIndicador("Proporcion De Deuda"));
		CondicionFiltro filtro3 = new CondicionFiltro("CondFiltroMargen", ">", 1.00, mercado.getIndicador("Margen"));
		condicionesFiltro.add(filtro1);
		condicionesFiltro.add(filtro2);
		condicionesFiltro.add(filtro3);
		//condicionesFiltro.add(filtro4);
		
		CondicionOrdenamiento orden1 = new CondicionOrdenamiento("CondOrdAscendente","ascendente",0,mercado.getIndicador("Indicador Vacio"));
		
		condicionesOrdenamiento.add(orden1);
		
		mercado.addMetodologia("metodologia1", condicionesFiltro , condicionesOrdenamiento);
		//mercado.addMetodologia("metodologiaWarrenBuffet", condicionesFiltro , condicionesOrdenamiento);
		//Metodologia metodologia1 = new Metodologia("metodologia1", condicionesFiltro , orden);
	
	}
	
	
	@Test
		
	public void test() {
		//fail("Not yet implemented");
	/*	
		mercado.getMetodologia("metodologia1").calcularMetodologia(mercado.getMetodologia("metodologia1"));
		
		System.out.println(" "+mercado.getMetodologia("metodologia1").getCondicionOrdenamiento().getVectorCondicion().size() );
		
		//mercado.getMetodologia("metodologia1").getCondicionOrdenamiento().vectorCondicion()[1].setNombre("gonzalo");
		
		for(ResultadoCondicionado empresaResultante : mercado.getMetodologia("metodologia1").getCondicionOrdenamiento().getResultadoCondicion()){
			System.out.println(" "+empresaResultante.getNombre());	
		}
		
				
	*/
	/*	for(int i=0; i< 10; i++){
			System.out.println(" "+metodologia.getCondicionOrdenamiento().getResultadoCondicion()[i].getNombre());
		}
	*/
	
	// prueba indicadores Metodologia Warren
	//	double capitalTotalFacebook = mercado.getIndicador("Capital Total").getValorFor(mercado.getEmpresa("Facebook Inc."), "20151231");
	//	System.out.println("El capital total facebook es: "+capitalTotalFacebook);
	//	double capitalTotalTwitter = mercado.getIndicador("Capital Total").getValorFor(mercado.getEmpresa("Twitter Inc."), "20151231");
	//	System.out.println("El capitalTotalTwitter es: "+capitalTotalTwitter);
	
		
		
		
		//double x = mercado.getIndicador("Ingreso Neto").getValorFor(mercado.getEmpresa("Facebook Inc."),"20151231");
		//System.out.println(x);
		
		
	}

}
