package dondeInvierto;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import db.CuentaService;
import db.DB_Manager;
import db.EmpresaService;
import db.IndicadorService;
import db.MercadoBursatilService;

/**
 * Contiene todas las empresas, cuentas, identificadores y metodologías del
 * dominio.
 */
public enum MercadoBursatil {
	INSTANCE;

	private List<Empresa> empresas = new ArrayList<Empresa>();
	private List<Indicador> indicadores = new ArrayList<Indicador>();
	private List<Metodologia> metodologias = new ArrayList<Metodologia>();
	EntityManagerFactory factory;
	/**
	 * Agrega datos de prueba (empresas, cuentas e indicadores) al mercado bursátil.
	 * 
	 * @throws ParseException
	 */
	public void init() throws ParseException {

		DB_Manager DBManager = DB_Manager.getSingletonInstance();
		factory = DBManager.getEmf();
		EntityManager em = factory.createEntityManager();
		//this.init_db(em);
		this.init_model(em);
	}

	/**
	 * Devuelve todas las empresas que existen en el mercado bursátil.
	 */
	public List<Empresa> getEmpresas() {
		return this.empresas;
	}

	/**
	 * Devuelve la empresa con el nombre buscado.
	 */
	public Empresa getEmpresa(String empresa) {
		return getEmpresas().stream().filter(e -> empresa.equals(e.getNombre())).findFirst().orElse(null);
	}

	/**
	 * Agrega la empresa a la lista de empresas del mercado bursátil.
	 */
	public void addEmpresa(String nombre) {
		getEmpresas().add(new Empresa(nombre));
	}

	/**
	 * Devuelve true si el mercado bursátil tiene alguna empresa con el nombre
	 * buscado.
	 */
	public boolean containsEmpresa(String empresa) {
		return getEmpresas().stream().anyMatch(e -> empresa.equals(e.getNombre()));
	}

	/**
	 * Agrega la cuenta en la lista de cuentas de la empresa y la lista de cuentas
	 * del mercado bursátil. Si la empresa de la cuenta no existe, la crea.
	 * 
	 * @throws ParseException
	 */
	public void addCuenta(String empresa, String tipo, String periodo, String valor) throws ParseException {

		if (!containsEmpresa(empresa)) {
			addEmpresa(empresa);
		}

		Empresa emp = getEmpresa(empresa);

		if (!emp.containsCuenta(tipo, periodo)) {
			try {
				emp.addCuenta(new Cuenta(tipo, periodo, valor));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Devuelve todos los indicadores que existen en el mercado bursátil.
	 */
	public List<Indicador> getIndicadores() {
		return this.indicadores;
	}

	/**
	 * Devuelve el indicador buscado.
	 */
	public Indicador getIndicador(String nombre) {
		return getIndicadores().stream().filter(i -> (nombre.equals(i.getNombre()))).findFirst().orElse(null);
	}
	
	/**
	 * Devuelve los indicadores buscados para un usuario.
	 */
	public List<Indicador> getIndicadorUsuario(String usuario) {
		return getIndicadores().stream().filter(i -> (usuario.equals(i.getCreador()))).collect(Collectors.toList());
	}

	/**
	 * Devuelve true si el mercado bursátil tiene algún indicador con el nombre
	 * buscado.
	 */
	public boolean containsIndicador(String indicador) {
		return getIndicadores().stream().anyMatch(i -> indicador.equals(i.getNombre()));
	}

	/**
	 * Agrega el indicador en la lista de indicadores del mercado bursátil.
	 */
	public void addIndicador(String nombre, String formula, String creador) {
		if (!containsIndicador(nombre)) {
			try {
				getIndicadores().add(new Indicador(nombre, formula, creador));
			} catch (IllegalStateException e) {
				System.err.println("[ERROR] (ANTLR) " + e.getMessage() + ". "
						+ "Se produjo un error al intentar parsear la expresión ingresada (" + nombre + " = " + formula
						+ "). El indicador no ha sido creado. Por favor, revísela e intente nuevamente.");
			}
		}
	}

	/**
	 * Calcula el valor de un indicador para una determinada empresa, en un periodo
	 * dado.
	 */
	public Double calcularIndicador(Indicador indicador, Empresa empresa, String periodo) {
		return indicador.getValorFor(empresa, periodo);
	}

	/**
	 * Devuelve listado de metodologias.
	 */

	public List<Metodologia> getMetodologias() {
		return this.metodologias;
	}

	/**
	 * Devuelve la metodologia con el nombre buscado.
	 */
	public Metodologia getMetodologia(String metodologia) {
		return getMetodologias().stream().filter(m -> metodologia.equals(m.getNombre())).findFirst().orElse(null);
	}

	/**
	 * Agrega la metodologia a la lista de metodologias del mercado bursátil.
	 */
	public void addMetodologia(String nombre, Set<CondicionFiltro> condicionesFiltro,
			Set<CondicionOrdenamiento> condicionesOrdenamiento, String usuario) {
		if (this.containsMetodologia(nombre)) {
			System.out.println("Ya existe una metodologia con ese nombre.");
		} else {
			getMetodologias().add(new Metodologia(nombre, condicionesFiltro, condicionesOrdenamiento, usuario));
		}
	}

	/**
	 * Devuelve true si el mercado bursátil tiene alguna empresa con el nombre
	 * buscado.
	 */
	public boolean containsMetodologia(String nombre) {
		return getMetodologias().stream().anyMatch(m -> nombre.equals(m.getNombre()));
	}

	public void init_db(EntityManager em) throws ParseException {
		EmpresaService empresa = new EmpresaService(em);
		CuentaService cuenta = new CuentaService(em);
		IndicadorService indicador = new IndicadorService(em);
		empresa.addEmpresa("Facebook Inc.");
		empresa.addEmpresa("Tesla Inc.");
		empresa.addEmpresa("Twitter Inc.");
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
				+ "Ingreso Neto En Operaciones Discontinuadas","DEFAULT"));
		indicador.addIndicador(new Indicador("Retorno sobre capital total",
				"Retorno sobre capital total = (Ingreso Neto - Dividendos) " + "/ Capital Total","DEFAULT"));
		indicador.addIndicador(new Indicador("Indicador", "Indicador = EBITDA + FCF","DEFAULT"));
		indicador.addIndicador(new Indicador("Ingreso Neto En Operaciones Continuas", "Ingreso Neto En Operaciones Continuas = EBITDA ","DEFAULT"));
		indicador.addIndicador(new Indicador("Ingreso Neto En Operaciones Discontinuadas", "Ingreso Neto En Operaciones Discontinuas = FCF","DEFAULT"));
		indicador.addIndicador(new Indicador("Dividendos", "Dividendos = EBITDA - FCF","DEFAULT"));
		indicador.addIndicador(new Indicador("Capital Total", "Capital Total = EBITDA + FCF","DEFAULT"));
		indicador.addIndicador(new Indicador("ROE", "ROE = ( Ingreso Neto - Dividendos) / Capital Total","DEFAULT"));
		indicador.addIndicador(new Indicador("Proporcion De Deuda", "Proporcion De Deuda = Dividendos / ( Capital Total - Dividendos )","DEFAULT"));
		indicador.addIndicador(new Indicador("Margen", "Margen = Capital Total - Dividendos","DEFAULT"));
		indicador.addIndicador(new Indicador("Indicador Vacio", "Indicador Vacio = 0","DEFAULT"));
		em.close();
	}

	public void init_model(EntityManager em) throws ParseException {
		MercadoBursatilService modelService = new MercadoBursatilService(em);
		this.empresas = modelService.generate_empresa_model();
		this.indicadores = modelService.generate_indicador_model();
	}
	
	public void close() {
		DB_Manager DBManager = DB_Manager.getSingletonInstance();
		DBManager.closeEmf(factory);
	}
}