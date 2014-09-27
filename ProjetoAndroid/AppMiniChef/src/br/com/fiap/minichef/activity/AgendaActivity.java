package br.com.fiap.minichef.activity;

import java.util.List ;

import android.app.Activity ;
import android.content.Context ;
import android.os.Bundle ;
import android.util.Log ;
import android.view.View ;
import android.view.View.OnClickListener ;
import android.widget.Button ;
import android.widget.EditText ;
import android.widget.TextView ;
import br.com.fiap.exemplo.activity.R;
import br.com.fiap.minichef.common.vo.ContatoVO;
import br.com.fiap.minichef.services.scn.CadastroSCN;

public class AgendaActivity extends Activity
{
	public static Context context ;
	public static String versao ;
	public static String matricula;
	
	Button btnSalvar, btnCancelar, btnNovo ;
	EditText txtNome, txtEndereco, txtTelefone ;

	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState ) ;
		carregarInterfaceListagem( ) ;
	}

	public void carregarInterfaceListagem( )
	{
		setContentView( R.layout.main ) ;
		btnNovo = ( Button ) findViewById( R.id.btnNovo ) ;
		btnNovo.setOnClickListener( new OnClickListener( )
		{
			public void onClick( View v )
			{
				CarregarInterfaceCadastro( ) ;
			}
		} ) ;
		
		try
		{
			CadastroSCN scn = new CadastroSCN( getApplicationContext( ) ) ;
			scn.selectAllOrderName( ) ;
			List<ContatoVO> contatos = scn.selectAllOrderName( ) ;
			for (int i = 0; i < contatos.size( ); i++)
			{
				ContatoVO contatoVO = contatos.get( i ) ;
				imprimirLinha( contatoVO ) ;
			}
		}
		catch( Exception e )
		{
			Log.e( "FIAP", e.getMessage( ) ) ;
		}
	}

	public void CarregarInterfaceCadastro( )
	{
		setContentView( R.layout.cadastro ) ;
		btnCancelar = ( Button ) findViewById( R.id.btnCancelar ) ;
		btnCancelar.setOnClickListener( new OnClickListener( )
		{
			public void onClick( View v )
			{
				carregarInterfaceListagem( ) ;
			}
		} ) ;

		txtNome     = ( EditText ) findViewById( R.id.txtNome ) ;
		txtEndereco = ( EditText ) findViewById( R.id.txtEndereco ) ;
		txtTelefone = ( EditText ) findViewById( R.id.txtTelefone ) ;
		btnSalvar   = ( Button ) findViewById( R.id.btnSalvar ) ;
		btnSalvar.setOnClickListener( new OnClickListener( )
		{
			public void onClick( View v )
			{
				salvarCadastro( ) ;
			}
		} ) ;
	}

	public void salvarCadastro( )
	{
		ContatoVO vo = new ContatoVO( ) ; 
		vo.setNome(     txtNome.getText( ).toString( ) ) ;
		vo.setTelefone( txtTelefone.getText( ).toString( ) ) ;
		vo.setEndereco( txtEndereco.getText( ).toString( ) ) ;
		
		try
		{
			CadastroSCN scn = new CadastroSCN( getApplicationContext( ) ) ;
			scn.salvarContato( vo ) ;
			
			setContentView( R.layout.main ) ;
			
			List<ContatoVO> contatos = scn.selectAllOrderName( ) ;
			for (int i = 0; i < contatos.size( ); i++)
			{
				ContatoVO contatoVO = contatos.get( i ) ;
				imprimirLinha( contatoVO ) ;
			}
		}
		catch( Exception e )
		{
			Log.e( "FIAP", e.getMessage( ) ) ;
		}
		
	}

	public void imprimirLinha( ContatoVO vo )
	{
		String nome     = vo.getNome( ) ;
		String telefone = vo.getTelefone( ) ;
		String endereco = vo.getEndereco( ) ;
		TextView tv = ( TextView ) findViewById( R.id.listaContatos ) ;
		if (tv.getText( ).toString( ).equalsIgnoreCase( "Nenhum contato cadastrado." )) 
			tv.setText( "" ) ;
		tv.setText( tv.getText( ) + "\r\n" + "Nome: " + nome + "\n " + "Telefone: " + telefone	+ "\n" + "Endereço: " + endereco ) ;
	}
}