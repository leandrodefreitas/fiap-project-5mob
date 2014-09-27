package br.com.fiap.minichef.activity ;

import java.io.IOException ;
import java.text.SimpleDateFormat ;
import java.util.Arrays ;

import android.app.Activity ;
import android.app.ProgressDialog ;
import android.content.Context ;
import android.content.Intent ;
import android.os.Bundle ;
import android.os.Handler ;
import android.util.Log ;
import android.view.View ;
import android.view.View.OnClickListener ;
import android.view.WindowManager ;
import android.view.inputmethod.InputMethodManager ;
import android.widget.Button ;
import android.widget.EditText ;
import android.widget.LinearLayout ;
import android.widget.TextView ;
import android.widget.Toast ;
import br.com.fiap.exemplo.activity.R;
import br.com.fiap.minichef.action.BuscaConcursoACT;
import br.com.fiap.minichef.common.vo.JogoVO;
import br.com.fiap.minichef.common.vo.Sorteio;
import br.com.fiap.minichef.util.InternetConnection;

public class ConsultarConcursoActivity extends Activity implements Runnable
{

	private static final String CATEGORIA = "handson" ;
	private static String URL ;

	private Handler handler = new Handler( ) ;
	private ProgressDialog dialog ;
	private String strConcurso = null ;
	private final int IPC_ID = ( int ) ( 10001 * Math.random( ) ) ;
	private Context contexto ;
	private LinearLayout layoutConsultarConcurso ;

	Sorteio sorteio = null ;
	JogoVO jogo = null ;

	TextView txtJogo ;
	EditText edtNumero ;
	Button btnConsultar, btnUltimoConcurso, btnLimpar, btnConferir ;
	TextView txtConcurso, txtData, txtDezenas ;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState ) ;
		setContentView( R.layout.consultar_concurso ) ;

		layoutConsultarConcurso = ( LinearLayout ) findViewById( R.id.layoutConsultarConcurso ) ;

		iniciarObjetos( ) ;

		InputMethodManager imm = ( InputMethodManager ) getSystemService( INPUT_METHOD_SERVICE ) ;
		imm.hideSoftInputFromWindow( edtNumero.getWindowToken( ), 0 ) ;
		getWindow( ).setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN ) ;

		if (sorteio == null)
		{
			btnConferir.setVisibility( View.INVISIBLE ) ;
		}

		Intent intent = getIntent( ) ;

		if (intent != null)
		{
			Bundle myBundle = intent.getExtras( ) ;
			if (myBundle != null)
			{
				jogo = ( JogoVO ) myBundle.getSerializable( "jogo" ) ;

				if (jogo != null)
				{
					txtJogo.setText( "Jogo: " + jogo.getNome( ) ) ;
				}
			}
		}

		if (jogo == null)
		{
			layoutConsultarConcurso.removeView( txtJogo ) ;
		}

		btnConsultar.setOnClickListener( new OnClickListener( )
		{

			@Override
			public void onClick( View v )
			{
				strConcurso = edtNumero.getText( ).toString( ) ;
				URL = "http://servicos.albertino.eti.br/Loteria.asmx/GetMegaSena_PorConcurso_JSON"
						+ "?numero=" + strConcurso ;
				contexto = getApplicationContext( ) ;

				( ( InputMethodManager ) getSystemService( Context.INPUT_METHOD_SERVICE ) )
						.hideSoftInputFromWindow( edtNumero.getWindowToken( ), 0 ) ;

				try
				{
					Integer.parseInt( strConcurso ) ;
				}
				catch (NumberFormatException e)
				{
					Toast.makeText( getBaseContext( ), "Insira o número do concurso.",
							Toast.LENGTH_LONG ).show( ) ;
					return ;
				}
				if (strConcurso == null || strConcurso.equals( "" ))
				{
					Toast.makeText( getBaseContext( ), "Insira o número do concurso.",
							Toast.LENGTH_LONG ).show( ) ;
					return ;
				}

				obterDadosDoConcurso( ) ;
			}
		} ) ;

		btnUltimoConcurso.setOnClickListener( new OnClickListener( )
		{

			@Override
			public void onClick( View v )
			{
				URL = "http://servicos.albertino.eti.br/Loteria.asmx/GetMegaSena_UltimoConcurso_JSON" ;
				strConcurso = null ;
				edtNumero.setText( "" ) ;
				contexto = getApplicationContext( ) ;

				( ( InputMethodManager ) getSystemService( Context.INPUT_METHOD_SERVICE ) )
						.hideSoftInputFromWindow( edtNumero.getWindowToken( ), 0 ) ;

				obterDadosDoConcurso( ) ;
			}
		} ) ;

		btnLimpar.setOnClickListener( new OnClickListener( )
		{

			@Override
			public void onClick( View v )
			{
				( ( InputMethodManager ) getSystemService( Context.INPUT_METHOD_SERVICE ) )
						.hideSoftInputFromWindow( edtNumero.getWindowToken( ), 0 ) ;
				contexto = getApplicationContext( ) ;

				edtNumero.setText( "" ) ;
				txtConcurso.setText( "" ) ;
				txtData.setText( "" ) ;
				txtDezenas.setText( "" ) ;

				sorteio = null ;
				btnConferir.setVisibility( View.INVISIBLE ) ;
			}
		} ) ;

		btnConferir.setOnClickListener( new OnClickListener( )
		{

			@Override
			public void onClick( View v )
			{
				contexto = getApplicationContext( ) ;
				if (jogo == null)
				{
					Intent intent = new Intent( ConsultarConcursoActivity.this,
							EscolherJogoActivity.class ) ;
					Bundle dado = new Bundle( ) ;
					dado.putInt( "myRequestCode", IPC_ID ) ;
					dado.putSerializable( "sorteio", sorteio ) ;
					intent.putExtras( dado ) ;
					startActivityForResult( intent, IPC_ID ) ;
				}
				else
				{
					Intent intent = new Intent( ConsultarConcursoActivity.this,
							ConferirJogoActivity.class ) ;
					Bundle dado = new Bundle( ) ;
					dado.putInt( "myRequestCode", IPC_ID ) ;
					dado.putSerializable( "jogo", jogo ) ;
					dado.putSerializable( "sorteio", sorteio ) ;
					intent.putExtras( dado ) ;
					startActivityForResult( intent, IPC_ID ) ;
				}

			}
		} ) ;
	}

	private void iniciarObjetos( )
	{
		txtJogo = ( TextView ) findViewById( R.id.txtJogo ) ;
		edtNumero = ( EditText ) findViewById( R.id.edtNumero ) ;
		btnConsultar = ( Button ) findViewById( R.id.btnConsultar ) ;
		btnUltimoConcurso = ( Button ) findViewById( R.id.btnUltimoConcurso ) ;
		btnLimpar = ( Button ) findViewById( R.id.btnLimpar ) ;
		btnConferir = ( Button ) findViewById( R.id.btnConferir ) ;
		txtConcurso = ( TextView ) findViewById( R.id.txtConcurso ) ;
		txtData = ( TextView ) findViewById( R.id.txtData ) ;
		txtDezenas = ( TextView ) findViewById( R.id.txtDezenas ) ;

	}

	public void obterDadosDoConcurso( )
	{
		Log.i( "RYFRYF", "consultarConcurso 1" ) ;

		if (InternetConnection.verificandoConexao( contexto ))
		{
			Log.i( "RYFRYF", "consultarConcurso 2" ) ;

			dialog = ProgressDialog.show( this, "Busca de Resultado",
					"Buscando resultado de concurso, por favor aguarde...", false, true ) ;
			new Thread( this ).start( ) ;
		}
		else
		{
			Log.i( "RYFRYF", "consultarConcurso 3" ) ;
			Toast.makeText( getBaseContext( ), "Sem acesso à internet. Favor verificar.",
					Toast.LENGTH_LONG ).show( ) ;
		}
		Log.i( "RYFRYF", "consultarConcurso 4" ) ;
	}

	@Override
	public void run( )
	{

		try
		{
			BuscaConcursoACT buscaConcurso = new BuscaConcursoACT( URL ) ;

			sorteio = buscaConcurso.buscarConcurso( strConcurso ) ;
			final SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" ) ;
			final String strMegasena ;

			if (sorteio != null)
			{
				Log.i( "RYFRYF", "sorteio nao eh nulo" ) ;

				strMegasena = "concurso: " + sorteio.getConcurso( ) + " - "
						+ sdf.format( sorteio.getData( ) ) + " - "
						+ Arrays.asList( sorteio.getDezenas( ) ) ;

				handler.post( new Runnable( )
				{

					@Override
					public void run( )
					{
						txtConcurso.setText( "Concurso: " + sorteio.getConcurso( ) ) ;
						txtData.setText( "Data: " + sdf.format( sorteio.getData( ) ) ) ;
						txtDezenas.setText( "Dezenas: " + Arrays.asList( sorteio.getDezenas( ) ) ) ;

						btnConferir.setVisibility( View.VISIBLE ) ;
						Log.i( CATEGORIA, strMegasena ) ;

					}

				} ) ;
			}
			else
			{
				handler.post( new Runnable( )
				{

					@Override
					public void run( )
					{
						Toast.makeText( getBaseContext( ), "Concurso não localizado.",
								Toast.LENGTH_LONG ).show( ) ;
						Log.i( "RYFRYF", "sorteio eh nulo" ) ;
					}

				} ) ;

			}

		}
		catch (IOException e)
		{
			Log.e( CATEGORIA, e.getMessage( ), e ) ;
		}
		finally
		{
			dialog.dismiss( ) ;
		}
	}

}
