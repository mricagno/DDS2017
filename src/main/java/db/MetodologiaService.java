package db;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.hibernate.HibernateException;
import dondeInvierto.Indicador;

public class MetodologiaService {
	protected EntityManager em;

	public MetodologiaService(EntityManager em) {
		this.em = em;
	}
	
	
	
}
