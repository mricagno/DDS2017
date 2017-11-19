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
		//mercado.init();
		
		mercado.addEmpresa("Facebook Inc.");
		mercado.addEmpresa("Tesla Inc.");
		mercado.addEmpresa("Twitter Inc.");
		
		mercado.getEmpresa("Facebook Inc.").setAntiguedad(15);
		mercado.getEmpresa("Tesla Inc.").setAntiguedad(5);
		mercado.getEmpresa("Twitter Inc.").setAntiguedad(11);
		
		
		mercado.addCuenta("Facebook Inc.", "EBITDA", "20151231", "8162");
		mercado.addCuenta("Facebook Inc.", "FCF", "20151231", "82");
		mercado.addCuenta("Facebook Inc.", "EBITDA", "20161231", "14870");
		mercado.addCuenta("Facebook Inc.", "FCF", "20161231", "399");
		
		mercado.addCuenta("Tesla Inc.", "EBITDA", "20151231", "213");		
		mercado.addCuenta("Tesla Inc.", "FCF", "20151231", "230");
		mercado.addCuenta("Tesla Inc.", "EBITDA", "20161231", "630");
		mercado.addCuenta("Tesla Inc.", "FCF", "20161231", "50");

		mercado.addCuenta("Twitter Inc.", "EBITDA", "20161231", "751");
		mercado.addCuenta("Twitter Inc.", "FCF", "20161231", "11");		
		mercado.addCuenta("Twitter Inc.", "EBITDA", "20151231", "8551");
		mercado.addCuenta("Twitter Inc.", "FCF", "20151231", "111");
		
		mercado.addIndicador("Ingreso Neto", "Ingreso Neto = EBITDA","DEFAULT");
		//mercado.addIndicador("Ingreso Neto", "A=BB+C");

		mercado.addIndicador("Indicador", "Indicador = EBITDA + FCF","DEFAULT");
		mercado.addIndicador("Ingreso Neto En Operaciones Continuas", "Ingreso Neto En Operaciones Continuas = EBITDA ","DEFAULT");
		mercado.addIndicador("Ingreso Neto En Operaciones Discontinuadas", "Ingreso Neto En Operaciones Discontinuas = FCF","DEFAULT");
		mercado.addIndicador("Ingreso Neto","Ingreso Neto = Ingreso Neto En Operaciones Continuas + "
				+ "Ingreso Neto En Ope0raciones Discontinuadas","DEFAULT");
		mercado.addIndicador("Dividendos", "Dividendos = EBITDA - FCF","DEFAULT");
		mercado.addIndicador("Capital Total", "Capital Total = EBITDA + FCF","DEFAULT");
		mercado.addIndicador("Roe", "Roe = ( Ingreso Neto - Dividendos) / Capital Total","DEFAULT");
		mercado.addIndicador("Proporcion De Deuda", "Proporcion De Deuda = Dividendos / ( Capital Total - Dividendos )","DEFAULT");
		mercado.addIndicador("Margen", "Margen = Capital Total - Dividendos","DEFAULT");
		mercado.addIndicador("Indicador Vacio", "Indicador Vacio = 0","DEFAULT");
		//Indicador indicador = mercado.getIndicador("Indicador");
		//CondicionFiltro filtro = new CondicionFiltro("CondFiltroIngNeto", ">", 1.00, indicador);
		//CondicionOrdenamiento orden = new CondicionOrdenamiento("CondOrdIngNet","ascendente",0,indicador);
		
		
		//condicionesFiltro.add(filtro1);
		//condicionesFiltro.add(filtro2);
		//condicionesFiltro.add(filtro3);
		//condicionesFiltro.add(filtro4);
			
//		CondicionFiltro filtroWB1 = new CondicionFiltro("CondFiltroRoe", ">", 1.00, mercado.getIndicador("Roe"));
//		CondicionFiltro filtroWB2 = new CondicionFiltro("CondFiltroPropDeuda", ">", 1.00, mercado.getIndicador("Proporcion De Deuda"));

//		condicionesOrdenamiento.add(orden1);
//		condicionesOrdenamiento.add(orden2);
				
//		mercado.addMetodologia("metodologia1", condicionesFiltro , condicionesOrdenamiento);
		//mercado.addMetodologia("metodologiaWarrenBuffet", condicionesFiltro , condicionesOrdenamiento);
		//Metodologia metodologia1 = new Metodologia("metodologia1", condicionesFiltro , orden);
		
		Set<CondicionFiltro> condicionesFiltro1 = new HashSet<>();
		Set<CondicionOrdenamiento> condicionesOrdenamiento1 = new HashSet<>();
		
		CondicionFiltro filtro10 = new CondicionFiltro("margenCrecienteUltimosAnios", "margenCrecienteUltimosAnios", 1.00, mercado.getIndicador("Margen"));
		//CondicionOrdenamiento orden10 = new CondicionOrdenamiento("CondOrdAscendente","ascendente",0,mercado.getIndicador("Indicador Vacio"));
		
		condicionesFiltro1.add(filtro10);
		//condicionesOrdenamiento1.add(orden10);
		
		mercado.addMetodologia("pruebaCondicionMargen", condicionesFiltro1, condicionesOrdenamiento1,"DEFAULT");
		
		
		// TEST METODOLOGIA WARREN BUFFET
		
				Set<CondicionFiltro> condicionesFiltroWB = new HashSet<>();
				Set<CondicionOrdenamiento> condicionesOrdenamientoWB = new HashSet<>();
				CondicionFiltro filtroWB3 = new CondicionFiltro("CondFiltroMargen", ">", 1.00, mercado.getIndicador("Margen"));
				CondicionFiltro filtroWB4 = new CondicionFiltro("CondFiltroLongevidad", "filtrarAntiguedadMayor", 10, mercado.getIndicador("Roe"));
				
				CondicionOrdenamiento ordenWB1 = new CondicionOrdenamiento("CondOrdMaximizarRoe","ascendente",10,mercado.getIndicador("Roe"));
				CondicionOrdenamiento ordenWB2 = new CondicionOrdenamiento("CondOrdMinimizarNivelDeuda","descendente",0,mercado.getIndicador("Proporcion De Deuda"));

				//CondicionFiltro condFiltroWB1 = new CondicionFiltro("ROEMejorUltimosAnios", "ROEMejorUltimosAnios", 1.00, mercado.getIndicador("Margen"));
				//ondicionOrdenamiento condOrdenamientoWB1= new CondicionOrdenamiento("CondOrdAscendente","ascendente",0,mercado.getIndicador("Indicador Vacio"));		
				
				condicionesOrdenamientoWB.add(ordenWB1);
				condicionesOrdenamientoWB.add(ordenWB2);
				condicionesFiltroWB.add(filtroWB3);
				condicionesFiltroWB.add(filtroWB4);
				
				mercado.addMetodologia("warrenBuffet", condicionesFiltroWB, condicionesOrdenamientoWB,"DEFAULT");
				
				// TEST METODOLOGIA TEST METODOLOGIA
				
						Set<CondicionFiltro> condicionesFiltroTM = new HashSet<>();
						Set<CondicionOrdenamiento> condicionesOrdenamientoTM = new HashSet<>();
						CondicionFiltro filtroTM = new CondicionFiltro("MenorQue8000", "<", 8000.00, mercado.getIndicador("Margen"));
						CondicionFiltro filtroTM2= new CondicionFiltro("CondMayorRoe", ">", 8, mercado.getIndicador("Indicador Vacio"));
						
						CondicionOrdenamiento ordenTM = new CondicionOrdenamiento("CondOrdDividendos","descendente",5,mercado.getIndicador("Dividendos"));
										
						condicionesOrdenamientoTM.add(ordenTM);
						condicionesFiltroTM.add(filtroTM);
						condicionesFiltroTM.add(filtroTM2);
						
						mercado.addMetodologia("TestMetodologia", condicionesFiltroTM, condicionesOrdenamientoTM,"DEFAULT");
						
		
	
	}
	
	
	@Test
		
	public void test() {
		
	//	mercado.getMetodologia("pruebaCondicionMargen").calcularMetodologia(mercado.getMetodologia("pruebaCondicionMargen"));
		
		mercado.getMetodologia("warrenBuffet").calcularMetodologia(mercado.getMetodologia("warrenBuffet"));
		//fail("Not yet implemented");
	
		//mercado.getMetodologia("metodologiaWarrenBuffet").calcularMetodologia(mercado.getMetodologia("metodologiaWarrenBuffet"));
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