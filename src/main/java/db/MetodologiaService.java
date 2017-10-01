package db;

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
			Set<CondicionOrdenamiento> condicionesOrdenamiento) {
		Metodologia metodologia = new Metodologia(nombre, condicionesFiltro, condicionesOrdenamiento);
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
	
	
	
}
