package db;

import static org.junit.Assert.*;
import java.util.List;
import javax.persistence.EntityTransaction;
import org.junit.Test;
import dondeInvierto.Condicion;
import dondeInvierto.Indicador;
import dondeInvierto.CondicionFiltro;
import dondeInvierto.CondicionOrdenamiento;

public class DB_Condicion_Test extends DB_jpa_Test {

	@Test
	public void test_condicion() {
		EntityTransaction trx = em.getTransaction();
		IndicadorService indicador_db = new IndicadorService(em);
		Indicador indicador = new Indicador("Retorno sobre capital total",
				"Retorno sobre capital total = (Ingreso Neto - Dividendos) " + "/ Capital Total","DEFAULT");
		Long indicador_id = indicador_db.addIndicador(indicador);
		final Condicion condicion = new CondicionOrdenamiento("TEST_ORD", "<", (double) 100, indicador_id);
		// Start the transaction
		trx.begin();
		em.persist(condicion);
		trx.commit();
		List<Condicion> condiciones = em.createQuery("Select e FROM Condicion e WHERE e.tipo = :tipo", Condicion.class)
				.setParameter("tipo", "Ordenamiento").getResultList();
		Condicion condicion_test = condiciones.stream().findFirst().get();
		assertNotNull(condicion_test);
		assertEquals("TEST_ORD", condicion_test.getNombre());
	}

	@Test
	public void test_filtro() {
		EntityTransaction trx = em.getTransaction();
		IndicadorService indicador_db = new IndicadorService(em);
		Indicador indicador = new Indicador("Retorno sobre capital total",
				"Retorno sobre capital total = (Ingreso Neto - Dividendos) " + "/ Capital Total","DEFAULT");
		Long indicador_id = indicador_db.addIndicador(indicador);
		final Condicion condicion = new CondicionFiltro("TEST_FILTRO", "<", (double) 100, indicador_id);
		// Start the transaction
		trx.begin();
		em.persist(condicion);
		trx.commit();
		List<Condicion> condiciones = em.createQuery("Select e FROM Condicion e WHERE e.tipo = :tipo", Condicion.class)
				.setParameter("tipo", "Filtro").getResultList();
		Condicion condicion_test = condiciones.stream().findFirst().get();
		assertNotNull(condicion_test);
		assertEquals("TEST_FILTRO", condicion_test.getNombre());
	}
	
}