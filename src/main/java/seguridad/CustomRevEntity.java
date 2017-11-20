package seguridad;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.DefaultRevisionEntity;

import javax.persistence.Entity;

@Entity
@RevisionEntity(CustomListener.class)
public class CustomRevEntity extends DefaultRevisionEntity {
    private String used_file;

    public String getUsed_file() {
        return used_file;
    }

    public void setUsed_file(String file) {
        this.used_file = file;
    }

}
