package dondeInvierto;

import java.util.ArrayList;
import java.util.List;

public class MercadoBursatil {
	private List<Empresa> empresas = new ArrayList<Empresa>();
	
	public MercadoBursatil(List<Empresa> empresas) {
		this.empresas = new ArrayList<Empresa>();
	}
	
	public List<Empresa> getEmpresas() {
		return this.empresas;
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
	
	public void addCuenta(Empresa empresa) {
		if (this.containsEmpresa(empresa) == -1) {
			this.empresas.add(empresa);
		} else {
			Cuenta cuenta = empresa.getCuentas().get(0);
			this.empresas.get(this.containsEmpresa(empresa)).addCuenta(cuenta);
		}
	}

}
