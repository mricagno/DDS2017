package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.Date;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import dondeInvierto.Empresa;

public class DbEmpresa_Manager {

	public DbEmpresa_Manager() {
	};

//	 Crear empresa en DB 
	public Long addEmpresa(Empresa empresa) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		EntityManager em = emf.createEntityManager();
		// Get a new transaction
		EntityTransaction trx = em.getTransaction();
		Long empresaID = null;
		try {
			trx.begin();
			em.persist(empresa);
			trx.commit();
			empresaID = empresa.getId();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
		return empresaID;
	}

	//	 Lee todas las empresas 
	public void listEmpresas() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		EntityManager em = emf.createEntityManager();
		// Get a new transaction
		EntityTransaction trx = em.getTransaction();
		try {
			trx.begin();
			List empresas = em.createQuery("FROM Empresa").getResultList();
			System.out.println("LISTA DE EMPRESAS");
			for (Iterator iterator = empresas.iterator(); iterator.hasNext();) {
				Empresa empresa = (Empresa) iterator.next();
				System.out.println("Id: " + empresa.getId());
				System.out.println("Nombre: " + empresa.getNombre());
			}
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}
	
	//Lee una empresa
	public Empresa getEmpresa(Long empresa_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		EntityManager em = emf.createEntityManager();
		// Get a new transaction
		EntityTransaction trx = em.getTransaction();
		Empresa empresa = null;
		try {
			trx.begin();
			empresa = em.find(Empresa.class, empresa_id);
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
		return empresa;
	}

	//	 Actualizar Empresa 
	public void updateEmpresa(Long id, String Nombre) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		EntityManager em = emf.createEntityManager();
		// Get a new transaction
		EntityTransaction trx = em.getTransaction();
		try {

			Empresa empresa = em.find(Empresa.class, id);
			trx.begin();
			empresa.setNombre(Nombre);
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	// Borrar Empresa 
	public void deleteEmpresa(Long id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		EntityManager em = emf.createEntityManager();
		// Get a new transaction
		EntityTransaction trx = em.getTransaction();
		try {

			Empresa empresa = (Empresa) em.find(Empresa.class, id);
			trx.begin();
			em.remove(empresa);
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

}