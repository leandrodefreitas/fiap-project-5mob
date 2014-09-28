package br.com.fiap.minichef.adapter ;

import java.util.Arrays ;
import java.util.List ;

import android.content.Context ;
import android.view.LayoutInflater ;
import android.view.View ;
import android.view.ViewGroup ;
import android.widget.ArrayAdapter ;
import android.widget.TextView ;
import br.com.fiap.minichef.activity.R;
import br.com.fiap.minichef.common.vo.JogoVO;

public class JogoAdapter extends ArrayAdapter<JogoVO>
{

	private final Context context ;
	private final List<JogoVO> jogosArrayList ;

	public JogoAdapter( Context context, List<JogoVO> jogos )
	{

		super( context, R.layout.item_listview_jogos, jogos ) ;

		this.context = context ;
		this.jogosArrayList = jogos ;
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent )
	{

		LayoutInflater inflater = ( LayoutInflater ) context
				.getSystemService( Context.LAYOUT_INFLATER_SERVICE ) ;

		View rowView = null ;
		rowView = inflater.inflate( R.layout.item_listview_jogos, parent, false ) ;

		TextView txtNomeJogo = ( TextView ) rowView.findViewById( R.id.txtNomeJogo ) ;
		TextView txtDezena1 = ( TextView ) rowView.findViewById( R.id.txtDezena1 ) ;
		TextView txtDezena2 = ( TextView ) rowView.findViewById( R.id.txtDezena2 ) ;
		TextView txtDezena3 = ( TextView ) rowView.findViewById( R.id.txtDezena3 ) ;
		TextView txtDezena4 = ( TextView ) rowView.findViewById( R.id.txtDezena4 ) ;
		TextView txtDezena5 = ( TextView ) rowView.findViewById( R.id.txtDezena5 ) ;
		TextView txtDezena6 = ( TextView ) rowView.findViewById( R.id.txtDezena6 ) ;

		txtNomeJogo.setText( jogosArrayList.get( position ).getNome( ) ) ;

		List<String> numJogo = Arrays.asList( jogosArrayList.get( position ).getNumeros( )
				.split( "\\|" ) ) ;

		txtDezena1.setText( numJogo.get( 0 ) ) ;
		txtDezena2.setText( numJogo.get( 1 ) ) ;
		txtDezena3.setText( numJogo.get( 2 ) ) ;
		txtDezena4.setText( numJogo.get( 3 ) ) ;
		txtDezena5.setText( numJogo.get( 4 ) ) ;
		txtDezena6.setText( numJogo.get( 5 ) ) ;
		return rowView ;
	}

}
