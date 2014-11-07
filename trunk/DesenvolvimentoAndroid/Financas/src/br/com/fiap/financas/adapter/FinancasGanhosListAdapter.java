package br.com.fiap.financas.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.fiap.R;
import br.com.fiap.financas.common.vo.GastoVO;

public class FinancasGanhosListAdapter extends BaseAdapter {
	Context context;
	private List<GastoVO> ganhosItems;

	public FinancasGanhosListAdapter(Context context, List<GastoVO> ganhosItems) {
		this.context  = context;
		this.ganhosItems = ganhosItems;
	}

	@Override
	public int getCount() {
		return ganhosItems.size();
	}

	@Override
	public Object getItem(int location) {
		return ganhosItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return ganhosItems.indexOf(getItem(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_row, null);
		}

		TextView descricao = (TextView) convertView.findViewById(R.id.descricao);
		TextView local = (TextView) convertView.findViewById(R.id.local);
		TextView data = (TextView) convertView.findViewById(R.id.data);
		TextView valor = (TextView) convertView.findViewById(R.id.registrovalor);

		GastoVO vo = ganhosItems.get(position);
		descricao.setText(vo.getDescricao());
		local.setText("Local: " + String.valueOf(vo.getLocal()));
		data.setText(vo.getDataFormatted());
		valor.setText(String.valueOf("R$: " + String.valueOf(vo.getValor())));

		return convertView;
	}

}