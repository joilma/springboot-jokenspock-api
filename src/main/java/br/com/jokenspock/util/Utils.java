package br.com.jokenspock.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Utils {
	
	public static String getResourceFileAsString(String fileName) {
	    InputStream is = getResourceFileAsInputStream(fileName);
	    if (is != null) {
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	        return (String)reader.lines().collect(Collectors.joining(System.lineSeparator()));
	    } else {
	        return null;
	    }
	}

	public static InputStream getResourceFileAsInputStream(String fileName) {
	    ClassLoader classLoader = Utils.class.getClassLoader();
	    return classLoader.getResourceAsStream(fileName);
	}

	public static File createFile(String nomeArquivo) throws IOException{
		ClassLoader classLoader = Utils.class.getClassLoader();
		File arquivo = new File(classLoader.getResource(nomeArquivo).getPath());
		
		if(!arquivo.createNewFile()){
			
			arquivo.delete();
			
			arquivo.createNewFile();
			
		}
		
		return arquivo;
		
	}
}
