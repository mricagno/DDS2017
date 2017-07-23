package drools;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dondeInvierto.Empresa;
import dondeInvierto.MercadoBursatil;
import dondeInvierto.Metodologia;

public class TestRuleExecution {

	@Test
	public void test() {
		List<Empresa> empresas = new ArrayList<Empresa>(); 
		Metodologia prueba = new Metodologia("Prueba", "http://localhost:8080/");
		MercadoBursatil mercado = new MercadoBursatil(empresas);
		
		prueba.evaluarMetodologia(mercado);
	}

}
