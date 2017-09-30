package db;

import java.util.List;

import javax.persistence.EntityManager;
import dondeInvierto.Cuenta;
import dondeInvierto.Empresa;
import dondeInvierto.Indicador;

public class MercadoBursatilService {
	protected EntityManager em;

	public MercadoBursatilService(EntityManager em) {
		this.em = em;
	}
	
	public List<Empresa> generate_empresa_model() {
		EmpresaService empresa = new EmpresaService(em);
		List<Empresa> empresas = empresa.listEmpresas();
		return empresas;
	}
	
	public List<Cuenta> generate_cuentas_model() {
		CuentaService cuenta = new CuentaService(em);
		List<Cuenta> cuentas = cuenta.listCuentasAll();
		return cuentas;
	}
	
	public List<Indicador> generate_indicador_model() {
		IndicadorService indicador = new IndicadorService(em);
		List<Indicador> indicadores = indicador.listIndicadores();
		return indicadores;
	}
	
}
