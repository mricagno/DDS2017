package db;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;

import dondeInvierto.*;

public class DB_Condicion_Test {
/*	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}*/

/*	@Test
	public void test_condicion() {
	  EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
	   EntityManager em = emf.createEntityManager(); // Get a new transaction
		EntityTransaction trx = em.getTransaction();
		final Condicion condicion = new CondicionOrdenamiento("TEST2", ">", 19,2);
		// Start the transaction
		trx.begin();
		em.persist(condicion);
		trx.commit();
		em.close();
	}
	
	@Test
	public void test_condicionFiltro() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		EntityManager em = emf.createEntityManager(); // Get a new transaction
		EntityTransaction trx = em.getTransaction();
		final Condicion condicion2 = new CondicionFiltro("TEST5", ">", 19);
		// Start the transaction
		trx.begin();
		em.persist(condicion2);
		trx.commit();
		em.close();
	}*/
	
	@Test
	public void test_condiciontipos() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		EntityManager em = emf.createEntityManager(); // Get a new transaction
		EntityTransaction trx = em.getTransaction();
		//final Condicion condicion = new CondicionOrdenamiento("TEST2", ">", 19,2);
		//final Condicion condicion2 = new CondicionFiltro("TEST5", ">", 19);
		// Start the transaction
		trx.begin();
		//em.persist(condicion);
		//em.persist(condicion2);
		trx.commit();
		em.close();
	}

}
