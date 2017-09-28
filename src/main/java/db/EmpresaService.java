package db;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Collections;
import org.hibernate.HibernateException;
import dondeInvierto.Empresa;

public class EmpresaService {
	protected EntityManager em;

	public EmpresaService(EntityManager em) {
		this.em = em;
	};

	// Crear empresa en DB
	public Long addEmpresa(String empresa_n) {
		Empresa empresa = new Empresa(empresa_n);
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
		}
		return empresaID;
	}

	// Lee todas las empresas
	public List<Empresa> listEmpresas() {
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
		}
	}

	// Lee una empresa por ID
	public Empresa getEmpresa(Long empresa_id) {
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
		}
		return empresa;
	}

	// Lee una empresa por nombre
	public Empresa getEmpresa_name(String name) {
		// Get a new transaction
		EntityTransaction trx = this.em.getTransaction();
		Empresa empresa = null;
		try {
			trx.begin();
			List<Empresa> empresas = this.em
					.createQuery("Select e FROM Empresa e WHERE e.nombre = :nombre", Empresa.class)
					.setParameter("nombre", name).getResultList();
			empresa = empresas.stream().findFirst().get();
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		}
		return empresa;
	}

	// Actualizar Empresa
	public void updateEmpresa(Long id, String Nombre) {
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
		}
	}

	// Borrar Empresa
	public void deleteEmpresa(Long id) {
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
		}
	}

}