package br.com.fiap.minichef.activity ;

import java.text.NumberFormat ;
import java.util.Arrays ;
import java.util.List ;

import android.app.Activity ;
import android.content.Intent ;
import android.graphics.Color ;
import android.os.Bundle ;
import android.util.Log ;
import android.widget.TextView ;
import android.widget.Toast ;
import br.com.fiap.exemplo.activity.R;
import br.com.fiap.minichef.common.vo.JogoVO;
import br.com.fiap.minichef.common.vo.Sorteio;
import br.com.fiap.minichef.util.Util;

public class ConferirJogoActivity extends Activity
{

	private TextView txtConcursoConferencia ;
	private TextView txtJogoConferencia ;
	Sorteio sorteio = null ;
	JogoVO jogo = null ;
	List<String> numSorteio ;
	List<String> numJogo ;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState ) ;
		setContentView( R.layout.conferir_jogo ) ;

		Log.i( "RYF", "chegou em ConferirJogoActivity" ) ;
		Intent intent = getIntent( ) ;
		Log.i( "RYF", "001" ) ;

		if (intent != null)
		{
			Bundle myBundle = intent.getExtras( ) ;
			Log.i( "RYF", "002" ) ;
			if (myBundle != null)
			{
				Log.i( "RYF", "003" ) ;
				sorteio = ( Sorteio ) myBundle.getSerializable( "sorteio" ) ;
				jogo = ( JogoVO ) myBundle.getSerializable( "jogo" ) ;

				if (sorteio != null)
				{
					Log.i( "RYF", "004" ) ;
					txtConcursoConferencia = ( TextView ) findViewById( R.id.txtConcursoConferencia ) ;
					txtConcursoConferencia.setText( "Concurso: " + sorteio.getConcurso( ) ) ;

					numSorteio = Arrays.asList( sorteio.getDezenas( ) ) ;

					TextView txtConcursoN1 = ( TextView ) findViewById( R.id.txtConcursoN1 ) ;
					TextView txtConcursoN2 = ( TextView ) findViewById( R.id.txtConcursoN2 ) ;
					TextView txtConcursoN3 = ( TextView ) findViewById( R.id.txtConcursoN3 ) ;
					TextView txtConcursoN4 = ( TextView ) findViewById( R.id.txtConcursoN4 ) ;
					TextView txtConcursoN5 = ( TextView ) findViewById( R.id.txtConcursoN5 ) ;
					TextView txtConcursoN6 = ( TextView ) findViewById( R.id.txtConcursoN6 ) ;

					NumberFormat nf = NumberFormat.getInstance( ) ;
					nf.setMinimumIntegerDigits( 2 ) ;

					txtConcursoN1.setText( nf.format( Long.valueOf( numSorteio.get( 0 ) ) ) ) ;
					txtConcursoN2.setText( nf.format( Long.valueOf( numSorteio.get( 1 ) ) ) ) ;
					txtConcursoN3.setText( nf.format( Long.valueOf( numSorteio.get( 2 ) ) ) ) ;
					txtConcursoN4.setText( nf.format( Long.valueOf( numSorteio.get( 3 ) ) ) ) ;
					txtConcursoN5.setText( nf.format( Long.valueOf( numSorteio.get( 4 ) ) ) ) ;
					txtConcursoN6.setText( nf.format( Long.valueOf( numSorteio.get( 5 ) ) ) ) ;
				}

				if (jogo != null)
				{
					Log.i( "RYF", "005" ) ;
					txtJogoConferencia = ( TextView ) findViewById( R.id.txtJogoConferencia ) ;
					txtJogoConferencia.setText( "Jogo: " + jogo.getNome( ) ) ;

					numJogo = Arrays.asList( jogo.getNumeros( ).split( "\\|" ) ) ;

					TextView txtJogoN1 = ( TextView ) findViewById( R.id.txtJogoN1 ) ;
					TextView txtJogoN2 = ( TextView ) findViewById( R.id.txtJogoN2 ) ;
					TextView txtJogoN3 = ( TextView ) findViewById( R.id.txtJogoN3 ) ;
					TextView txtJogoN4 = ( TextView ) findViewById( R.id.txtJogoN4 ) ;
					TextView txtJogoN5 = ( TextView ) findViewById( R.id.txtJogoN5 ) ;
					TextView txtJogoN6 = ( TextView ) findViewById( R.id.txtJogoN6 ) ;

					NumberFormat nf = NumberFormat.getInstance( ) ;
					nf.setMinimumIntegerDigits( 2 ) ;

					txtJogoN1.setText( nf.format( Long.valueOf( numJogo.get( 0 ) ) ) ) ;
					txtJogoN2.setText( nf.format( Long.valueOf( numJogo.get( 1 ) ) ) ) ;
					txtJogoN3.setText( nf.format( Long.valueOf( numJogo.get( 2 ) ) ) ) ;
					txtJogoN4.setText( nf.format( Long.valueOf( numJogo.get( 3 ) ) ) ) ;
					txtJogoN5.setText( nf.format( Long.valueOf( numJogo.get( 4 ) ) ) ) ;
					txtJogoN6.setText( nf.format( Long.valueOf( numJogo.get( 5 ) ) ) ) ;

					int qtdeAcertos = 0 ;

					if (Util.numeroEstaNoJogo( numJogo.get( 0 ), numSorteio ))
					{
						txtJogoN1.setTextColor( Color.RED ) ;
						qtdeAcertos++ ;
					}
					if (Util.numeroEstaNoJogo( numJogo.get( 1 ), numSorteio ))
					{
						txtJogoN2.setTextColor( Color.RED ) ;
						qtdeAcertos++ ;
					}
					if (Util.numeroEstaNoJogo( numJogo.get( 2 ), numSorteio ))
					{
						txtJogoN3.setTextColor( Color.RED ) ;
						qtdeAcertos++ ;
					}
					if (Util.numeroEstaNoJogo( numJogo.get( 3 ), numSorteio ))
					{
						txtJogoN4.setTextColor( Color.RED ) ;
						qtdeAcertos++ ;
					}
					if (Util.numeroEstaNoJogo( numJogo.get( 4 ), numSorteio ))
					{
						txtJogoN5.setTextColor( Color.RED ) ;
						qtdeAcertos++ ;
					}
					if (Util.numeroEstaNoJogo( numJogo.get( 5 ), numSorteio ))
					{
						txtJogoN6.setTextColor( Color.RED ) ;
						qtdeAcertos++ ;
					}

					String msg = "" ;

					switch (qtdeAcertos)
					{
					case 0:
					case 1:
						msg = "Você acertou " + qtdeAcertos + " número!" ;
						break ;
					case 2:
					case 3:
						msg = "Você acertou " + qtdeAcertos + " números!" ;
						break ;
					case 4:
						msg = "Você fez QUADRA! Parabéns!!!" ;
						break ;
					case 5:
						msg = "Você fez QUINA! Parabéns!!!" ;
						break ;
					case 6:
						msg = "Você fez SENA! Parabéns!!!" ;
						break ;

					default:
						break ;
					}

					Toast.makeText( getBaseContext( ), msg, Toast.LENGTH_LONG ).show( ) ;

				}
			}

		}

	}

}
