package seguridad;

import dondeInvierto.MercadoBursatil;
import org.hibernate.envers.RevisionListener;

public class CustomListener implements RevisionListener {
    private MercadoBursatil mercado = MercadoBursatil.INSTANCE;

    public void newRevision(Object revisionEntity) {
        CustomRevEntity customRevEntity = (CustomRevEntity) revisionEntity;
        customRevEntity.setUsed_file(mercado.get_lastFileLoaded());
    }
}
