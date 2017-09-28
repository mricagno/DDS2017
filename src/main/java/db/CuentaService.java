package db;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.text.ParseException;
import java.util.Iterator;
import org.hibernate.HibernateException;
import dondeInvierto.Cuenta;
import dondeInvierto.Empresa;

public class CuentaService {
	protected EntityManager em;
	public CuentaService(EntityManager em) {
		this.em = em;
	};
//	 Crear cuenta en DB
	public Long addCuenta(String tipo, String periodo, String valor, Empresa empresa) throws ParseException {
		empresa.addCuenta(new Cuenta(tipo, periodo, valor));
		// Get a new transaction
		EntityTransaction trx = this.em.getTransaction();
		Long cuentaID = null;
		try {
			trx.begin();
			this.em.persist(empresa);
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		} 
		return cuentaID;
	}

	//Devuelve todas las cuentas 
	public void listCuentasAll() {
		// Get a new transaction
		EntityTransaction trx = this.em.getTransaction();
		try {
			trx.begin();
			List<Cuenta> cuentas = this.em.createQuery("FROM Cuenta",Cuenta.class).getResultList();
			System.out.println("LISTA DE Cuentas");
			for (Iterator<Cuenta> iterator = cuentas.iterator(); iterator.hasNext();) {
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
		} 
	}
	
	//	 Lee las cuentas de una empresa
	public void listCuentas(Empresa empresa) {
		// Get a new transaction
		EntityTransaction trx = this.em.getTransaction();
		try {
			trx.begin();
			List<Cuenta> cuentas = this.em.createQuery("FROM Cuenta WHERE empresa_id = :eid",Cuenta.class).setParameter("eid", empresa).getResultList();
			System.out.println("LISTA DE Cuentas de empresa " + empresa.getNombre());
			for (Iterator<Cuenta> iterator = cuentas.iterator(); iterator.hasNext();) {
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
		} 
	}

	//	 Actualizar Cuenta
	public void updateCuenta(Long id, Double valor) {
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
