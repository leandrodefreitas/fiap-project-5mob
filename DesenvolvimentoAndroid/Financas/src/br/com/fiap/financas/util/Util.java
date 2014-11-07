package br.com.fiap.financas.util;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

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

	public static String formataDiaDaSemana(Date data){
		String dataFormatada = "";

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);

		if (data != null){
			int dia = calendar.get(Calendar.DAY_OF_WEEK);
			dataFormatada = diaDaSemana(dia);
		}

		return dataFormatada;
	}


	public static String diaDaSemana(int weekOfMonth) {
		String dia = "    ";
		
		switch(weekOfMonth) {
			case 1:
				dia = Constantes.DIA_SEMANA_DOMINGO;
				break;
			case 2:
				dia = Constantes.DIA_SEMANA_SEGUNDA;
				break;
			case 3:
				dia = Constantes.DIA_SEMANA_TERCA;
				break;
			case 4:
				dia = Constantes.DIA_SEMANA_QUARTA;
				break;
			case 5:
				dia = Constantes.DIA_SEMANA_QUINTA;
				break;
			case 6:
				dia = Constantes.DIA_SEMANA_SEXTA;
				break;
			case 7:
				dia = Constantes.DIA_SEMANA_SABADO;
				break;
			default:
				break;
		}
		return dia.substring(0, 3);
	}

	public static String formataMoedaBRL(Double valor){
		String valorFormatada = "";

		Locale ptBR = new Locale("pt", "BR");
	    NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(ptBR); 

		if (valor != null){
			valorFormatada = formatoMoeda.format(valor);
		}

		return valorFormatada;
	}

}
