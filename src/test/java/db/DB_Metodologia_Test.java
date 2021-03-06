package db;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityTransaction;
import org.junit.Test;
import dondeInvierto.*;

public class DB_Metodologia_Test extends DB_jpa_Test {

	@Test
	public void guardarMetodologia_exitoso() {
		EntityTransaction trx = em.getTransaction();
		CondicionFiltro filtro1 = new CondicionFiltro("CondFiltroRoe", ">", 1.00,
				new Indicador("ROE", "ROE = ( Ingreso Neto - Dividendos) / Capital Total","mricagno"));
		CondicionFiltro filtro2 = new CondicionFiltro("CondFiltroPropDeuda", ">", 1.00, new Indicador(
				"Proporcion De Deuda", "Proporcion De Deuda = Dividendos / ( Capital Total - Dividendos )","mricagno"));
		CondicionFiltro filtro3 = new CondicionFiltro("CondFiltroMargen", ">", 1.00,
				new Indicador("Margen", "Margen = Capital Total - Dividendos","mricagno"));
		Set<CondicionFiltro> condicionesFiltro = new HashSet<>();
		Set<CondicionOrdenamiento> condicionesOrdenamiento = new HashSet<>();

		CondicionOrdenamiento orden1 = new CondicionOrdenamiento("CondOrdAscendente", "ascendente", 0,
				new Indicador("Indicador Vacio", "Indicador Vacio = 0","mricagno"));

		// Start the transaction
		trx.begin();
		em.persist(filtro1);
		em.persist(filtro2);
		em.persist(filtro3);
		em.persist(orden1);
		condicionesFiltro.add(filtro1);
		condicionesFiltro.add(filtro2);
		condicionesFiltro.add(filtro3);
		condicionesOrdenamiento.add(orden1);
		Metodologia metodologia = new Metodologia("Metodologia1", condicionesFiltro, condicionesOrdenamiento,"mricagno");
		em.persist(metodologia);
		trx.commit();
		List<Metodologia> metodologias = em.createNamedQuery("Metodologia.getAll", Metodologia.class).getResultList();
		assertNotNull(metodologias);
		assertEquals(1, metodologias.size());
	}

}
