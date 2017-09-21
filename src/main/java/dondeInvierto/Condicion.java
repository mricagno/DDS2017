package dondeInvierto;

import javax.persistence.*;
import java.text.ParseException;

@Entity
@Table(name = "condicion")
@DiscriminatorColumn(name = "tipo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Condicion {
	@Id
	private String nombre;
	private String comparador;
	//private Indicador indicador;
	private int valor;

	/**
	 * Constructor de la condición.
	 */
	public Condicion(String nombre, String comparador, Indicador indicador, int valor) {
		this.nombre = nombre;
		this.comparador = comparador;
		this.valor = valor;
	}

	/**
	 * Devuelve el nombre de la condicion.
	 */

	public String getNombre() {
		return this.nombre;
	}

	/**
	 * Devuelve el comparador de la condicion.
	 */
	@Column(name = "COMPARADOR")
	public String getComparador() {
		return this.comparador;
	}

	/**
	 * Devuelve el valor a comparar de la condicion.
	 */
	@Column(name = "VALOR")
	public int getValor() {
		return this.valor;
	}

	public boolean evaluarCondicion(double resultadoIndicador, Condicion condicion) {

		boolean resultadoCondicionado = true;
		switch (condicion.getComparador()) {

		case "<":
			resultadoCondicionado = resultadoIndicador < condicion.getValor();
			break;

		case ">":
			resultadoCondicionado = resultadoIndicador > condicion.getValor();
			break;

		case "==":
			resultadoCondicionado = resultadoIndicador == condicion.getValor();
			break;

		case "<=":
			resultadoCondicionado = resultadoIndicador <= condicion.getValor();
			break;

		case ">=":
			resultadoCondicionado = resultadoIndicador >= condicion.getValor();
			break;

		}
		return resultadoCondicionado;

	}
}
