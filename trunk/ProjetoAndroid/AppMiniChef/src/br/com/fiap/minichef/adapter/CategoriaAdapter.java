package br.com.fiap.minichef.adapter;

import java.util.List;

import br.com.fiap.minichef.activity.R;
import br.com.fiap.minichef.common.vo.CategoriaVO;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CategoriaAdapter extends BaseAdapter {
	
	private Context context;
	private List<CategoriaVO> lista;
	private CheckedTextView tv;
	
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
		final int auxposition = position;
		
		View v = convertView;		
		
		if (v == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    v = inflater.inflate(R.layout.categoria_item, null);
		}
		
		tv = (CheckedTextView) v.findViewById(R.id.categoriaItem);
		tv.setText(lista.get(position).getDescricao());
		
		
		return v;
	}

}
