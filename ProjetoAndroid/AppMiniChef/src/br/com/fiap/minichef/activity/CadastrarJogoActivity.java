package br.com.fiap.minichef.activity ;

import java.text.NumberFormat ;
import java.util.ArrayList ;
import java.util.Collections ;
import java.util.Iterator ;
import java.util.List ;

import android.app.Activity ;
import android.graphics.Color ;
import android.os.Bundle ;
import android.util.Log ;
import android.view.View ;
import android.view.View.OnClickListener ;
import android.view.WindowManager ;
import android.view.inputmethod.InputMethodManager ;
import android.widget.Button ;
import android.widget.EditText ;
import android.widget.TextView ;
import android.widget.Toast ;
import br.com.fiap.minichef.activity.R;
import br.com.fiap.minichef.common.vo.JogoVO;
import br.com.fiap.minichef.services.scn.JogoSCN;

public class CadastrarJogoActivity extends Activity
{

	TextView txtN01, txtN02, txtN03, txtN04, txtN05, txtN06, txtN07, txtN08, txtN09, txtN10 ;
	TextView txtN11, txtN12, txtN13, txtN14, txtN15, txtN16, txtN17, txtN18, txtN19, txtN20 ;
	TextView txtN21, txtN22, txtN23, txtN24, txtN25, txtN26, txtN27, txtN28, txtN29, txtN30 ;
	TextView txtN31, txtN32, txtN33, txtN34, txtN35, txtN36, txtN37, txtN38, txtN39, txtN40 ;
	TextView txtN41, txtN42, txtN43, txtN44, txtN45, txtN46, txtN47, txtN48, txtN49, txtN50 ;
	TextView txtN51, txtN52, txtN53, txtN54, txtN55, txtN56, txtN57, txtN58, txtN59, txtN60 ;

	Button btnSalvar, btnLimpar ;
	EditText edtNome ;

	List<TextView> listaTextView ;
	List<Integer> listaNumeros ;

	private int NUM_MAX = 6 ;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState ) ;
		setContentView( R.layout.cadastrar_jogo ) ;

		listaTextView = new ArrayList<TextView>( ) ;
		listaNumeros = new ArrayList<Integer>( ) ;

		carregarObjetos( ) ;
		gerarGatilho( ) ;

		InputMethodManager imm = ( InputMethodManager ) getSystemService( INPUT_METHOD_SERVICE ) ;
		imm.hideSoftInputFromWindow( edtNome.getWindowToken( ), 0 ) ;
		getWindow( ).setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN ) ;

	}

	private void carregarObjetos( )
	{

		edtNome = ( EditText ) findViewById( R.id.edtNome ) ;
		btnSalvar = ( Button ) findViewById( R.id.btnSalvar ) ;
		btnLimpar = ( Button ) findViewById( R.id.btnLimparNumeros ) ;

		txtN01 = ( TextView ) findViewById( R.id.txtNumeroCad01 ) ;
		txtN02 = ( TextView ) findViewById( R.id.txtNumeroCad02 ) ;
		txtN03 = ( TextView ) findViewById( R.id.txtNumeroCad03 ) ;
		txtN04 = ( TextView ) findViewById( R.id.txtNumeroCad04 ) ;
		txtN05 = ( TextView ) findViewById( R.id.txtNumeroCad05 ) ;
		txtN06 = ( TextView ) findViewById( R.id.txtNumeroCad06 ) ;
		txtN07 = ( TextView ) findViewById( R.id.txtNumeroCad07 ) ;
		txtN08 = ( TextView ) findViewById( R.id.txtNumeroCad08 ) ;
		txtN09 = ( TextView ) findViewById( R.id.txtNumeroCad09 ) ;
		txtN10 = ( TextView ) findViewById( R.id.txtNumeroCad10 ) ;

		txtN11 = ( TextView ) findViewById( R.id.txtNumeroCad11 ) ;
		txtN12 = ( TextView ) findViewById( R.id.txtNumeroCad12 ) ;
		txtN13 = ( TextView ) findViewById( R.id.txtNumeroCad13 ) ;
		txtN14 = ( TextView ) findViewById( R.id.txtNumeroCad14 ) ;
		txtN15 = ( TextView ) findViewById( R.id.txtNumeroCad15 ) ;
		txtN16 = ( TextView ) findViewById( R.id.txtNumeroCad16 ) ;
		txtN17 = ( TextView ) findViewById( R.id.txtNumeroCad17 ) ;
		txtN18 = ( TextView ) findViewById( R.id.txtNumeroCad18 ) ;
		txtN19 = ( TextView ) findViewById( R.id.txtNumeroCad19 ) ;
		txtN20 = ( TextView ) findViewById( R.id.txtNumeroCad20 ) ;

		txtN21 = ( TextView ) findViewById( R.id.txtNumeroCad21 ) ;
		txtN22 = ( TextView ) findViewById( R.id.txtNumeroCad22 ) ;
		txtN23 = ( TextView ) findViewById( R.id.txtNumeroCad23 ) ;
		txtN24 = ( TextView ) findViewById( R.id.txtNumeroCad24 ) ;
		txtN25 = ( TextView ) findViewById( R.id.txtNumeroCad25 ) ;
		txtN26 = ( TextView ) findViewById( R.id.txtNumeroCad26 ) ;
		txtN27 = ( TextView ) findViewById( R.id.txtNumeroCad27 ) ;
		txtN28 = ( TextView ) findViewById( R.id.txtNumeroCad28 ) ;
		txtN29 = ( TextView ) findViewById( R.id.txtNumeroCad29 ) ;
		txtN30 = ( TextView ) findViewById( R.id.txtNumeroCad30 ) ;

		txtN31 = ( TextView ) findViewById( R.id.txtNumeroCad31 ) ;
		txtN32 = ( TextView ) findViewById( R.id.txtNumeroCad32 ) ;
		txtN33 = ( TextView ) findViewById( R.id.txtNumeroCad33 ) ;
		txtN34 = ( TextView ) findViewById( R.id.txtNumeroCad34 ) ;
		txtN35 = ( TextView ) findViewById( R.id.txtNumeroCad35 ) ;
		txtN36 = ( TextView ) findViewById( R.id.txtNumeroCad36 ) ;
		txtN37 = ( TextView ) findViewById( R.id.txtNumeroCad37 ) ;
		txtN38 = ( TextView ) findViewById( R.id.txtNumeroCad38 ) ;
		txtN39 = ( TextView ) findViewById( R.id.txtNumeroCad39 ) ;
		txtN40 = ( TextView ) findViewById( R.id.txtNumeroCad40 ) ;

		txtN41 = ( TextView ) findViewById( R.id.txtNumeroCad41 ) ;
		txtN42 = ( TextView ) findViewById( R.id.txtNumeroCad42 ) ;
		txtN43 = ( TextView ) findViewById( R.id.txtNumeroCad43 ) ;
		txtN44 = ( TextView ) findViewById( R.id.txtNumeroCad44 ) ;
		txtN45 = ( TextView ) findViewById( R.id.txtNumeroCad45 ) ;
		txtN46 = ( TextView ) findViewById( R.id.txtNumeroCad46 ) ;
		txtN47 = ( TextView ) findViewById( R.id.txtNumeroCad47 ) ;
		txtN48 = ( TextView ) findViewById( R.id.txtNumeroCad48 ) ;
		txtN49 = ( TextView ) findViewById( R.id.txtNumeroCad49 ) ;
		txtN50 = ( TextView ) findViewById( R.id.txtNumeroCad50 ) ;

		txtN51 = ( TextView ) findViewById( R.id.txtNumeroCad51 ) ;
		txtN52 = ( TextView ) findViewById( R.id.txtNumeroCad52 ) ;
		txtN53 = ( TextView ) findViewById( R.id.txtNumeroCad53 ) ;
		txtN54 = ( TextView ) findViewById( R.id.txtNumeroCad54 ) ;
		txtN55 = ( TextView ) findViewById( R.id.txtNumeroCad55 ) ;
		txtN56 = ( TextView ) findViewById( R.id.txtNumeroCad56 ) ;
		txtN57 = ( TextView ) findViewById( R.id.txtNumeroCad57 ) ;
		txtN58 = ( TextView ) findViewById( R.id.txtNumeroCad58 ) ;
		txtN59 = ( TextView ) findViewById( R.id.txtNumeroCad59 ) ;
		txtN60 = ( TextView ) findViewById( R.id.txtNumeroCad60 ) ;

		listaTextView.add( txtN01 ) ;
		listaTextView.add( txtN02 ) ;
		listaTextView.add( txtN03 ) ;
		listaTextView.add( txtN04 ) ;
		listaTextView.add( txtN05 ) ;
		listaTextView.add( txtN06 ) ;
		listaTextView.add( txtN07 ) ;
		listaTextView.add( txtN08 ) ;
		listaTextView.add( txtN09 ) ;
		listaTextView.add( txtN10 ) ;

		listaTextView.add( txtN11 ) ;
		listaTextView.add( txtN12 ) ;
		listaTextView.add( txtN13 ) ;
		listaTextView.add( txtN14 ) ;
		listaTextView.add( txtN15 ) ;
		listaTextView.add( txtN16 ) ;
		listaTextView.add( txtN17 ) ;
		listaTextView.add( txtN18 ) ;
		listaTextView.add( txtN19 ) ;
		listaTextView.add( txtN20 ) ;

		listaTextView.add( txtN21 ) ;
		listaTextView.add( txtN22 ) ;
		listaTextView.add( txtN23 ) ;
		listaTextView.add( txtN24 ) ;
		listaTextView.add( txtN25 ) ;
		listaTextView.add( txtN26 ) ;
		listaTextView.add( txtN27 ) ;
		listaTextView.add( txtN28 ) ;
		listaTextView.add( txtN29 ) ;
		listaTextView.add( txtN30 ) ;

		listaTextView.add( txtN31 ) ;
		listaTextView.add( txtN32 ) ;
		listaTextView.add( txtN33 ) ;
		listaTextView.add( txtN34 ) ;
		listaTextView.add( txtN35 ) ;
		listaTextView.add( txtN36 ) ;
		listaTextView.add( txtN37 ) ;
		listaTextView.add( txtN38 ) ;
		listaTextView.add( txtN39 ) ;
		listaTextView.add( txtN40 ) ;

		listaTextView.add( txtN41 ) ;
		listaTextView.add( txtN42 ) ;
		listaTextView.add( txtN43 ) ;
		listaTextView.add( txtN44 ) ;
		listaTextView.add( txtN45 ) ;
		listaTextView.add( txtN46 ) ;
		listaTextView.add( txtN47 ) ;
		listaTextView.add( txtN48 ) ;
		listaTextView.add( txtN49 ) ;
		listaTextView.add( txtN50 ) ;

		listaTextView.add( txtN51 ) ;
		listaTextView.add( txtN52 ) ;
		listaTextView.add( txtN53 ) ;
		listaTextView.add( txtN54 ) ;
		listaTextView.add( txtN55 ) ;
		listaTextView.add( txtN56 ) ;
		listaTextView.add( txtN57 ) ;
		listaTextView.add( txtN58 ) ;
		listaTextView.add( txtN59 ) ;
		listaTextView.add( txtN60 ) ;
	}

	private void gerarGatilho( )
	{

		btnSalvar.setOnClickListener( new OnClickListener( )
		{

			@Override
			public void onClick( View arg0 )
			{
				gravarJogo( ) ;
			}
		} ) ;

		btnLimpar.setOnClickListener( new OnClickListener( )
		{

			@Override
			public void onClick( View arg0 )
			{
				limparCampos( ) ;
			}
		} ) ;

		for (final TextView textView : listaTextView)
		{

			textView.setOnClickListener( new OnClickListener( )
			{

				@Override
				public void onClick( View arg0 )
				{
					inverterCorTextView( textView ) ;
				}
			} ) ;

		}

	}

	private void inverterCorTextView( TextView textView )
	{
		if (textView.getCurrentTextColor( ) == -16777216)
		{
			if (listaNumeros.size( ) < NUM_MAX)
			{
				listaNumeros.add( Integer.valueOf( textView.getText( ).toString( ) ) ) ;
				textView.setTextColor( Color.RED ) ;

				for (Integer integer : listaNumeros)
				{
					Log.i( "RYFRYF", "selecionado: " + integer.intValue( ) ) ;
				}
			}
		}
		else
		{

			for (Iterator<Integer> it = listaNumeros.iterator( ); it.hasNext( );)
			{
				Integer n = it.next( ) ;
				int aux1 = n.intValue( ) ;
				int aux2 = Integer.parseInt( textView.getText( ).toString( ) ) ;
				if (aux1 == aux2)
				{
					it.remove( ) ;
					break ;
				}
			}
			textView.setTextColor( Color.BLACK ) ;

		}
	}

	private void limparCampos( )
	{
		edtNome.setText( "" ) ;
		listaNumeros = new ArrayList<Integer>( ) ;
		for (TextView textView : listaTextView)
		{
			textView.setTextColor( Color.BLACK ) ;
		}

	}

	private void gravarJogo( )
	{
		if (listaNumeros.size( ) != 6)
		{
			Toast.makeText( getBaseContext( ), "Selecione 6 números para o jogo.",
					Toast.LENGTH_LONG ).show( ) ;
			return ;
		}

		if (edtNome.getText( ).toString( ).isEmpty( ))
		{
			Toast.makeText( getBaseContext( ), "Preencha o nome do jogo.", Toast.LENGTH_LONG )
					.show( ) ;
			return ;
		}

		Collections.sort( listaNumeros ) ;

		NumberFormat nf = NumberFormat.getInstance( ) ;
		nf.setMinimumIntegerDigits( 2 ) ;

		StringBuffer strNumeros = new StringBuffer( "" ) ;

		for (int i = 0; i < listaNumeros.size( ); i++)
		{
			strNumeros.append( nf.format( Long.valueOf( listaNumeros.get( i ) ) ) ) ;
			if (i != listaNumeros.size( ) - 1)
			{
				strNumeros.append( "|" ) ;
			}
		}

		JogoVO jogo = new JogoVO( ) ;
		jogo.setId( 1 ) ;
		jogo.setNome( edtNome.getText( ).toString( ).trim( ) ) ;
		jogo.setNumeros( strNumeros.toString( ) ) ;

		JogoSCN controle = new JogoSCN( getApplicationContext( ) ) ;

		if (controle.obterJogoPorNome( jogo.getNome( ) ) != null)
		{
			Toast.makeText( getBaseContext( ), "Nome do jogo já existe. Favor alterar.",
					Toast.LENGTH_LONG ).show( ) ;
			return ;
		}

		if (controle.obterJogoPorNumeros( jogo.getNumeros( ) ) != null)
		{
			Toast.makeText( getBaseContext( ), "Jogo com esses números já existe. Favor alterar.",
					Toast.LENGTH_LONG ).show( ) ;
			return ;
		}

		controle.salvarJogo( jogo ) ;

		limparCampos( ) ;

		Toast.makeText( getBaseContext( ),
				"Jogo '" + jogo.getNome( ) + "' - " + jogo.getNumeros( ) + " - salvo!",
				Toast.LENGTH_LONG ).show( ) ;
	}

}
