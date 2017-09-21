package dondeInvierto;

import javax.persistence.*;
import java.text.ParseException;

@Entity
@DiscriminatorValue(value="Ordenamiento")
public class CondicionOrdenamiento extends Condicion {
	int nuevo_parametro;
	
	public CondicionOrdenamiento(String nombre, String comparador, Indicador indicador, int valor, int test) {
		super(nombre, comparador, indicador, valor);
		this.nuevo_parametro = test;
		// TODO Auto-generated constructor stub
	}

}
