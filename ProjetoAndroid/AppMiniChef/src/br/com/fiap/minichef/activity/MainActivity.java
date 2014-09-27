package br.com.fiap.minichef.activity ;

import br.com.fiap.exemplo.activity.R;
import android.app.Activity ;
import android.content.Intent ;
import android.os.Bundle ;
import android.view.View ;
import android.view.View.OnClickListener ;
import android.widget.Button ;

public class MainActivity extends Activity
{

	Button btnConsultarConcurso, btnCadastrarJogo, btnConferirJogo, btnGerarSurpresinha ;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState ) ;
		setContentView( R.layout.main ) ;

		btnConsultarConcurso = ( Button ) findViewById( R.id.btnConsultarConcurso ) ;
		btnCadastrarJogo = ( Button ) findViewById( R.id.btnCadastrarJogo ) ;
		btnConferirJogo = ( Button ) findViewById( R.id.btnConferirJogo ) ;

		btnConsultarConcurso.setOnClickListener( new OnClickListener( )
		{

			@Override
			public void onClick( View v )
			{
				Intent intent = new Intent( getBaseContext( ), ConsultarConcursoActivity.class ) ;
				startActivity( intent ) ;
			}
		} ) ;

		btnCadastrarJogo.setOnClickListener( new OnClickListener( )
		{

			@Override
			public void onClick( View v )
			{
				Intent intent = new Intent( getBaseContext( ), CadastrarJogoActivity.class ) ;
				startActivity( intent ) ;
			}
		} ) ;

		btnConferirJogo.setOnClickListener( new OnClickListener( )
		{

			@Override
			public void onClick( View v )
			{
				Intent intent = new Intent( getBaseContext( ), EscolherJogoActivity.class ) ;
				startActivity( intent ) ;
			}
		} ) ;
	}

}
