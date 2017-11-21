package db;

import dondeInvierto.Indicador;
import dondeInvierto.IndicadorCalculado;
import org.hibernate.HibernateException;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class IndicadorCalculadoService {
    protected EntityManager em;

    public IndicadorCalculadoService(EntityManager em) {
        this.em = em;
    }

    ;

    //Crear indicador en DB
    public void addIndicadoresCalculado(List<IndicadorCalculado> indicadoresCalculado) {
        // Get a new transaction
        EntityTransaction trx = em.getTransaction();

        try {
            trx.begin();
            indicadoresCalculado.forEach(indicador -> {
                em.persist(indicador);
            });

            trx.commit();
        } catch (HibernateException e) {
            if (trx != null)
                trx.rollback();
            e.printStackTrace();
        }
    }

    ;

}
