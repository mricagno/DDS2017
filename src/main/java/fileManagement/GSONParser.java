package fileManagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GSONParser {

	public List<CuentaFromFile> parseJSON(BufferedReader br) {
		Gson gson = new Gson();
		Type type = new TypeToken<List<CuentaFromFile>>(){}.getType();
		List<CuentaFromFile> cuentas = gson.fromJson(br, type);
		for (CuentaFromFile c : cuentas) {
			c.setNombre(c.getNombre().toUpperCase());
		}

		try {
			br.close();
		} catch (IOException e) {
			System.out.println("Se produjo un error al intentar cerrar el BufferedReader.");
			e.printStackTrace();
		}
		
		return cuentas;
	}	
}
