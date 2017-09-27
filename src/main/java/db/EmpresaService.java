package db;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collections;
import org.hibernate.HibernateException;
import dondeInvierto.Empresa;

public class EmpresaService {
	protected EntityManager em;

	public EmpresaService(EntityManager em) {
		this.em = em;
	};

//	 Crear empresa en DB 
	public Long addEmpresa(String empresa_n) {
		Empresa empresa = new Empresa(empresa_n);
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		//this.em = emf.createEntityManager();
		// Get a new transaction
		EntityTransaction trx = em.getTransaction();
		Long empresaID = null;
		try {
			trx.begin();
			this.em.persist(empresa);
			trx.commit();
			empresaID = empresa.getId();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		} finally {
			//this.em.close();
		}
		return empresaID;
	}

	//	 Lee todas las empresas 
	public List<Empresa> listEmpresas() {
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		//this.em = emf.createEntityManager();
		// Get a new transaction
		EntityTransaction trx = this.em.getTransaction();
		try {
			trx.begin();
			@SuppressWarnings("unchecked")
			List<Empresa> empresas = this.em.createQuery("FROM Empresa").getResultList();
			trx.commit();
			return empresas;
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
			return Collections.emptyList();
		} finally {
			//this.em.close();
		}
	}
	
	//Lee una empresa por ID
	public Empresa getEmpresa(Long empresa_id) {
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		//this.em = emf.createEntityManager();
		// Get a new transaction
		EntityTransaction trx = this.em.getTransaction();
		Empresa empresa = null;
		try {
			trx.begin();
			empresa = this.em.find(Empresa.class, empresa_id);
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		} finally {
			//this.em.close();
		}
		return empresa;
	}
	
	//Lee una empresa por nombre
	public Empresa getEmpresa_name(String name) {
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		//this.em = emf.createEntityManager();
		// Get a new transaction
		EntityTransaction trx = this.em.getTransaction();
		Empresa empresa = null;
		try {
			trx.begin();
			//empresa = em.find(Empresa.class, empresa_id);
			List<Empresa> empresas = this.em.createQuery("Select e FROM Empresa e WHERE e.nombre = :nombre",Empresa.class)
	                .setParameter("nombre", name).getResultList();
			empresa = empresas.stream().findFirst().get();
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		} finally {
			//this.em.close();
		}
		return empresa;
	}

	//Actualizar Empresa 
	public void updateEmpresa(Long id, String Nombre) {
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		//this.em = emf.createEntityManager();
		// Get a new transaction
		EntityTransaction trx = this.em.getTransaction();
		try {

			Empresa empresa = this.em.find(Empresa.class, id);
			trx.begin();
			empresa.setNombre(Nombre);
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		} finally {
			//this.em.close();
		}
	}

	// Borrar Empresa 
	public void deleteEmpresa(Long id) {
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
		//this.em = emf.createEntityManager();
		// Get a new transaction
		EntityTransaction trx = this.em.getTransaction();
		try {

			Empresa empresa = (Empresa) this.em.find(Empresa.class, id);
			trx.begin();
			this.em.remove(empresa);
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