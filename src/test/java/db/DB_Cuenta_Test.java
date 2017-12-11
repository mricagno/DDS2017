package db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import javax.persistence.EntityTransaction;
import org.junit.Test;
import dondeInvierto.Cuenta;
import dondeInvierto.Empresa;

public class DB_Cuenta_Test extends DB_jpa_Test {
	
	@Test
	public void guardarCuenta_exitoso() throws Exception {
		EntityTransaction trx = em.getTransaction();
		final Empresa empresa = new Empresa("FACEBOOK INC.");
		empresa.addCuenta(new Cuenta("FCF", "20151231", "3.99"));
		trx.begin();
		em.persist(empresa);
		trx.commit();
		List<Cuenta> cuentas = em.createNamedQuery("Cuenta.getAll", Cuenta.class).getResultList();
		assertNotNull(cuentas);
		assertEquals(1, cuentas.size());
	}
	
	@Test
	public void borrarCuenta_exitoso() throws Exception {
		EntityTransaction trx = em.getTransaction();
		final Empresa empresa = new Empresa("FACEBOOK INC.");
		empresa.addCuenta(new Cuenta("FCF", "20151231", "3.99"));
		trx.begin();
		em.persist(empresa);
		trx.commit();
		List<Cuenta> cuentas = em.createNamedQuery("Cuenta.getAll", Cuenta.class).getResultList();
		Cuenta cuenta = (Cuenta) em.find(Cuenta.class, cuentas.stream().findFirst().get().getId());
		trx.begin();
		cuenta.setValor((double) 300);
		trx.commit();
		assertNotNull(cuentas);
		assertEquals((double)300.0, cuenta.getValor(),(double)0);
	}
	
}