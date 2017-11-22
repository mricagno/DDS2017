package db;

import java.util.List;

import javax.persistence.EntityManager;

import dondeInvierto.*;

public class MercadoBursatilService {
    protected EntityManager em;

    public MercadoBursatilService(EntityManager em) {
        this.em = em;
    }

    public List<Empresa> generate_empresa_model() {
        EmpresaService empresa = new EmpresaService(em);
        List<Empresa> empresas = empresa.listEmpresas();
        return empresas;
    }

    public List<Indicador> generate_indicador_model() {
        IndicadorService indicador = new IndicadorService(em);
        List<Indicador> indicadores = indicador.listIndicadores();
        return indicadores;
    }

    public List<Usuario> generate_usuario_model() {
        UsuarioService usuario = new UsuarioService(em);
        List<Usuario> usuarios = usuario.listUsuarios();
        return usuarios;
    }

    public List<Metodologia> generate_metodologias_model() {
        MetodologiaService metodologia = new MetodologiaService(em);
        List<Metodologia> metodologias = metodologia.getMetodologias();
        return metodologias;
    }

    public List<IndicadorCalculado> generate_indicadoresCalculados_model() {
        IndicadorCalculadoService indicadorCalculadoService = new IndicadorCalculadoService(em);
        List<IndicadorCalculado> indicadorCalculados = indicadorCalculadoService.listIndicadoresCalculados();
        return indicadorCalculados;
    }

}
