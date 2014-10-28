package br.com.fiap.financas.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.fiap.R;
import br.com.fiap.financas.common.vo.CategoriaVO;

public class CategoriaAdapter extends BaseAdapter {
	
	private Context context;
	private List<CategoriaVO> lista;
	
	public CategoriaAdapter(Context context, List<CategoriaVO> lista){
		this.context = context;
		this.lista = lista;
	}

	@Override
	public int getCount() {
		return lista.size();
	}

	@Override
	public Object getItem(int position) {
		return lista.get(position);
	}

	@Override
	public long getItemId(int position) {
		return lista.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.lista_categoria, null);
		
		TextView tv = (TextView) layout.findViewById(R.id.categoriaItem);
		tv.setText(lista.get(position).getDescricao());
		
		return layout;
	}

}
