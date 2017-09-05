package db;

import java.text.ParseException;

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
		EntityManager em = emf.createEntityManager();
		// Get a new transaction
		EntityTransaction trx = em.getTransaction();
		final Empresa empresa = new Empresa("Facebook Inc.");
		// Start the transaction
		trx.begin();
		em.persist(empresa);
		trx.commit();
	}

	@Test
	public void saveCuenta()  throws Exception {
		EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("db");
		EntityManager em = emf2.createEntityManager();
		final Empresa empresa = new Empresa("Facebook Inc.1");
		final Empresa empresa2 = new Empresa("Google");
		empresa.addCuenta(new Cuenta("FCF", "20151231", "3.99",empresa));
		empresa.addCuenta(new Cuenta("EBITDA", "20151231", "3.99",empresa));
		empresa2.addCuenta(new Cuenta("EBITDA", "20151231", "3.99",empresa2));
		// Get a new transaction
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		em.persist(empresa);
		em.persist(empresa2);
		trx.commit();
	}

	@Test
	public void saveIndicador() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		EntityManager em = emf.createEntityManager();
		// Get a new transaction
		EntityTransaction trx = em.getTransaction();
		final Indicador indicador = new Indicador("TEST","TEST = EBITDA + 2");
		// Get a new transaction
		trx.begin();
		em.persist(indicador);
		trx.commit();
	}

	/*
	 * @Test public void getEmpresas() throws ParseException { final EntityManager
	 * entityManager = PerThreadEntityManagers.getEntityManager(); final
	 * EntityTransaction transaction = entityManager.getTransaction(); final Empresa
	 * empresa = new Empresa("Facebook Inc.3"); final Cuenta cuenta = new
	 * Cuenta("FCF", "20151231", "3.99"); empresa.addCuenta(cuenta);
	 * entityManager.persist(empresa); entityManager.persist(cuenta);
	 * transaction.commit(); java.util.List<Empresa> empresas =
	 * PerThreadEntityManagers.getEntityManager()
	 * .createQuery("From Empresa where nombre='Facebook Inc.3'",
	 * Empresa.class).getResultList(); java.util.List<Cuenta> cuentas; for (Empresa
	 * empresa1 : empresas) { //java.util.List<Cuenta> cuentas =
	 * PerThreadEntityManagers.getEntityManager().
	 * createQuery("from Cuenta where empresa_id = :empresa").setParameter(
	 * "empresa", "Facebook Inc.3").getResultList();
	 * 
	 * } cuentas = PerThreadEntityManagers.getEntityManager().
	 * createQuery("from Cuenta where empresa_id = 'Facebook Inc.3'").getResultList(
	 * ); entityManager.getTransaction().commit(); entityManager.close();
	 * assertNotNull(cuentas); }
	 */
}