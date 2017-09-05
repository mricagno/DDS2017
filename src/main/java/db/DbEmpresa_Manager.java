package db;

import java.util.List;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import dondeInvierto.Empresa;

public class DbEmpresa_Manager {

	private static SessionFactory factory;
	public static void main(String[] args) {
	      try{
	         factory = new Configuration().configure().buildSessionFactory();
	      }catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
	      }
	      DbEmpresa_Manager ME = new DbEmpresa_Manager();
	}
	/* Crear empresa en DB */
	public Long addEmpresa(Empresa empresa) {
		Session session = factory.openSession();
		Transaction tx = null;
		Long empresaID = null;
		try {
			tx = session.beginTransaction();
			empresaID = (Long) session.save(empresa);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return empresaID;
	}

	/* Lee todas las empresas */
	public void listEmpresas() {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List empresas = session.createQuery("FROM Empresa").list();
			for (Iterator iterator = empresas.iterator(); iterator.hasNext();) {
				Empresa empresa = (Empresa) iterator.next();
				System.out.print("Id: " + empresa.getId());
				System.out.print("Nombre: " + empresa.getNombre());
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/* Actualizar Empresa */
	public void updateEmpesa(Long id, String Nombre) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Empresa empresa = (Empresa)session.get(Empresa.class, id);
			empresa.setNombre(Nombre);
			session.update(empresa);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/* Borrar Empresa */
	public void deleteEmpresa(Long id) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Empresa empresa = (Empresa) session.get(Empresa.class, id);
			session.delete(empresa);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}