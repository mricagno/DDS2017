package fileManagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import dondeInvierto.Cuenta;
import dondeInvierto.Empresa;

public class GSONParser {

	public List<Empresa> parseJSON(BufferedReader br) {
		Gson gson = new Gson();
		Type type = new TypeToken<List<CuentaFromFile>>(){}.getType();
		List<CuentaFromFile> cuentas = gson.fromJson(br, type);
		
		List<Empresa> resultado = new ArrayList<Empresa>();
		
		for (int i=0; i<cuentas.size(); i++) {
			resultado.add(crearCuenta(cuentas.get(i)));
		}
		
		try {
			br.close();
		} catch (IOException e) {
			System.out.println("Se produjo un error al intentar cerrar el BufferedReader.");
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	public Empresa crearCuenta(CuentaFromFile cuentaArchivo) {

		Empresa empresa = new Empresa(cuentaArchivo.getNombre(), cuentaArchivo.getSigla());
		
		try {
			Date periodo = new SimpleDateFormat("yyyyMMdd").parse(cuentaArchivo.getPeriodo());
			double valor = Double.parseDouble(cuentaArchivo.getValor());			
			
			Cuenta cuenta = new Cuenta(cuentaArchivo.getTipo(), periodo, valor);
			
			empresa.addCuenta(cuenta);
		
		} catch (ParseException e) {
			System.out.println("Se produjo un error al intentar parsear la fecha de la cuenta.");
			e.printStackTrace();
		}
		
		return empresa;
		
	}
	
}
