package db;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.text.ParseException;
import java.util.Collections;
import org.hibernate.HibernateException;
import dondeInvierto.Cuenta;
import dondeInvierto.Empresa;

public class CuentaService {
	protected EntityManager em;

	public CuentaService(EntityManager em) {
		this.em = em;
	};

	// Crear cuenta en DB de empresa nueva
	public Long addCuenta(String tipo, String periodo, String valor, Empresa empresa) throws ParseException {
		// Get a new transaction
		EntityTransaction trx = this.em.getTransaction();
		Long cuentaID = null;
		try {
			trx.begin();
			empresa.addCuenta(new Cuenta(tipo, periodo, valor));
			this.em.persist(empresa);
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		}
		return cuentaID;
	}

	// Crear cuenta en DB de empresa existente
	public void addCuenta_existCompany(String tipo, String periodo, String valor, Empresa empresa)
			throws ParseException {

		// Get a new transaction
		EntityTransaction trx = this.em.getTransaction();
		Empresa empresa_db = null;
		// Long cuentaID = null;
		try {
			trx.begin();
			List<Empresa> empresas = this.em
					.createQuery("Select e FROM Empresa e WHERE e.nombre = :nombre", Empresa.class)
					.setParameter("nombre", empresa.getNombre()).getResultList();
			empresa_db = empresas.stream().findFirst().get();
			empresa_db.addCuenta(new Cuenta(tipo, periodo, valor));
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		}
	}

	// Devuelve todas las cuentas
	public List<Cuenta> listCuentasAll() {
		// Get a new transaction
		EntityTransaction trx = this.em.getTransaction();
		try {
			trx.begin();
			List<Cuenta> cuentas = this.em.createQuery("FROM Cuenta", Cuenta.class).getResultList();
			trx.commit();
			return cuentas;
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	// Lee las cuentas de una empresa
	public List<Cuenta> listaCuentas(Empresa empresa) {
		// Get a new transaction
		EntityTransaction trx = this.em.getTransaction();
		try {
			trx.begin();
			List<Cuenta> cuentas = this.em.createQuery("FROM Cuenta WHERE id = :eid", Cuenta.class)
					.setParameter("eid", empresa.getId()).getResultList();
			trx.commit();
			return cuentas;
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	// Actualizar Cuenta
	public void updateCuenta2(Long id, Double valor) {
		// Get a new transaction
		EntityTransaction trx = this.em.getTransaction();
		Cuenta cuenta_db = null;
		try {

			Cuenta cuenta = this.em.find(Cuenta.class, id);
			trx.begin();
			cuenta.setValor(valor);
			// Cuenta cuenta = this.em.find(Cuenta.class, id);
			List<Cuenta> cuentas = this.em.createQuery("Select c FROM Cuenta c WHERE c.cuentaID = :cid", Cuenta.class)
					.setParameter("cid", id).getResultList();
			cuenta_db = cuentas.stream().findFirst().get();
			System.out.println(valor + "existeeeee");
			cuenta_db.setValor(valor);
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		}
	}

	// Borrar Cuenta
	public void deleteCuenta(Long id) {
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
		}
	}

}
