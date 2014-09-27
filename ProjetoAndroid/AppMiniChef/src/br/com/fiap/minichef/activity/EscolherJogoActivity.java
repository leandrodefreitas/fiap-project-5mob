package br.com.fiap.minichef.activity ;

import java.util.List ;

import android.app.Activity ;
import android.app.AlertDialog ;
import android.content.Context ;
import android.content.DialogInterface ;
import android.content.DialogInterface.OnClickListener ;
import android.content.Intent ;
import android.os.Bundle ;
import android.util.Log ;
import android.view.View ;
import android.widget.AdapterView ;
import android.widget.AdapterView.OnItemClickListener ;
import android.widget.AdapterView.OnItemLongClickListener ;
import android.widget.LinearLayout ;
import android.widget.ListView ;
import android.widget.TextView ;
import br.com.fiap.exemplo.activity.R;
import br.com.fiap.minichef.adapter.JogoAdapter;
import br.com.fiap.minichef.common.vo.JogoVO;
import br.com.fiap.minichef.common.vo.Sorteio;
import br.com.fiap.minichef.services.scn.JogoSCN;

public class EscolherJogoActivity extends Activity
{

	private TextView txtSorteio ;
	private ListView listviewJogos ;
	private List<JogoVO> jogos ;
	private Sorteio sorteio = null ;
	private final int IPC_ID = ( int ) ( 10001 * Math.random( ) ) ;
	JogoAdapter jogoAdapter ;
	Context context ;
	private LinearLayout layoutEscolherJogo ;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		Log.i( "RYFRYF", "entrou no create" ) ;
		super.onCreate( savedInstanceState ) ;
		setContentView( R.layout.escolher_jogo ) ;

		layoutEscolherJogo = ( LinearLayout ) findViewById( R.id.layoutEscolherJogo ) ;

		txtSorteio = ( TextView ) findViewById( R.id.txtSorteio ) ;

		context = this ;

		Intent intent = getIntent( ) ;

		if (intent != null)
		{
			Bundle myBundle = intent.getExtras( ) ;
			if (myBundle != null)
			{
				sorteio = ( Sorteio ) myBundle.getSerializable( "sorteio" ) ;

				if (sorteio != null)
				{
					txtSorteio.setText( "Concurso: " + sorteio.getConcurso( ) ) ;
				}
			}
		}

		if (sorteio == null)
		{
			layoutEscolherJogo.removeView( txtSorteio ) ;
		}

		listviewJogos = ( ListView ) findViewById( R.id.listViewJogos ) ;

		JogoSCN controle = new JogoSCN( getApplicationContext( ) ) ;
		jogos = controle.obterTodosJogos( ) ;

		if (jogos.isEmpty( ))
		{

			Log.i( "RYF", "LISTA DE JOGOS VAZIO" ) ;

			AlertDialog.Builder alert = new AlertDialog.Builder( EscolherJogoActivity.this ) ;

			alert.setTitle( "Mensagem" ) ;
			alert.setMessage( "Nenhum jogo cadastrado. Cadastrar jogo?" ) ;
			alert.setPositiveButton( "Sim", new OnClickListener( )
			{

				@Override
				public void onClick( DialogInterface dialog, int which )
				{
					Intent intent = new Intent( getBaseContext( ), CadastrarJogoActivity.class ) ;
					startActivity( intent ) ;
				}
			} ) ;
			alert.setNegativeButton( "Cancelar", new OnClickListener( )
			{
				@Override
				public void onClick( DialogInterface dialog, int which )
				{
					dialog.dismiss( ) ;
				}
			} ) ;
			alert.show( ) ;
		}
		else
		{
			Log.i( "RYF", "LISTA DE JOGOS NÃO VAZIO" ) ;
		}

		for (JogoVO jogo : jogos)
		{
			Log.i( "RYF", jogo.getNome( ) + " - " + jogo.getNumeros( ) ) ;
		}

		jogoAdapter = new JogoAdapter( this, jogos ) ;
		listviewJogos.setAdapter( jogoAdapter ) ;

		listviewJogos.setOnItemClickListener( new OnItemClickListener( )
		{

			@Override
			public void onItemClick( AdapterView<?> parent, View view, int position, long id )
			{

				JogoVO jogo = jogos.get( position ) ;

				if (sorteio == null)
				{
					Log.i( "RYF", "ir para ConsultarConcursoActivity" ) ;

					Intent intent = new Intent( EscolherJogoActivity.this,
							ConsultarConcursoActivity.class ) ;
					Bundle dado = new Bundle( ) ;
					dado.putInt( "myRequestCode", IPC_ID ) ;
					dado.putSerializable( "jogo", jogo ) ;
					intent.putExtras( dado ) ;
					startActivityForResult( intent, IPC_ID ) ;
				}
				else
				{
					Log.i( "RYF", "ir para ConferirJogoActivity" ) ;

					Intent intent = new Intent( EscolherJogoActivity.this,
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

		listviewJogos.setOnItemLongClickListener( new OnItemLongClickListener( )
		{

			@Override
			public boolean onItemLongClick( AdapterView<?> parent, View view, int position, long id )
			{
				removeItemFromList( position ) ;
				return true ;
			}
		} ) ;
	}

	protected void removeItemFromList( int position )
	{
		final int deletePosition = position ;

		AlertDialog.Builder alert = new AlertDialog.Builder( EscolherJogoActivity.this ) ;

		alert.setTitle( "Exclusão" ) ;
		alert.setMessage( "Gostaria de excluir este jogo?" ) ;
		alert.setPositiveButton( "Sim", new OnClickListener( )
		{

			@Override
			public void onClick( DialogInterface dialog, int which )
			{
				JogoVO jogo = jogos.get( deletePosition ) ;

				JogoSCN controle = new JogoSCN( getApplicationContext( ) ) ;
				controle.excluirJogoPorId( jogo.getId( ) ) ;

				jogos.remove( deletePosition ) ;
				jogoAdapter.notifyDataSetChanged( ) ;
				jogoAdapter.notifyDataSetInvalidated( ) ;

			}
		} ) ;
		alert.setNegativeButton( "Cancelar", new OnClickListener( )
		{
			@Override
			public void onClick( DialogInterface dialog, int which )
			{
				dialog.dismiss( ) ;
			}
		} ) ;

		alert.show( ) ;

	}

}
