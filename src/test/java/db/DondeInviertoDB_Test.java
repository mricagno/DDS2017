package db;

import static org.junit.Assert.assertEquals;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.Test;
import dondeInvierto.Cuenta;
import dondeInvierto.Empresa;
import dondeInvierto.Indicador;

public class DondeInviertoDB_Test {

	@Test
	public void saveEmpresa() throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		EntityManager em = emf.createEntityManager(); // Get a new transaction
		EntityTransaction trx = em.getTransaction();
		final Empresa empresa = new Empresa("Facebook Inc.");
		// Start the transaction
		trx.begin();
		em.persist(empresa);
		trx.commit();
		em.close();
	}

	@Test
	public void saveCuenta() throws Exception {
		EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("db");
		EntityManager em = emf2.createEntityManager();
		final Empresa empresa = new Empresa("Facebook Inc.1");
		final Empresa empresa2 = new Empresa("Google");
		empresa.addCuenta(new Cuenta("FCF", "20151231", "3.99", empresa));
		empresa.addCuenta(new Cuenta("EBITDA", "20151231", "3.99", empresa));
		empresa2.addCuenta(new Cuenta("EBITDA", "20151231", "3.99", empresa2));
		// Get a new transaction
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		em.persist(empresa);
		em.persist(empresa2);
		trx.commit();
		em.close();
	}
/*
	@Test
	public void saveIndicador() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		EntityManager em = emf.createEntityManager();
		// Get a new transaction
		EntityTransaction trx = em.getTransaction();
		final Indicador indicador = new Indicador("TEST", "TEST = EBITDA + 2"); // Get a new transaction trx.begin();
		em.persist(indicador);
		trx.commit();
		em.close();
	}*/
	/*
	@Test
	public void saveEmpresa_clase() {
		DbEmpresa_Manager empresa_manager = new DbEmpresa_Manager();
		final Empresa empresa = new Empresa("Facebook Inc.8");
		Long empresa_id = empresa_manager.addEmpresa(empresa);
		assertEquals(empresa_id, new Long(1));
	}
	
	@Test
	public void updateEmpresa_clase() {
		DbEmpresa_Manager empresa_manager = new DbEmpresa_Manager();
		final Empresa empresa = new Empresa("Facebook Inc.8");
		empresa_manager.addEmpresa(empresa);
		empresa_manager.updateEmpesa(empresa.getId(), "Facebook Inc.9");
	}
	
	@Test
	public void readEmpresa_clase() {
		DbEmpresa_Manager empresa_manager = new DbEmpresa_Manager();
		empresa_manager.listEmpresas();
	}
	
	@Test
	public void deleteEmpresa_clase() {
		DbEmpresa_Manager empresa_manager = new DbEmpresa_Manager();
		empresa_manager.deleteEmpresa(new Long(1));
	}*/

}