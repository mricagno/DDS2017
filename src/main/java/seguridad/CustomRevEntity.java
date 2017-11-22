package seguridad;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.DefaultRevisionEntity;

import javax.persistence.Entity;

@Entity
@RevisionEntity(CustomListener.class)
public class CustomRevEntity extends DefaultRevisionEntity {
    private String used_file;
    private String usuario;

    public String getUsed_file() {
        return used_file;
    }

    public void setUsed_file(String file) {
        this.used_file = file;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
