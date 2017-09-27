package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import dondeInvierto.Cuenta;
import dondeInvierto.Empresa;

public class CuentaService {
	protected EntityManager em;
	public CuentaService(EntityManager em) {
		this.em = em;
	};
//	 Crear cuenta en DB 
	//public Long addCuenta(Cuenta cuenta, Empresa empresa) {
	public Long addCuenta(String tipo, String periodo, String valor, Empresa empresa) throws ParseException {
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		//this.em = emf.createEntityManager();
		empresa.addCuenta(new Cuenta(tipo, periodo, valor));
		// Get a new transaction
		EntityTransaction trx = this.em.getTransaction();
		Long cuentaID = null;
		try {
			trx.begin();
			this.em.persist(empresa);
			trx.commit();
			//cuentaID = cuenta.getId();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		} finally {
			//this.em.close();
		}
		return cuentaID;
	}

	//Devuelve todas las cuentas 
	public void listCuentasAll() {
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		//this.em = emf.createEntityManager();
		// Get a new transaction
		EntityTransaction trx = this.em.getTransaction();
		try {
			trx.begin();
			List cuentas = this.em.createQuery("FROM Cuenta").getResultList();
			System.out.println("LISTA DE Cuentas");
			for (Iterator iterator = cuentas.iterator(); iterator.hasNext();) {
				Cuenta cuenta = (Cuenta) iterator.next();
				System.out.println("Id: " + cuenta.getId());
				System.out.println("Tipo: " + cuenta.getTipo());
				System.out.println("Periodo: " + cuenta.getPeriodo());
				System.out.println("Valor: " + cuenta.getValor());
			}
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		} finally {
			//this.em.close();
		}
	}
	
	//	 Lee las cuentas de una empresa
	public void listCuentas(Empresa empresa) {
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		//this.em = emf.createEntityManager();
		// Get a new transaction
		EntityTransaction trx = this.em.getTransaction();
		try {
			trx.begin();
			List cuentas = this.em.createQuery("FROM Cuenta WHERE empresa_id = :eid",Cuenta.class).setParameter("eid", empresa).getResultList();
			System.out.println("LISTA DE Cuentas de empresa " + empresa.getNombre());
			for (Iterator iterator = cuentas.iterator(); iterator.hasNext();) {
				Cuenta cuenta = (Cuenta) iterator.next();
				System.out.println("Id: " + cuenta.getId());
				System.out.println("Tipo: " + cuenta.getTipo());
				System.out.println("Periodo: " + cuenta.getPeriodo());
				System.out.println("Valor: " + cuenta.getValor());
			}
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		} finally {
			//this.em.close();
		}
	}

	//	 Actualizar Cuenta
	public void updateCuenta(Long id, Double valor) {
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		//this.em = emf.createEntityManager();
		// Get a new transaction
		EntityTransaction trx = this.em.getTransaction();
		try {

			Cuenta cuenta = this.em.find(Cuenta.class, id);
			trx.begin();
			cuenta.setValor(valor);
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		} finally {
			//this.em.close();
		}
	}

	// Borrar Cuenta 
	public void deleteCuenta(Long id) {
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		//this.em = emf.createEntityManager();
		// Get a new transaction
		EntityTransaction trx = em.getTransaction();
		try {
			Cuenta cuenta = (Cuenta) this.em.find(Cuenta.class, id);
			trx.begin();
			this.em.remove(cuenta);
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		} finally {
			//this.em.close();
		}
	}
}
