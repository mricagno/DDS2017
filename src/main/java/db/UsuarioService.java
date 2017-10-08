package db;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Collections;
import org.hibernate.HibernateException;
import dondeInvierto.Empresa;
import dondeInvierto.Usuario;

public class UsuarioService {
	protected EntityManager em;

	public UsuarioService(EntityManager em) {
		this.em = em;
	};
	
	// Lee todas las empresas
	public List<Usuario> listUsuarios() {
		// Get a new transaction
		EntityTransaction trx = this.em.getTransaction();
		try {
			trx.begin();
			List<Usuario> usuarios = this.em.createQuery("FROM Usuario",Usuario.class).getResultList();
			trx.commit();
			return usuarios;
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
}
