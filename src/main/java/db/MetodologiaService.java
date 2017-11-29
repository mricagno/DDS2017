package db;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.hibernate.HibernateException;
import dondeInvierto.CondicionFiltro;
import dondeInvierto.CondicionOrdenamiento;
import dondeInvierto.Metodologia;

public class MetodologiaService {
	protected EntityManager em;

	public MetodologiaService(EntityManager em) {
		this.em = em;
	}
	
	// Crear Metodologia en DB
	public void setMetodologia(String nombre, Set<CondicionFiltro> condicionesFiltro,
			Set<CondicionOrdenamiento> condicionesOrdenamiento,String usuario) {
		Metodologia metodologia = new Metodologia(nombre, condicionesFiltro, condicionesOrdenamiento,usuario);
		// Get a new transaction
		EntityTransaction trx = em.getTransaction();
		try {
			trx.begin();
			this.em.persist(metodologia);
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		}
	}
	
	public List<Metodologia> getMetodologias(){
		// Get a new transaction
		EntityTransaction trx = this.em.getTransaction();
		try {
			trx.begin();
			List<Metodologia> metodologias = this.em.createQuery(
					"FROM Metodologia", Metodologia.class)
					.getResultList();
			trx.commit();
			return metodologias;
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
}
