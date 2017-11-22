package db;

import dondeInvierto.Indicador;
import dondeInvierto.IndicadorCalculado;
import org.hibernate.HibernateException;

import java.util.Collections;
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

    public void borrar_indicadores() {
        // Get a new transaction
        EntityTransaction trx = em.getTransaction();
        try {
            trx.begin();
            em.createQuery("delete from IndicadorCalculado ").executeUpdate();
            trx.commit();
        } catch (HibernateException e) {
            if (trx != null)
                trx.rollback();
            e.printStackTrace();
        }
    }

    ;

    // Lee todos los indicadores Calculados
    public List<IndicadorCalculado> listIndicadoresCalculados() {
        // Get a new transaction
        EntityTransaction trx = this.em.getTransaction();
        try {
            trx.begin();
            List<IndicadorCalculado> indicadoresCalculados = this.em.createQuery("FROM IndicadorCalculado ", IndicadorCalculado.class).getResultList();
            trx.commit();
            return indicadoresCalculados;
        } catch (HibernateException e) {
            if (trx != null)
                trx.rollback();
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
