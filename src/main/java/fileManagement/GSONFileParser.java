package fileManagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GSONFileParser {
	
	public List<ImportedCuenta> readFile(String filepath) {

		List<ImportedCuenta> cuentas = null;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			
			Gson gson = new Gson();
			
			Type tipoListaCuentas = new TypeToken<List<ImportedCuenta>>(){}.getType();
			cuentas = gson.fromJson(br, tipoListaCuentas);
			

		} catch (FileNotFoundException e) {
			System.out.println("No se encontró el archivo seleccionado.");
			e.printStackTrace();
		}
		
		return cuentas;
	}
}
