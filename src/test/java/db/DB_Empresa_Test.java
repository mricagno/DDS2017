package db;

import static org.junit.Assert.*;
import java.util.List;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.junit.Test;
import dondeInvierto.Empresa;

public class DB_Empresa_Test extends DB_jpa_Test {

	@Test
	public void guardarEmpresa_exitoso() {
		EntityTransaction trx = em.getTransaction();
		final Empresa empresa = new Empresa("Facebook Inc.");
		trx.begin();
		em.persist(empresa);
		trx.commit();
		List<Empresa> empresas = em.createNamedQuery("Empresa.getAll", Empresa.class).getResultList();
		assertNotNull(empresas);
		assertEquals(1, empresas.size());
	}
	
	@Test
	public void actualizarEmpresa_exitoso() {
		Empresa empresa = em.find(Empresa.class, new Long(1));
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		empresa.setNombre("Google");
		trx.commit();
		List<Empresa> empresas = em.createNamedQuery("Empresa.getAll", Empresa.class).getResultList();
		assertNotNull(empresas);
		assertEquals("Google", empresas.stream().findFirst().get().getNombre());
	}
	
	@Test
	public void borrarEmpresa_exitoso() {
		Empresa empresa = em.find(Empresa.class, new Long(1));
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		em.remove(empresa);
		trx.commit();
		List<Empresa> empresas = em.createNamedQuery("Empresa.getAll", Empresa.class).getResultList();
		assertNotNull(empresas);
		assertEquals(0, empresas.size());
	}
	
	@Test
	public void getEmpresa_exitoso() {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		List<Empresa> empresas = em.createQuery("Select e FROM Empresa e WHERE e.nombre = :nombre",Empresa.class)
                .setParameter("nombre", "Facebook Inc.").getResultList();
		Empresa empresa = empresas.stream().findFirst().get();
		trx.commit();
		assertNotNull(empresa);
		assertEquals("Facebook Inc.", empresa.getNombre());
		
	}

}