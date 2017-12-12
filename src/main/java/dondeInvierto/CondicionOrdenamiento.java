package dondeInvierto;

import javax.persistence.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@DiscriminatorValue(value = "Ordenamiento")
public class CondicionOrdenamiento extends Condicion {
    @Transient
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    @Transient
    LocalDate localDate = LocalDate.now();
    @Transient
    double resultadoIndicador;

    public CondicionOrdenamiento(String nombre, String comparador, double valor, Indicador indicador) {
        super(nombre, comparador, valor, indicador);
        // TODO Auto-generated constructor stub
    }

    public CondicionOrdenamiento() {
    }

    public List<ResultadoCondicionado> getResultadoCondicion() {
        return this.resultadoCondicion;
    }

    // Se define nuevamente evaluarCondicion para las condiciones de ordenamiento
    @Override
    public List<ResultadoCondicionado> evaluarCondicion(Condicion condicion) {

        Collections.sort(resultadoCondicion);
        for (int i = 0; i < resultadoCondicion.size(); i++) {
            System.out.println(resultadoCondicion);
        }

        if (condicion.getComparador() == "descendente") {
            Collections.reverse(resultadoCondicion);
        }
        return resultadoCondicion;
    }

    public List<ResultadoCondicionado> evaluarCondicion(Condicion condicion,
                                                        List<ResultadoCondicionado> resultadoCondicionado) {
        Calendar calendar = new GregorianCalendar();
        double contador = 0;

        if (condicion.getValor() == 0) {
            String empresaNombre;
            Empresa empresa = null;
            for (int i = 0; i < resultadoCondicionado.size(); i++) {
                empresaNombre = resultadoCondicionado.get(i).getNombre();
                empresa = mercado.getEmpresa(empresaNombre);
                List<String> listaPeriodos = new ArrayList<>();
                for (Cuenta cuenta : empresa.getCuentas()) {
                    if (!listaPeriodos.contains(cuenta.getPeriodoAsString())) {
                        listaPeriodos.add(cuenta.getPeriodoAsString());
                        resultadoIndicador = mercado.getIndicadorCalculado(empresa.getId(), condicion.getIndicador().getId(), cuenta.getPeriodoAsString());
                        contador += resultadoIndicador;
                    }
                }
                resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(), contador));
                contador = 0;

            }
        } else {
            String empresaNombre;
            Empresa empresa;
            for (int i = 0; i < resultadoCondicionado.size(); i++) {
                empresaNombre = resultadoCondicionado.get(i).getNombre();
                empresa = mercado.getEmpresa(empresaNombre);
                List<String> listaPeriodos = new ArrayList<>();
                for (Cuenta cuenta : empresa.getCuentas()) {
                    if (!listaPeriodos.contains(cuenta.getPeriodoAsString())) {
                        listaPeriodos.add(cuenta.getPeriodoAsString());
                        calendar.setTime(cuenta.getPeriodo());
                        if (localDate.getYear() - calendar.get(Calendar.YEAR) <= condicion.getValor()) {
                            resultadoIndicador = mercado.getIndicadorCalculado(empresa.getId(), condicion.getIndicador().getId(), cuenta.getPeriodoAsString());
                            contador += resultadoIndicador;
                        }
                    }
                }
                resultadoCondicion.add(new ResultadoCondicionado(empresa.getNombre(), contador));
                contador = 0;
            }
        }

        Collections.sort(resultadoCondicion);

        if (condicion.getComparador() == "ascendente") {
            Collections.reverse(resultadoCondicion);
        }
        return resultadoCondicion;

    }

    public static void reverse(List<ResultadoCondicionado> resultadoCondicion) {
        if (resultadoCondicion == null || resultadoCondicion.size() <= 1) {
            return;
        }
        for (int i = 0; i < resultadoCondicion.size() / 2; i++) {
            ResultadoCondicionado temp = resultadoCondicion.get(i);
            resultadoCondicion.set(i, resultadoCondicion.get(resultadoCondicion.size() - 1 - i));
            resultadoCondicion.set(resultadoCondicion.size() - 1 - i, temp);
        }
    }

}