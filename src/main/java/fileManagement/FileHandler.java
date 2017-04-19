package fileManagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
//import java.util.ArrayList;
import java.util.List;

import dondeInvierto.Empresa;

public class FileHandler extends GSONParser {
	
	public BufferedReader readFile(String filepath) {
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(filepath));
		} catch (FileNotFoundException e) {
			System.out.println("El archivo especificado no pudo ser encontrado.");
			e.printStackTrace();
		}
		
		return br;
	}
	
	public String determineType(String filepath) {
		return filepath.substring(filepath.indexOf('.', 0));
	}

	/* public List<Empresa> dispatchParser(String extension, BufferedReader br) {
	
		List<Empresa> cuentas = new ArrayList<Empresa>();
		
		switch (extension) {
			case ".json":
				parseJSON(br);
				break;
			case ".xml":
				break;
			case ".csv":
				break;
		}
		
		return parseJSON(br);
		
	} */
	
	public List<Empresa> dispatchParser(BufferedReader br) {
		return parseJSON(br);
	}
	
}
