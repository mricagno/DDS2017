package dondeInvierto;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries(value = {@NamedQuery(name = "Metodologia.getAll", query = "SELECT b FROM Metodologia b")})
@Table(name = "metodologia")
public class Metodologia {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "CREADOR")
    private String creadoPor;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "metodologia_Filtro", joinColumns = @JoinColumn(name = "metodologia_id"),
            inverseJoinColumns = @JoinColumn(name = "condicionFiltro_id"))
    private Set<CondicionFiltro> condicionesFiltro;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "metodologia_Ordenamiento", joinColumns = @JoinColumn(name = "metodologia_id"),
            inverseJoinColumns = @JoinColumn(name = "condicionOrdenamiento_id"))
    private Set<CondicionOrdenamiento> condicionesOrdenamiento;
    @Transient
    private List<ResultadoCondicionado> listaFiltradaUOrdenada;
    @Transient
    private List<ResultadoCondicionado> listaOrdenaUnaCondicion;

    public Metodologia(String nombre, Set<CondicionFiltro> condicionesFiltro,
                       Set<CondicionOrdenamiento> condicionesOrdenamiento, String creador) {
        this.nombre = nombre;
        this.condicionesFiltro = condicionesFiltro;
        this.condicionesOrdenamiento = condicionesOrdenamiento;
        this.creadoPor = creador;
    }

    public Metodologia() {
    }

    /**
     * Devuelve el nombre de la metodologia.
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Devuelve el nombre del usuario que creó la metodología.
     */
    public String getCreador() {
        return this.creadoPor;
    }

    /**
     * Devuelve el indicador de la metodologia.
     */
    public Set<CondicionFiltro> getCondicionesFiltro() {
        return this.condicionesFiltro;
    }

    /**
     * Devuelve la tupla de comparado y valor a comparar de la metodologia.
     */
    public Set<CondicionOrdenamiento> getCondicionesOrdenamiento() {
        return this.condicionesOrdenamiento;
    }


    /**
     * Calcula el valor de una metodologia para una determinada empresa, en un periodo dado.
     */
    public void calcularMetodologia() {//(Metodologia metodologia){
        int posicion = 0;
        ResultadoCondicionado resultado;
        MercadoBursatil mercado = MercadoBursatil.INSTANCE;
        /**
         *Inicializo datos
         */
        List<String> listaNombres = new ArrayList<>();
        this.listaFiltradaUOrdenada = new ArrayList<ResultadoCondicionado>();
        this.listaOrdenaUnaCondicion = new ArrayList<ResultadoCondicionado>();
        for (CondicionFiltro condicion_limpiar_fil : this.getCondicionesFiltro()) {
            condicion_limpiar_fil.setResultadoCondicion(new ArrayList<ResultadoCondicionado>());
        }

        for (CondicionOrdenamiento condicion_limpiar_ord : this.getCondicionesOrdenamiento()) {
            condicion_limpiar_ord.setResultadoCondicion(new ArrayList<ResultadoCondicionado>());
        }
        if (this.getCondicionesFiltro().size() == 0) {

            mercado.getEmpresas().forEach(empresa -> listaNombres.add(empresa.getNombre()));
            mercado.getEmpresas().forEach(empresa -> listaFiltradaUOrdenada.add(new ResultadoCondicionado(empresa.getNombre(), 0)));

        } else {

            for (CondicionFiltro condicion : this.getCondicionesFiltro()) {
                listaFiltradaUOrdenada = condicion.evaluarCondicion(condicion);
            }

            for (int j = 0; j < listaFiltradaUOrdenada.size(); j++) {
                listaNombres.add(listaFiltradaUOrdenada.get(j).getNombre());
            }

        }

        for (CondicionOrdenamiento condicion : this.getCondicionesOrdenamiento()) {
            listaOrdenaUnaCondicion = condicion.evaluarCondicion(condicion, listaFiltradaUOrdenada);
            for (int i = 0; i < listaOrdenaUnaCondicion.size(); i++) {
                resultado = listaOrdenaUnaCondicion.get(i);
                posicion = listaNombres.indexOf(resultado.getNombre());
                listaFiltradaUOrdenada.get(posicion).setPosicionPonderable(i);
            }
        }

        listaFiltradaUOrdenada.sort(Comparator.comparing(ResultadoCondicionado::getPosicionPonderable));

    }

    public List<ResultadoCondicionado> getListaFiltradaUOrdenada() {
        return listaFiltradaUOrdenada;
    }

    public void setListaFiltradaUOrdenada(List<ResultadoCondicionado> listaFiltradaUOrdenada) {
        this.listaFiltradaUOrdenada = listaFiltradaUOrdenada;
    }
}