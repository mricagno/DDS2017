package Metodologias;

import static org.junit.Assert.*;

import java.util.*;

import db.*;
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

import javax.persistence.EntityManager;


public class Metodologias {
	static MercadoBursatil mercado = MercadoBursatil.INSTANCE;

	Empresa facebook = new Empresa("Facebook Inc.");
	Empresa twitter = new Empresa("Twitter Inc.");
	Empresa tesla = new Empresa("Tesla Inc.");


	@BeforeClass
	public static void inicializar() throws Exception {
		DB_Manager DBManager = DB_Manager.getSingletonInstance();
		EntityManager em = DBManager.getEmf().createEntityManager();
		mercado.setFactory(DBManager.getEmf());
		MercadoBursatilService modelService = new MercadoBursatilService(em);

		EmpresaService empresa = new EmpresaService(em);
		CuentaService cuenta = new CuentaService(em);
		IndicadorService indicador = new IndicadorService(em);
		UsuarioService usuario = new UsuarioService(em);
		MetodologiaService metodologia = new MetodologiaService(em);
		usuario.addUsuario("gonzalo", "gonzalo", 0);
		usuario.addUsuario("patricio", "patricio", 0);
		usuario.addUsuario("gian", "gian", 0);
		usuario.addUsuario("maxi", "maxi", 0);
		empresa.addEmpresa("Facebook Inc.");
		empresa.addEmpresa("Tesla Inc.");
		empresa.addEmpresa("Twitter Inc.");
		mercado.setEmpresas(empresa.listEmpresas());
		mercado.getEmpresa("Facebook Inc.").setAntiguedad(15);
		mercado.getEmpresa("Tesla Inc.").setAntiguedad(5);
		mercado.getEmpresa("Twitter Inc.").setAntiguedad(11);
		Empresa facebook = empresa.getEmpresa_name("Facebook Inc.");
		cuenta.addCuenta("EBITDA", "20151231", "8162", facebook);
		cuenta.addCuenta("EBITDA", "20161231", "14870", facebook);
		cuenta.addCuenta("FCF", "20151231", "3.99", facebook);
		Empresa tesla = empresa.getEmpresa_name("Tesla Inc.");
		cuenta.addCuenta("EBITDA", "20151231", "213", tesla);
		cuenta.addCuenta("EBITDA", "20161231", "630", tesla);
		cuenta.addCuenta("FCF", "20151231", "230", tesla);
		Empresa twitter = empresa.getEmpresa_name("Twitter Inc.");
		cuenta.addCuenta("EBITDA", "20161231", "751", twitter);
		cuenta.addCuenta("FCF", "20151231", "1751", twitter);
		indicador.addIndicador(new Indicador("Ingreso Neto", "Ingreso Neto = Ingreso Neto En Operaciones Continuas + "
				+ "Ingreso Neto En Operaciones Discontinuadas", "DEFAULT"));
		indicador.addIndicador(new Indicador("Retorno sobre capital total",
				"Retorno sobre capital total = (Ingreso Neto - Dividendos) " + "/ Capital Total", "DEFAULT"));
		indicador.addIndicador(new Indicador("Indicador", "Indicador = EBITDA + FCF", "DEFAULT"));
		indicador.addIndicador(new Indicador("Ingreso Neto En Operaciones Continuas",
				"Ingreso Neto En Operaciones Continuas = EBITDA ", "DEFAULT"));
		indicador.addIndicador(new Indicador("Ingreso Neto En Operaciones Discontinuadas",
				"Ingreso Neto En Operaciones Discontinuas = FCF", "DEFAULT"));
		indicador.addIndicador(new Indicador("Dividendos", "Dividendos = EBITDA - FCF", "DEFAULT"));
		indicador.addIndicador(new Indicador("Capital Total", "Capital Total = EBITDA + FCF", "DEFAULT"));
		indicador.addIndicador(new Indicador("ROE", "ROE = ( Ingreso Neto - Dividendos) / Capital Total", "DEFAULT"));
		indicador.addIndicador(new Indicador("Proporcion De Deuda",
				"Proporcion De Deuda = Dividendos / ( Capital Total - Dividendos )", "DEFAULT"));
		indicador.addIndicador(new Indicador("Margen", "Margen = Capital Total - Dividendos", "DEFAULT"));
		indicador.addIndicador(new Indicador("Indicador Vacio", "Indicador Vacio = 0", "DEFAULT"));
		mercado.setIndicadores(indicador.listIndicadores());
		CondicionFiltro filtro1 = new CondicionFiltro("CondFiltroLongevidad", "filtrarAntiguedadMayor", 10,
				new Indicador("Indicador Vacio", "Indicador Vacio = 0", "DEFAULT"));
		CondicionOrdenamiento orden1 = new CondicionOrdenamiento("CondOrdMaximizarRoe", "ascendente", 10,
				new Indicador("ROE", "ROE = ( Ingreso Neto - Dividendos) / Capital Total", "DEFAULT"));
		CondicionOrdenamiento orden2 = new CondicionOrdenamiento("CondOrdMinimizarNivelDeuda", "descendente", 0,
				new Indicador("Proporcion De Deuda",
						"Proporcion De Deuda = Dividendos / ( Capital Total - Dividendos )", "DEFAULT"));
		Set<CondicionFiltro> condicionesFiltro = new HashSet<>();
		Set<CondicionOrdenamiento> condicionesOrdenamiento = new HashSet<>();
		filtro1.setEmpresas(mercado.getEmpresas());
		orden1.setEmpresas(mercado.getEmpresas());
		orden2.setEmpresas(mercado.getEmpresas());
		condicionesFiltro.add(filtro1);
		condicionesOrdenamiento.add(orden1);
		condicionesOrdenamiento.add(orden2);
		metodologia.setMetodologia("metodologia1", condicionesFiltro, condicionesOrdenamiento, "DEFAULT");
		Set<CondicionFiltro> condicionesFiltroWB = new HashSet<>();
		Set<CondicionOrdenamiento> condicionesOrdenamientoWB = new HashSet<>();
		CondicionFiltro filtroWB3 = new CondicionFiltro("CondFiltroMargen", ">", 1.00, mercado.getIndicador("Margen"));
		CondicionFiltro filtroWB4 = new CondicionFiltro("CondFiltroLongevidad", "filtrarAntiguedadMayor", 10, mercado.getIndicador("Indicador Vacio"));
		CondicionOrdenamiento ordenWB1 = new CondicionOrdenamiento("CondOrdMaximizarRoe", "ascendente", 10, mercado.getIndicador("ROE"));
		CondicionOrdenamiento ordenWB2 = new CondicionOrdenamiento("CondOrdMinimizarNivelDeuda","descendente",0,mercado.getIndicador("Proporcion De Deuda"));
		filtroWB3.setEmpresas(mercado.getEmpresas());
		filtroWB4.setEmpresas(mercado.getEmpresas());
		ordenWB1.setEmpresas(mercado.getEmpresas());
		ordenWB2.setEmpresas(mercado.getEmpresas());
		condicionesOrdenamientoWB.add(ordenWB1);
		condicionesOrdenamientoWB.add(ordenWB2);
		condicionesFiltroWB.add(filtroWB3);
		condicionesFiltroWB.add(filtroWB4);
		metodologia.setMetodologia("warrenBuffet", condicionesFiltroWB, condicionesOrdenamientoWB, "DEFAULT");

		//MercadoBursatilService modelService = new MercadoBursatilService(em);
		mercado.setEmpresas(modelService.generate_empresa_model());
		mercado.setIndicadores(modelService.generate_indicador_model());
		mercado.setUsuarios(modelService.generate_usuario_model());
		mercado.setMetodologias(modelService.generate_metodologias_model());
		;
		mercado.setIndicadorCalculado(modelService.generate_indicadoresCalculados_model());
		;
		mercado.preCalculo_indicadores();

	}

	@Test

	public void test() {
		mercado.getMetodologia("warrenBuffet").calcularMetodologia();//mercado.getMetodologia("warrenBuffet"));
		List<ResultadoCondicionado> listaFiltradaUOrdenada2 = mercado.getMetodologia("warrenBuffet").getListaFiltradaUOrdenada();
		System.out.println("Resultado aplicacion de metodología: ");
		listaFiltradaUOrdenada2.forEach(l -> {
			System.out.println(l.getNombre());
		});
	}

}