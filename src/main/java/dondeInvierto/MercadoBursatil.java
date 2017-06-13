package dondeInvierto;

import java.util.ArrayList;
import java.util.List;

public class MercadoBursatil {
	private List<Empresa> empresas = new ArrayList<Empresa>();
	private List<Indicador> indicadores = new ArrayList<Indicador>();
	
	public MercadoBursatil(List<Empresa> empresas) {
		this.empresas = new ArrayList<Empresa>();
		this.indicadores = new ArrayList<Indicador>();
	}
	
	public List<Empresa> getEmpresas() {
		return this.empresas;
	}
	
	public List<Indicador> getIndicadores() {
		return this.indicadores;
	}
	
	public int containsEmpresa(Empresa empresa) {
		int foundFlag = -1;
		
		for (int i = 0; i < this.getEmpresas().size(); i++) {
			if ( empresa.getSigla().equals(this.getEmpresas().get(i).getSigla()) ) {
					foundFlag = i;
			}
		}
		
		return foundFlag;
	}
	
	public int containsIndicador(Indicador indicador) {
		int foundFlag = -1;
		
		for (int i = 0; i < this.getIndicadores().size(); i++) {
			if ( indicador.getNombre().equals(this.getIndicadores().get(i).getNombre()) ) {
					foundFlag = i;
			}
		}
		
		return foundFlag;
	}
	
	public void addCuenta(Empresa empresa) {
		if (this.containsEmpresa(empresa) == -1) {
			this.empresas.add(empresa);
		} else {
			Cuenta cuenta = empresa.getCuentas().get(0);
			this.empresas.get(this.containsEmpresa(empresa)).addCuenta(cuenta);
		}
	}
	
	public void addIndicador(Indicador indicador) {
		if (this.containsIndicador(indicador) == -1) {
			this.indicadores.add(indicador);
		} else {
			System.out.println("Ya existe un indicador con ese nombre.");
		}
	}

}
