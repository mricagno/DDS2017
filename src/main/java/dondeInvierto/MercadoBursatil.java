package dondeInvierto;

import batch.Job_fileLoad;
import db.*;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;


/**
 * Contiene todas las empresas, cuentas, identificadores y metodologías del
 * dominio.
 */
public enum MercadoBursatil {
    INSTANCE;
    private List<Usuario> usuarios = new ArrayList<Usuario>();
    private List<Empresa> empresas = new ArrayList<Empresa>();
    private List<Indicador> indicadores = new ArrayList<Indicador>();
    private List<Metodologia> metodologias = new ArrayList<Metodologia>();
    private Usuario usuario_logueado;
    String last_file_loaded;
    EntityManagerFactory factory;

    /**
     * Agrega datos de prueba (empresas, cuentas e indicadores) al mercado bursátil.
     *
     * @throws Exception
     */
    public void init() throws Exception {

        DB_Manager DBManager = DB_Manager.getSingletonInstance();
        this.factory = DBManager.getEmf();
        EntityManager em = factory.createEntityManager();
        this.init_model(em);
        this.set_job();
    }

    /**
     * Devuelve el usuario con el nombre buscado.
     */

    public Usuario getUsuario(String usuario, String password) {

        return this.usuarios.stream().filter(u -> usuario.equals(u.getUsuario()))
                .filter(u1 -> password.equals(u1.getPass())).findFirst().orElse(null);
    }

    /**
     * Devuelve todos los usuarios que existen en el mercado bursátil.
     */
    public List<Usuario> getUsuarios() {
        return this.usuarios;
    }

    public void setIndicadores(List<Indicador> indicadores) {
        this.indicadores = indicadores;
    }

    public void setMetodologias(List<Metodologia> metodologias) {
        this.metodologias = metodologias;
    }

    /**
     * Setea el usuario logeado en el mercado bursátil.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario_logueado = usuario;
    }

    /**
     * Agrega el usuario a la lista de usuarios del mercado bursátil.
     */
    public void addUsuario(String nombre, String password, int cant_int) {
        getUsuarios().add(new Usuario(nombre, password, cant_int));
    }

    /**
     * Devuelve la empresa con el nombre buscado.
     */
    public Usuario getUsuarioLog() {
        return this.usuario_logueado;
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
     * Devuelve la cuenta buscada.
     */
    public Cuenta getCuenta(Cuenta cuenta, Empresa empresa) {
        return getCuentas(empresa).stream().filter(e -> cuenta.getTipo().equals(e.getTipo())).
                filter(e -> cuenta.getPeriodoAsString().equals(e.getPeriodoAsString())).findFirst().orElse(null);
    }

    /**
     * Devuelve todas las cuentas que existen para una empresa.
     */
    public List<Cuenta> getCuentas(Empresa empresa) {
        return this.getEmpresa(empresa.getNombre()).getCuentas();
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
     * Borra el indicador deseado.
     */
    public boolean delete_Indicador(String nombre) {
        boolean borrado_exitoso = false;
        for (Iterator<Indicador> iter = this.indicadores.listIterator(); iter.hasNext(); ) {
            Indicador a = iter.next();
            if (nombre.equals(a.getNombre())) {
                iter.remove();
                borrado_exitoso = true;
            }
        }
        return borrado_exitoso;
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
    public boolean addIndicador(String nombre, String formula, String usuario) {
        boolean added = false;

        if (!containsIndicador(nombre)) {
            try {
                getIndicadores().add(new Indicador(nombre, formula, usuario));
                added = true;
            } catch (IllegalStateException e) {
                System.err.println("[ERROR] (ANTLR) " + e.getMessage() + ". "
                        + "Se produjo un error al intentar parsear la expresión ingresada (" + formula
                        + "). El indicador no ha sido creado. Por favor, revísela e intente nuevamente.");
            }
        }

        return added;
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

    public EntityManagerFactory getFactory() {
        return this.factory;
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

    public void init_db(EntityManager em) throws Exception {
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
        CondicionFiltro filtro1 = new CondicionFiltro("CondFiltroLongevidad", "filtrarAntiguedadMayor", 10,
                new Indicador("Indicador Vacio", "Indicador Vacio = 0", "DEFAULT"));
        CondicionOrdenamiento orden1 = new CondicionOrdenamiento("CondOrdMaximizarRoe", "ascendente", 10,
                new Indicador("ROE", "ROE = ( Ingreso Neto - Dividendos) / Capital Total", "DEFAULT"));
        CondicionOrdenamiento orden2 = new CondicionOrdenamiento("CondOrdMinimizarNivelDeuda", "descendente", 0,
                new Indicador("Proporcion De Deuda",
                        "Proporcion De Deuda = Dividendos / ( Capital Total - Dividendos )", "DEFAULT"));
        Set<CondicionFiltro> condicionesFiltro = new HashSet<>();
        Set<CondicionOrdenamiento> condicionesOrdenamiento = new HashSet<>();
        condicionesFiltro.add(filtro1);
        condicionesOrdenamiento.add(orden1);
        condicionesOrdenamiento.add(orden2);
        metodologia.setMetodologia("metodologia1", condicionesFiltro, condicionesOrdenamiento, "DEFAULT");
        //em.close();
    }

    public void init_model(EntityManager em) throws Exception {
        MercadoBursatilService modelService = new MercadoBursatilService(em);
        this.empresas = modelService.generate_empresa_model();
        if (this.empresas.isEmpty()) {
            this.init_db(em);
        }
        this.empresas = modelService.generate_empresa_model();
        this.indicadores = modelService.generate_indicador_model();
        this.usuarios = modelService.generate_usuario_model();
        this.metodologias = modelService.generate_metodologias_model();


    }

    public void close() {
        DB_Manager DBManager = DB_Manager.getSingletonInstance();
        DBManager.closeEmf(factory);
    }

    public String get_lastFileLoaded() {
        return this.last_file_loaded;
    }

    public void set_lastFileLoaded(String file) {
        this.last_file_loaded = file;
    }

    public void set_job() throws SchedulerException {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();
        JobDetail job = newJob(Job_fileLoad.class)
                .withIdentity("job1", "group1")
                .build();
        // Trigger the job to run now, and then repeat every 40 seconds
        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(40)
                        .repeatForever())
                .build();

        // Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(job, trigger);
        scheduler.start();
    }
}