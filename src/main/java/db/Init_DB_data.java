package db;

import java.text.ParseException;

import javax.persistence.EntityManager;

import dondeInvierto.Empresa;

public class Init_DB_data {

	public Init_DB_data(EntityManager em) throws ParseException {
		
		 EmpresaService  empresa = new EmpresaService(em);
		 CuentaService cuenta = new CuentaService(em);
		 empresa.addEmpresa("Facebook Inc.");
		 empresa.addEmpresa("Tesla Inc.");
		 empresa.addEmpresa("Twitter Inc.");
		 Empresa facebook = empresa.getEmpresa_name("Facebook Inc.");
		 cuenta.addCuenta("EBITDA", "20151231", "8162", facebook);
		 cuenta.addCuenta("EBITDA", "20161231", "14870", facebook);
		 cuenta.addCuenta("FCF", "20151231", "3.99", facebook);
		 Empresa tesla = empresa.getEmpresa_name("Tesla Inc.");
		 cuenta.addCuenta("EBITDA", "20151231", "213", tesla);
		 cuenta.addCuenta("EBITDA", "20161231", "630", tesla);
		 Empresa twitter = empresa.getEmpresa_name("Twitter Inc.");
		 cuenta.addCuenta("EBITDA", "20161231", "751", twitter); 
		 em.close();
	}

}
