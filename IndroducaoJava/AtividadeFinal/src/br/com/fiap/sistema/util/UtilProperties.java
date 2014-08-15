package br.com.fiap.sistema.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class UtilProperties {
	
	/**
	 * Método getProp() é resposável por acessar o arquivo de extensão 
	 * ".properties" 
	 * 
	 * @return
	 * @throws IOException
	 */
	public static Properties getProp() throws IOException {
		Properties props = new Properties();
		FileInputStream file = new FileInputStream(
				"./properties/autoatendimentoBancario.properties");
		props.load(file);
		return props;

	}

}
