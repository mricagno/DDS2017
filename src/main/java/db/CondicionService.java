package db;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.hibernate.HibernateException;
import dondeInvierto.*;

public class CondicionService {
	protected EntityManager em;

	public CondicionService(EntityManager em) {
		this.em = em;
	}
	
	public void addCondicion_ord(String nombre,String comparador, double valor,Indicador indicador) {
		// Get a new transaction
		EntityTransaction trx = em.getTransaction();
		final Condicion condicion_ord = new CondicionOrdenamiento(nombre, comparador, valor,indicador);
		try {
			trx.begin();
			em.persist(condicion_ord);
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		}
	}
	
	public void addCondicion_filtro(String nombre,String comparador, double valor,Indicador indicador) {
		// Get a new transaction
		EntityTransaction trx = em.getTransaction();
		final Condicion condicion_filtro = new CondicionFiltro(nombre, comparador, valor,indicador);
		try {
			trx.begin();
			em.persist(condicion_filtro);
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		}
	}
	
}
