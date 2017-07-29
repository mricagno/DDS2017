package dondeInvierto;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Contiene todas las empresas, cuentas, identificadores y metodologías del dominio.
 */
public enum MercadoBursatil {
	INSTANCE;
	
	private List<Empresa> empresas = new ArrayList<Empresa>();
	private List<Indicador> indicadores = new ArrayList<Indicador>();
	//private List<Metodologia> metodologias = new ArrayList<Metodologia>();
	
	/**
	 * Agrega datos de prueba (empresas, cuentas e indicadores) al mercado bursátil.
	 */
	public void init() {
		
		addEmpresa("Facebook Inc.","11");
		addEmpresa("Tesla Inc.","30");
		addEmpresa("Twitter Inc.","8");
		
		try {
			addCuenta("Facebook Inc.", "EBITDA", "20151231", "8162");
			addCuenta("Facebook Inc.", "EBITDA", "20161231", "14870");
			addCuenta("Facebook Inc.", "FCF", "20151231", "3.99");
			addCuenta("Tesla Inc.", "EBITDA", "20151231", "213");
			addCuenta("Tesla Inc.", "EBITDA", "20161231", "630");
			addCuenta("Twitter Inc.", "EBITDA", "20161231", "751");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	
		addIndicador("Ingreso Neto", 
				"Ingreso Neto = Ingreso Neto En Operaciones Continuas + "
				+ "Ingreso Neto En Operaciones Discontinuadas");
		addIndicador("Retorno sobre capital total",
				"Retorno sobre capital total = (Ingreso Neto - Dividendos) "
				+ "/ Capital Total");

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
		return getEmpresas().stream().
				filter(e -> empresa.equals(e.getNombre())).
				findFirst().orElse(null);
	}
	
	/**
	 * Agrega la empresa a la lista de empresas del mercado bursátil.
	 */
	public void addEmpresa(String nombre, Integer antiguedad) {
		getEmpresas().add(new Empresa(nombre, antiguedad));
	}
	
	/**
	 * Ordena la lista de empresas del mercado bursátil.
	 */
	public void ordenaEmpresa() {
		getEmpresas().sort();
	}
	
	/**
	 * Devuelve true si el mercado bursátil tiene alguna empresa con el nombre buscado.
	 */
	public boolean containsEmpresa(String empresa) {		
		return getEmpresas().stream().
				anyMatch(e -> empresa.equals(e.getNombre()));
	}
	
	/**
	 * Agrega la cuenta en la lista de cuentas de la empresa y la lista de cuentas del mercado bursátil.
	 * Si la empresa de la cuenta no existe, la crea.
	 * @throws ParseException 
	 */
	public void addCuenta(String empresa, String tipo, String periodo, String valor) throws ParseException {
		
		if(!containsEmpresa(empresa)) {
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
		return getIndicadores().stream().
				filter(i -> (nombre.equals(i.getNombre()))).
				findFirst().orElse(null);
	}
	
	/**
	 * Devuelve true si el mercado bursátil tiene algún indicador con el nombre buscado.
	 */
	public boolean containsIndicador(String indicador) {		
		return getIndicadores().stream().
				anyMatch(i -> indicador.equals(i.getNombre()));
	}
	
	/**
	 * Agrega el indicador en la lista de indicadores del mercado bursátil.
	 */
	public void addIndicador(String nombre, String formula) {
		if (!containsIndicador(nombre)) {
			try {
				getIndicadores().add(new Indicador(nombre, formula));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Calcula el valor de un indicador para una determinada empresa, en un periodo dado.
	 */
	public Double calcularIndicador(Indicador indicador, Empresa empresa, String periodo) {
		return indicador.getValorFor(empresa, periodo);
	}
	
	/*public List<Metodologia> getMetodologias() {
		return this.metodologias;
	}	
	
	public void addMetodologia(Metodologia metodologia) {
		if (this.containsMetodologia(metodologia.getNombre()) == -1) {
			this.metodologias.add(metodologia);
		} else {
			System.out.println("Ya existe un indicador con ese nombre.");
		}
	}*/
}