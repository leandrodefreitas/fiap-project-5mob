package br.com.fiap.financas.activity;

import br.com.fiap.R;
import br.com.fiap.financas.util.InteractiveLineGraphView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;

public class GraficosActivity extends Activity {

    private InteractiveLineGraphView mGraphView ;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.informacoes);
		//mGraphView = (InteractiveLineGraphView) findViewById(R.id.chart);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*public boolean onOptionsItemSelected( MenuItem item )
	{
		switch (item.getItemId( ))
		{
		case R.id.action_zoom_in:
			mGraphView.zoomIn( ) ;
			return true ;

		case R.id.action_zoom_out:
			mGraphView.zoomOut( ) ;
			return true ;

		case R.id.action_pan_left:
			mGraphView.panLeft( ) ;
			return true ;

		case R.id.action_pan_right:
			mGraphView.panRight( ) ;
			return true ;

		case R.id.action_pan_up:
			mGraphView.panUp( ) ;
			return true ;

		case R.id.action_pan_down:
			mGraphView.panDown( ) ;
			return true ;
		}

		return super.onOptionsItemSelected( item ) ;
	}*/

}
