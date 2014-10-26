package br.com.fiap.financas.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.fiap.R;
import br.com.fiap.financas.activity.GastosFragment;
import br.com.fiap.financas.common.vo.RegistroVO;

public class FinancasGanhosListAdapter extends BaseAdapter {
	private GastosFragment gastosFragment;
	private LayoutInflater inflater;
	private List<RegistroVO> ganhosItems;

	public FinancasGanhosListAdapter(GastosFragment gastosFragment, List<RegistroVO> ganhosItems) {
		this.gastosFragment = gastosFragment;
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
//			inflater = (LayoutInflater) activity
//			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
		if (convertView == null)
			convertView = inflater.inflate(R.layout.list_row, null);

		TextView descricao = (TextView) convertView.findViewById(R.id.descricao);
		TextView local = (TextView) convertView.findViewById(R.id.local);
		TextView data = (TextView) convertView.findViewById(R.id.data);
		TextView valor = (TextView) convertView.findViewById(R.id.registrovalor);

		RegistroVO vo = ganhosItems.get(position);
		descricao.setText(vo.getDescricao());
		local.setText("Local: " + String.valueOf(vo.getLocal()));
		data.setText(vo.getDataFormatted());
		valor.setText(String.valueOf("R$: " + String.valueOf(vo.getValor())));

		return convertView;
	}

}