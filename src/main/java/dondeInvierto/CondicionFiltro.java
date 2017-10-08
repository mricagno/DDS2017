package dondeInvierto;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(value = "Filtro")
public class CondicionFiltro extends Condicion {
	public CondicionFiltro(String nombre, String comparador, double valor, Indicador indicador) {
		super(nombre, comparador, valor, indicador);
		// TODO Auto-generated constructor stub
	}

	public List<ResultadoCondicionado> getResultadoCondicion() {
		return this.resultadoCondicion;
	}

	// Se define nuevamente evaluarCondicion para las condiciones de filtro
	@Override
	public List<ResultadoCondicionado> evaluarCondicion(Condicion condicion) {
		double resultadoIndicador;
		switch (condicion.getComparador()) {
		case "<":
			for (Empresa empresa : empresas) {
				List<String> listaPeriodos = new ArrayList<>();
				for (Cuenta cuenta : empresa.getCuentas()) {
					if (!listaPeriodos.contains(cuenta.getPeriodoAsString())) {
						listaPeriodos.add(cuenta.getPeriodoAsString());
						resultadoIndicador = condicion.getIndicador().getValorFor(empresa, cuenta.getPeriodoAsString());
						if (resultadoIndicador < condicion.getValor()) {
							resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(), resultadoIndicador));
						}
					}
				}
			}
			return resultadoCondicion;

		case ">":
			for (Empresa empresa : empresas) {
				List<String> listaPeriodos = new ArrayList<>();
				for (Cuenta cuenta : empresa.getCuentas()) {
					if (!listaPeriodos.contains(cuenta.getPeriodoAsString())) {
						listaPeriodos.add(cuenta.getPeriodoAsString());
						resultadoIndicador = condicion.getIndicador().getValorFor(empresa, cuenta.getPeriodoAsString());
						if (resultadoIndicador > condicion.getValor()) {
							resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(), resultadoIndicador));
						}
					}
				}
			}
			return resultadoCondicion;

		case "==":
			double valorCondicion = (double) condicion.getValor();
			for (Empresa empresa : empresas) {
				List<String> listaPeriodos = new ArrayList<>();
				for (Cuenta cuenta : empresa.getCuentas()) {
					if (!listaPeriodos.contains(cuenta.getPeriodoAsString())) {
						listaPeriodos.add(cuenta.getPeriodoAsString());
						resultadoIndicador = condicion.getIndicador().getValorFor(empresa, cuenta.getPeriodoAsString());
						if (resultadoIndicador == valorCondicion) {
							resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(), resultadoIndicador));
						}
					}
				}
			}
			return resultadoCondicion;

		case "<=":
			for (Empresa empresa : empresas) {
				new ArrayList<>();
				for (Cuenta cuenta : empresa.getCuentas()) {
					resultadoIndicador = condicion.getIndicador().getValorFor(empresa, cuenta.getPeriodoAsString());
					if (resultadoIndicador <= condicion.getValor()) {
						resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(), resultadoIndicador));
					}
				}
			}
			return resultadoCondicion;

		case ">=":
			for (Empresa empresa : empresas) {
				List<String> listaPeriodos = new ArrayList<>();
				for (Cuenta cuenta : empresa.getCuentas()) {
					if (!listaPeriodos.contains(cuenta.getPeriodoAsString())) {
						listaPeriodos.add(cuenta.getPeriodoAsString());
						resultadoIndicador = condicion.getIndicador().getValorFor(empresa, cuenta.getPeriodoAsString());
						if (resultadoIndicador >= condicion.getValor()) {
							resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(), resultadoIndicador));
						}
					}
				}
			}
			return resultadoCondicion;

		case "!=":
			for (Empresa empresa : empresas) {
				List<String> listaPeriodos = new ArrayList<>();
				for (Cuenta cuenta : empresa.getCuentas()) {
					if (!listaPeriodos.contains(cuenta.getPeriodoAsString())) {
						listaPeriodos.add(cuenta.getPeriodoAsString());
						resultadoIndicador = condicion.getIndicador().getValorFor(empresa, cuenta.getPeriodoAsString());
						if (resultadoIndicador != condicion.getValor()) {
							resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(), resultadoIndicador));
						}
					}
				}
			}
			return resultadoCondicion;

		case "filtrarAntiguedadMenor":
			for (Empresa empresa : empresas) {
				if (empresa.getAntiguedad() < condicion.getValor()) {
					resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(), empresa.getAntiguedad()));
				}
			}
			return resultadoCondicion;

		case "filtrarAntiguedadMayor":
			for (Empresa empresa : empresas) {
				if (empresa.getAntiguedad() > condicion.getValor()) {
					resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(), empresa.getAntiguedad()));
				}
			}
			return resultadoCondicion;

		// case "anios":

		}
		return resultadoCondicion;
	}
}