package db;

import static org.junit.Assert.*;
import java.util.List;
import javax.persistence.EntityTransaction;
import org.junit.Test;
import dondeInvierto.Indicador;

public class DB_Indicador_Test extends DB_jpa_Test {

	@Test
	public void guardarIndicador_exitoso() {
		EntityTransaction trx = em.getTransaction();
		final Indicador indicador = new Indicador("Ingreso Neto", 
				"Ingreso Neto = Ingreso Neto En Operaciones Continuas + "
				+ "Ingreso Neto En Operaciones Discontinuadas");
		trx.begin();
		em.persist(indicador);
		trx.commit();
		List<Indicador> indicadores = em.createNamedQuery("Indicador.getAll", Indicador.class).getResultList();
		assertNotNull(indicadores);
		assertEquals(1, indicadores.size());
	}
}
