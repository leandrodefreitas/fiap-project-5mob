package br.com.fiap.minichef.activity ;

import br.com.fiap.exemplo.activity.R;
import android.app.Activity ;
import android.content.Intent ;
import android.os.Bundle ;
import android.os.Handler ;

public class Splash extends Activity implements Runnable
{

	private final int splashTime = 3000 ;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState ) ;
		setContentView( R.layout.splash ) ;

		Handler h = new Handler( ) ;
		h.postDelayed( this, splashTime ) ;
	}

	public void run( )
	{
		startActivity( new Intent( ".MainActivity" ) ) ;

		finish( ) ;
	}
}