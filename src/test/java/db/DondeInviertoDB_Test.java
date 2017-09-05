package db;

import static org.junit.Assert.assertNotNull;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.Assert;
import org.junit.Test;

import dondeInvierto.Empresa;
import dondeInvierto.Cuenta;
import dondeInvierto.Indicador;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import antlr.collections.List;

public class ContextTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

	@Test
	public void contextUp() {
		assertNotNull(entityManager());
	}

	@Test
	public void contextUpWithTransaction() throws Exception {
		withTransaction(() -> {
		});
		Persistence.createEntityManagerFactory("db");
	}

	@Test
	public void saveEmpresa() {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		final Empresa empresa = new Empresa("Facebook Inc.");
		entityManager.persist(empresa);
		transaction.commit();
	}

	@Test
	public void saveCuenta() throws ParseException {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		final Empresa empresa = new Empresa("Facebook Inc.1");
		final Cuenta cuenta = new Cuenta("FCF", "20151231", "3.99");
		empresa.addCuenta(cuenta);
		entityManager.persist(empresa);
		entityManager.persist(cuenta);
		transaction.commit();
	}
	/*
	 * @Test public void saveIndicador() { final EntityManager entityManager =
	 * PerThreadEntityManagers.getEntityManager(); final EntityTransaction
	 * transaction = entityManager.getTransaction(); final Indicador indicador= new
	 * Indicador("EBITDA", formula); entityManager.persist(empresa);
	 * transaction.commit(); }
	 */

	@Test
	public void getEmpresas() throws ParseException {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		final Empresa empresa = new Empresa("Facebook Inc.3");
		final Cuenta cuenta = new Cuenta("FCF", "20151231", "3.99");
		empresa.addCuenta(cuenta);
		entityManager.persist(empresa);
		entityManager.persist(cuenta);
		transaction.commit();
		java.util.List<Empresa> empresas = PerThreadEntityManagers.getEntityManager()
				.createQuery("From Empresa where nombre='Facebook Inc.3'", Empresa.class).getResultList();
		java.util.List<Cuenta> cuentas;
		for (Empresa empresa1 : empresas) {
			//java.util.List<Cuenta> cuentas = PerThreadEntityManagers.getEntityManager().createQuery("from Cuenta where empresa_id = :empresa").setParameter("empresa", "Facebook Inc.3").getResultList();
			
		}
		cuentas = PerThreadEntityManagers.getEntityManager().createQuery("from Cuenta where empresa_id = 'Facebook Inc.3'").getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
		assertNotNull(cuentas);
	}
}