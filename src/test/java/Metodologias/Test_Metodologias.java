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


public class Test_Metodologias {
	static MercadoBursatil mercado = MercadoBursatil.INSTANCE;


	@BeforeClass
	public static void inicializar() throws Exception {
		/**
		 * Se cargan gestores de DB
		 */
		DB_Manager DBManager = DB_Manager.getSingletonInstance();
		EntityManager em = DBManager.getEmf().createEntityManager();
		mercado.setFactory(DBManager.getEmf());
		MercadoBursatilService modelService = new MercadoBursatilService(em);
		/**
		 * Se cargan gestores de DB para cada entidad
		 */
		EmpresaService empresa = new EmpresaService(em);
		CuentaService cuenta = new CuentaService(em);
		IndicadorService indicador = new IndicadorService(em);
		UsuarioService usuario = new UsuarioService(em);
		MetodologiaService metodologia = new MetodologiaService(em);
		/**
		 * Se cargan usuarios
		 */
		usuario.addUsuario("GONZALO", "gonzalo", 0);
		usuario.addUsuario("PATRICIO", "patricio", 0);
		usuario.addUsuario("GIAN", "gian", 0);
		usuario.addUsuario("MAXI", "maxi", 0);
		/**
		 * Se cargan empresas
		 */
		empresa.addEmpresa("TWITTER INC.");
		empresa.addEmpresa("FACEBOOK INC.");
		empresa.addEmpresa("TESLA INC.");
		mercado.setEmpresas(empresa.listEmpresas());
		mercado.getEmpresa("FACEBOOK INC.").setAntiguedad(15);
		mercado.getEmpresa("TESLA INC.").setAntiguedad(5);
		mercado.getEmpresa("TWITTER INC.").setAntiguedad(11);
		/**
		 * Se cargan las cuentas en las respectivas empresas
		 */
		Empresa facebook = empresa.getEmpresa_name("FACEBOOK INC.");
		cuenta.addCuenta("EBITDA", "20151231", "8162", facebook);
		cuenta.addCuenta("EBITDA", "20161231", "14870", facebook);
		cuenta.addCuenta("FCF", "20151231", "3.99", facebook);
		Empresa tesla = empresa.getEmpresa_name("TESLA INC.");
		cuenta.addCuenta("EBITDA", "20151231", "213", tesla);
		cuenta.addCuenta("EBITDA", "20161231", "630", tesla);
		cuenta.addCuenta("FCF", "20151231", "230", tesla);
		Empresa twitter = empresa.getEmpresa_name("TWITTER INC.");
		cuenta.addCuenta("EBITDA", "20161231", "751", twitter);
		cuenta.addCuenta("FCF", "20151231", "1751", twitter);
		/**
		 * Se cargan indicadores
		 */
		indicador.addIndicador(new Indicador("Ingreso Neto", "Ingreso Neto = Ingreso Neto En Operaciones Continuas + "
				+ "Ingreso Neto En Operaciones Discontinuadas", "DEFAULT"));
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
		/**
		 * Se cargan condiciones de filtro
		 */
		CondicionFiltro filtro1 = new CondicionFiltro("CondFiltroLongevidad", "filtrarAntiguedadMayor", 10,
                mercado.getIndicador("Indicador Vacio").getId());
		/**
		 * Se cargan condiciones de ordenamiento
		 */
		CondicionOrdenamiento orden1 = new CondicionOrdenamiento("CondOrdMaximizarRoe", "ascendente", 10,
                mercado.getIndicador("ROE").getId());
		CondicionOrdenamiento orden2 = new CondicionOrdenamiento("CondOrdMinimizarNivelDeuda", "descendente", 0,
                mercado.getIndicador("Proporcion De Deuda").getId());
		/**
		 * Se cargan las empresas en las condiciones
		 */
		Set<CondicionFiltro> condicionesFiltro = new HashSet<>();
		Set<CondicionOrdenamiento> condicionesOrdenamiento = new HashSet<>();
		filtro1.setEmpresas(mercado.getEmpresas());
		orden1.setEmpresas(mercado.getEmpresas());
		orden2.setEmpresas(mercado.getEmpresas());
		condicionesFiltro.add(filtro1);
		condicionesOrdenamiento.add(orden1);
		condicionesOrdenamiento.add(orden2);
		/**
		 * Se carga metodologia1
		 */
		metodologia.setMetodologia("metodologia1", condicionesFiltro, condicionesOrdenamiento, "DEFAULT");
		/**
		 * Se repite el proceso de carga de metodologia
		 * En este caso para WarrenBuffet
		 */
		Set<CondicionFiltro> condicionesFiltroWB = new HashSet<>();
		Set<CondicionOrdenamiento> condicionesOrdenamientoWB = new HashSet<>();
		/**
		 * Se cargan condiciones de filtro
		 */
        CondicionFiltro filtroWB3 = new CondicionFiltro("CondFiltroMargen", ">", 1.00, mercado.getIndicador("Margen").getId());
        CondicionFiltro filtroWB4 = new CondicionFiltro("CondFiltroLongevidad", "filtrarAntiguedadMayor", 10, mercado.getIndicador("Indicador Vacio").getId());
		/**
		 * Se cargan condiciones de ordenamiento
		 */
        CondicionOrdenamiento ordenWB1 = new CondicionOrdenamiento("CondOrdMaximizarRoe", "ascendente", 10, mercado.getIndicador("ROE").getId());
        CondicionOrdenamiento ordenWB2 = new CondicionOrdenamiento("CondOrdMinimizarNivelDeuda", "descendente", 0, mercado.getIndicador("Proporcion De Deuda").getId());
		filtroWB3.setEmpresas(mercado.getEmpresas());
		filtroWB4.setEmpresas(mercado.getEmpresas());
		ordenWB1.setEmpresas(mercado.getEmpresas());
		ordenWB2.setEmpresas(mercado.getEmpresas());
		condicionesOrdenamientoWB.add(ordenWB1);
		condicionesOrdenamientoWB.add(ordenWB2);
		condicionesFiltroWB.add(filtroWB3);
		condicionesFiltroWB.add(filtroWB4);
		metodologia.setMetodologia("warrenBuffet", condicionesFiltroWB, condicionesOrdenamientoWB, "DEFAULT");
		/**
		 * Se repite el proceso de carga de metodologia
		 * En este caso para antiguedadMayor
		 */
		Set<CondicionFiltro> condicionesFiltroAM = new HashSet<>();
		Set<CondicionOrdenamiento> condicionesOrdenamientoAM = new HashSet<>();
		/**
		 * Se cargan condiciones de filtro
		 */
        CondicionFiltro filtro = new CondicionFiltro("CondFiltroLongevidad", "filtrarAntiguedadMayor", 10, mercado.getIndicador("Indicador Vacio").getId());
		filtro.setEmpresas(mercado.getEmpresas());
		condicionesFiltroAM.add(filtro);
		metodologia.setMetodologia("antiguedadMayor", condicionesFiltroAM, condicionesOrdenamientoAM, "DEFAULT");
		/**
		 * Se repite el proceso de carga de metodologia
		 * En este caso para antiguedadMenor
		 */
		Set<CondicionFiltro> condicionesFiltroAMe = new HashSet<>();
		Set<CondicionOrdenamiento> condicionesOrdenamientoAMe = new HashSet<>();
		/**
		 * Se cargan condiciones de filtro
		 */
        CondicionFiltro filtrome = new CondicionFiltro("CondFiltroLongevidad", "filtrarAntiguedadMenor", 10, mercado.getIndicador("Indicador Vacio").getId());
		filtrome.setEmpresas(mercado.getEmpresas());
		condicionesFiltroAMe.add(filtrome);
		metodologia.setMetodologia("antiguedadMenor", condicionesFiltroAMe, condicionesOrdenamientoAMe, "DEFAULT");
		/**
		 * Se repite el proceso de carga de metodologia
		 * En este caso para antiguedadDiferente
		 */
		Set<CondicionFiltro> condicionesFiltrodif = new HashSet<>();
		Set<CondicionOrdenamiento> condicionesOrdenamientodif = new HashSet<>();
		/**
		 * Se cargan condiciones de filtro
		 */
        CondicionFiltro filtrodif = new CondicionFiltro("CondFiltroLongevidad", "filtrarAntiguedadDiferente", 15, mercado.getIndicador("Indicador Vacio").getId());
		filtrodif.setEmpresas(mercado.getEmpresas());
		condicionesFiltrodif.add(filtrodif);
		metodologia.setMetodologia("antiguedadDiferente", condicionesFiltrodif, condicionesOrdenamientodif, "DEFAULT");
		/**
		 * Se repite el proceso de carga de metodologia
		 * En este caso para antiguedadIgual
		 */
		Set<CondicionFiltro> condicionesFiltroig = new HashSet<>();
		Set<CondicionOrdenamiento> condicionesOrdenamientoig = new HashSet<>();
		/**
		 * Se cargan condiciones de filtro
		 */
        CondicionFiltro filtroig = new CondicionFiltro("CondFiltroLongevidad", "filtrarAntiguedadIgual", 5, mercado.getIndicador("Indicador Vacio").getId());
		filtroig.setEmpresas(mercado.getEmpresas());
		condicionesFiltroig.add(filtroig);
		metodologia.setMetodologia("antiguedadIgual", condicionesFiltroig, condicionesOrdenamientoig, "DEFAULT");
		/**
		 * Se repite el proceso de carga de metodologia
		 * En este caso para Condición ordenamiento ROE
		 */
		Set<CondicionFiltro> condicionesFiltroroe = new HashSet<>();
		Set<CondicionOrdenamiento> condicionesOrdenamientoroe = new HashSet<>();
		/**
		 * Se cargan condiciones de filtro
		 */
        CondicionOrdenamiento ordenROE = new CondicionOrdenamiento("CondOrdMaximizarRoe", "descendente", 10, mercado.getIndicador("ROE").getId());
		ordenROE.setEmpresas(mercado.getEmpresas());
		condicionesOrdenamientoroe.add(ordenROE);
		metodologia.setMetodologia("CondOrdROE", condicionesFiltroroe, condicionesOrdenamientoroe, "DEFAULT");
		/**
		 * Se repite el proceso de carga de metodologia
		 * En este caso para antiguedadMayor ordenada
		 */
		Set<CondicionFiltro> condicionesFiltroAMord = new HashSet<>();
		Set<CondicionOrdenamiento> condicionesOrdenamientoAMord = new HashSet<>();
		/**
		 * Se cargan condiciones de filtro
		 */
        CondicionFiltro filtroord = new CondicionFiltro("CondFiltroLongevidad", "filtrarAntiguedadMayor", 10, mercado.getIndicador("Indicador Vacio").getId());
		filtroord.setEmpresas(mercado.getEmpresas());
		condicionesFiltroAMord.add(filtroord);
		condicionesOrdenamientoAMord.add(ordenROE);
		metodologia.setMetodologia("antiguedadMayorOrd", condicionesFiltroAMord, condicionesOrdenamientoAMord, "DEFAULT");
		/**
		 * Se carga el mercado bursatil con todos los datos
		 */
		mercado.setEmpresas(modelService.generate_empresa_model());
		mercado.setIndicadores(modelService.generate_indicador_model());
		mercado.setUsuarios(modelService.generate_usuario_model());
		mercado.setMetodologias(modelService.generate_metodologias_model());
		mercado.setIndicadorCalculado(modelService.generate_indicadoresCalculados_model());
		mercado.preCalculo_indicadores();

	}

	@Test
	public void testwarrenBuffet() {
		mercado.getMetodologia("warrenBuffet").calcularMetodologia();
		List<ResultadoCondicionado> listaFiltradaUOrdenada2 = mercado.getMetodologia("warrenBuffet").getListaFiltradaUOrdenada();
		System.out.println("Resultado aplicacion de metodología: ");
		listaFiltradaUOrdenada2.forEach(l -> {
			System.out.println(l.getNombre());
		});
	}

	@Test
	public void testantiguedadmayor() {
		mercado.getMetodologia("antiguedadMayor").calcularMetodologia();
		List<ResultadoCondicionado> listaFiltradaUOrdenada2 = mercado.getMetodologia("antiguedadMayor").getListaFiltradaUOrdenada();
		System.out.println("Resultado aplicacion de metodología: ");
		listaFiltradaUOrdenada2.forEach(l -> {
			System.out.println(l.getNombre());
		});
		assertEquals("FACEBOOK INC.", listaFiltradaUOrdenada2.stream().findFirst().get().getNombre());
	}

	@Test
	public void testantiguedadmenor() {
		mercado.getMetodologia("antiguedadMenor").calcularMetodologia();
		List<ResultadoCondicionado> listaFiltradaUOrdenada2 = mercado.getMetodologia("antiguedadMenor").getListaFiltradaUOrdenada();
		System.out.println("Resultado aplicacion de metodología: ");
		listaFiltradaUOrdenada2.forEach(l -> {
			System.out.println(l.getNombre());
		});
		assertEquals("TESLA INC.", listaFiltradaUOrdenada2.stream().findFirst().get().getNombre());
	}

	@Test
	public void testantiguedaddiferente() {
		mercado.getMetodologia("antiguedadDiferente").calcularMetodologia();
		List<ResultadoCondicionado> listaFiltradaUOrdenada2 = mercado.getMetodologia("antiguedadDiferente").getListaFiltradaUOrdenada();
		System.out.println("Resultado aplicacion de metodología: ");
		listaFiltradaUOrdenada2.forEach(l -> {
			System.out.println(l.getNombre());
		});
		assertNotEquals("FACEBOOK INC.", listaFiltradaUOrdenada2.stream().findFirst().get().getNombre());
	}

	@Test
	public void testantiguedadIgual() {
		mercado.getMetodologia("antiguedadIgual").calcularMetodologia();
		List<ResultadoCondicionado> listaFiltradaUOrdenada2 = mercado.getMetodologia("antiguedadIgual").getListaFiltradaUOrdenada();
		System.out.println("Resultado aplicacion de metodología: ");
		listaFiltradaUOrdenada2.forEach(l -> {
			System.out.println(l.getNombre());
		});
		assertEquals("TESLA INC.", listaFiltradaUOrdenada2.stream().findFirst().get().getNombre());
	}

	@Test
	public void test_OrdenamientoROE() {
		mercado.getMetodologia("CondOrdROE").calcularMetodologia();
		List<ResultadoCondicionado> listaFiltradaUOrdenada2 = mercado.getMetodologia("CondOrdROE").getListaFiltradaUOrdenada();
		System.out.println("Resultado aplicacion de metodología: ");
		listaFiltradaUOrdenada2.forEach(l -> {
			System.out.println(l.getNombre());
		});
		assertEquals("TESLA INC.", listaFiltradaUOrdenada2.stream().findFirst().get().getNombre());
	}

	@Test
	public void testantiguedadmayorOrd() {
		mercado.getMetodologia("antiguedadMayorOrd").calcularMetodologia();
		List<ResultadoCondicionado> listaFiltradaUOrdenada2 = mercado.getMetodologia("antiguedadMayorOrd").getListaFiltradaUOrdenada();
		System.out.println("Resultado aplicacion de metodología: ");
		listaFiltradaUOrdenada2.forEach(l -> {
			System.out.println(l.getNombre());
		});
		assertEquals("FACEBOOK INC.", listaFiltradaUOrdenada2.stream().findFirst().get().getNombre());
	}

}