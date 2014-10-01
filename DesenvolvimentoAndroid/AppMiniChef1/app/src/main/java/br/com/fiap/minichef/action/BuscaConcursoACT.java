package br.com.fiap.minichef.action ;

import java.io.IOException ;
import java.util.Arrays ;
import java.util.Date ;
import java.util.HashMap ;
import java.util.Map ;

import org.json.JSONException ;
import org.json.JSONObject ;

import android.util.Log ;
import br.com.fiap.minichef.common.vo.Sorteio;
import br.com.fiap.minichef.util.FIAPHttp;

public class BuscaConcursoACT
{

	private final String url ;

	public BuscaConcursoACT( String url ) throws IOException
	{
		this.url = url ;
	}

	public Sorteio buscarConcurso( String numeroConcurso )
	{
		Map<String, Object> params = new HashMap<String, Object>( ) ;

		if (numeroConcurso != null)
		{
			params.put( "numero", numeroConcurso ) ;
		}

		String jsonResultado = FIAPHttp.getInstance( FIAPHttp.JAKARTA ).doPost( url, params ) ;
		if (jsonResultado.contains( "Sequence contains no matching element" ))
		{
			Log.i( "RYFRYF", "nao encontrou numero, retornando NULL" ) ;
			return null ;
		}
		jsonResultado = jsonResultado.substring( 1, jsonResultado.length( ) - 1 ) ;

		Log.i( "RYF", jsonResultado ) ;

		JSONObject userObject ;
		Sorteio sorteio = new Sorteio( ) ;
		try
		{
			userObject = new JSONObject( jsonResultado ) ;
			sorteio.setConcurso( userObject.getInt( "Concurso" ) ) ;
			String strMilisegundos = userObject.getString( "Data" ).replaceAll( "\\D", "" ) ;
			sorteio.setData( new Date( Long.valueOf( strMilisegundos ) ) ) ;
			String strDezenas = userObject.getString( "Dezenas" ) ;
			Log.i( "RYF", strDezenas ) ;

			sorteio.setDezenas( strDezenas.split( "\\|" ) ) ;

			Log.i( "RYF", "numero: " + sorteio.getConcurso( ) ) ;
			Log.i( "RYF", "data: " + sorteio.getData( ) ) ;
			Log.i( "RYF", "dezenas: " + Arrays.asList( sorteio.getDezenas( ) ) ) ;

		}
		catch (JSONException e)
		{
			e.printStackTrace( ) ;
		}

		return sorteio ;
	}
}
