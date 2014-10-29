package br.com.fiap.financas.util;

public class Util {
	
	public static String imprimeDataFormatoBR(String data){
		String dataFormatada = null;
		
		if (data.length() > 0){
			
			String dia;
			String mes;
			String ano;			

			dia = data.substring(8, 10);
			mes = data.substring(5,	7);
			ano = data.substring(0, 4);
			
			dataFormatada = dia + "/" + mes + "/" + ano;
		}
		
		return dataFormatada;
		
	}
	
	
}
