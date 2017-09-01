package db;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.Assert;
import org.junit.Test;

import dondeInvierto.Empresa;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

public class ContextTest extends AbstractPersistenceTest implements WithGlobalEntityManager {
	
	@Test
	public void contextUp() {
		assertNotNull(entityManager());
	}
	
	@Test
	public void contextUpWithTransaction() throws Exception {
		withTransaction(() -> {});
		Persistence.createEntityManagerFactory("db");
	}
	
	@Test
	public void saveEmpresa() {
		final EntityManager entityManager =PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		final Empresa empresa= new Empresa("Google");
		entityManager.persist(empresa);
		transaction.commit();
	}
	
}