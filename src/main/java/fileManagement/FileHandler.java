package fileManagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class FileHandler extends GSONParser {
	
	public FileHandler() {
	}
	
	public BufferedReader readFile(String filepath) throws FileNotFoundException {
		BufferedReader br = null;
		br = new BufferedReader(new FileReader(filepath));		
		return br;
	}
	
	/* public String determineType(String filepath) {
		return filepath.substring(filepath.indexOf('.', 0));
	}

	public List<Empresa> dispatchParser(String extension, BufferedReader br) {
	
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
	
	public List<CuentaFromFile> dispatchParser(BufferedReader br) {
		return parseJSON(br);
	}
	
}
