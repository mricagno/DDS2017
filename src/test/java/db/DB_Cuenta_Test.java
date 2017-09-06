package db;

import static org.junit.Assert.assertEquals;
import java.text.ParseException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.Test;
import dondeInvierto.Cuenta;
import dondeInvierto.Empresa;
import dondeInvierto.Indicador;

public class DB_Cuenta_Test {

	
	@Test
	public void Create_Cuenta_clase() throws ParseException {
		DbCuenta_Manager cuenta_manager = new DbCuenta_Manager();
		DbEmpresa_Manager empresa_manager = new DbEmpresa_Manager();
		final Empresa empresa = empresa_manager.getEmpresa(new Long(1));
		final Cuenta cuenta = new Cuenta("FCFF", "20171231", "3.99", empresa);
		cuenta_manager.addCuenta(cuenta);
	}
	
	/*@Test
	public void listCuentas_clase() {
		DbCuenta_Manager cuenta_manager = new DbCuenta_Manager();
		cuenta_manager.listCuentasAll();
	}*/
	
	@Test
	public void listCuentasEmpresa_clase() {
		DbCuenta_Manager cuenta_manager = new DbCuenta_Manager();
		DbEmpresa_Manager empresa_manager = new DbEmpresa_Manager();
		final Empresa empresa = empresa_manager.getEmpresa(new Long(1));
		cuenta_manager.listCuentas(empresa);
	}
	
	@Test
	public void delete_Cuenta_clase() throws ParseException {
		DbCuenta_Manager cuenta_manager = new DbCuenta_Manager();
		cuenta_manager.deleteCuenta(new Long(12));
	}
	
	@Test
	public void updateCuenta_clase() {
		DbCuenta_Manager cuenta_manager = new DbCuenta_Manager();
		cuenta_manager.updateCuenta(new Long(2), new Double(4));
	}
	
	
}
