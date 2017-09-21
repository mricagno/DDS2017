package dondeInvierto;

import javax.persistence.*;
import java.text.ParseException;
@Entity
@DiscriminatorValue(value="Filtro")
public class CondicionFiltro extends Condicion {

	public CondicionFiltro(String nombre, String comparador, Indicador indicador, int valor) {
		super(nombre, comparador, indicador, valor);
		// TODO Auto-generated constructor stub
	}

}
