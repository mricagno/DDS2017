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
import dondeInvierto.Cuenta;
import dondeInvierto.Empresa;

public class DbCuenta_Manager {
	
	public DbCuenta_Manager() {
	};
//	 Crear cuenta en DB 
	public Long addCuenta(Cuenta cuenta) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		EntityManager em = emf.createEntityManager();
		// Get a new transaction
		EntityTransaction trx = em.getTransaction();
		Long cuentaID = null;
		try {
			trx.begin();
			em.persist(cuenta);
			trx.commit();
			cuentaID = cuenta.getId();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
		return cuentaID;
	}

	//Devuelve todas las cuentas 
	public void listCuentasAll() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		EntityManager em = emf.createEntityManager();
		// Get a new transaction
		EntityTransaction trx = em.getTransaction();
		try {
			trx.begin();
			List cuentas = em.createQuery("FROM Cuenta").getResultList();
			System.out.println("LISTA DE Cuentas");
			for (Iterator iterator = cuentas.iterator(); iterator.hasNext();) {
				Cuenta cuenta = (Cuenta) iterator.next();
				System.out.println("Id: " + cuenta.getId());
				System.out.println("Tipo: " + cuenta.getTipo());
				System.out.println("Periodo: " + cuenta.getPeriodo());
				System.out.println("Valor: " + cuenta.getValor());
				System.out.println("Empresa: " + cuenta.getEmpresa().getNombre());
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
	
	//	 Lee las cuentas de una empresa
	public void listCuentas(Empresa empresa) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		EntityManager em = emf.createEntityManager();
		// Get a new transaction
		EntityTransaction trx = em.getTransaction();
		try {
			trx.begin();
			List cuentas = em.createQuery("FROM Cuenta c WHERE c.empresa = :eid",Cuenta.class).setParameter("eid", empresa).getResultList();
			System.out.println("LISTA DE Cuentas de empresa " + empresa.getNombre());
			for (Iterator iterator = cuentas.iterator(); iterator.hasNext();) {
				Cuenta cuenta = (Cuenta) iterator.next();
				System.out.println("Id: " + cuenta.getId());
				System.out.println("Tipo: " + cuenta.getTipo());
				System.out.println("Periodo: " + cuenta.getPeriodo());
				System.out.println("Valor: " + cuenta.getValor());
				System.out.println("Empresa: " + cuenta.getEmpresa());
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

	//	 Actualizar Cuenta
	public void updateCuenta(Long id, Double valor) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		EntityManager em = emf.createEntityManager();
		// Get a new transaction
		EntityTransaction trx = em.getTransaction();
		try {

			Cuenta cuenta = em.find(Cuenta.class, id);
			trx.begin();
			cuenta.setValor(valor);
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	// Borrar Cuenta 
	public void deleteCuenta(Long id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		EntityManager em = emf.createEntityManager();
		// Get a new transaction
		EntityTransaction trx = em.getTransaction();
		try {
			Cuenta cuenta = (Cuenta) em.find(Cuenta.class, id);
			trx.begin();
			em.remove(cuenta);
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
