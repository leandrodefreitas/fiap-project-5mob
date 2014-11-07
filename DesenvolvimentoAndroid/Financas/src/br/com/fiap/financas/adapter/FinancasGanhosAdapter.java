package br.com.fiap.financas.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.fiap.R;
import br.com.fiap.financas.common.vo.GanhoVO;
import br.com.fiap.financas.util.Constantes;
import br.com.fiap.financas.util.Util;
 
public class FinancasGanhosAdapter extends BaseAdapter {
 
	// Declare Variables
	Context context;
	List<GanhoVO> lstGanho;
	LayoutInflater inflater;

	public FinancasGanhosAdapter(Context context, List<GanhoVO> lstGanho) {
		this.context = context;
		this.lstGanho = lstGanho;
	}

	public int getCount() {
		return lstGanho.size();
	}

	public Object getItem(int location) {
		return lstGanho.get(location);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		// Declare Variables
		TextView txtDescricao;
		TextView txtData;
		TextView txtValor;
		TextView txtSaldoGranho;

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.ganhos_listview_item, parent, false);

		// Locate the TextViews in ganhos_listview_item.xml
		txtDescricao = (TextView) itemView.findViewById(R.id.descricao);
		txtData = (TextView) itemView.findViewById(R.id.data);
		txtValor = (TextView) itemView.findViewById(R.id.valor);
		txtSaldoGranho = (TextView) itemView.findViewById(R.id.saldo_ganho);
		
		GanhoVO ganhoVO = lstGanho.get(position);

		txtDescricao.setText(ganhoVO.getDescricao());
		txtData.setText(Util.formataDiaDaSemana(ganhoVO.getData()) + Constantes.HIFEN_COM_ESPACO 
				+ Util.imprimeDataFormatoBR(ganhoVO.getDataFormatted()));
		txtValor.setText(Util.formataMoedaBRL(ganhoVO.getValor()));
		txtSaldoGranho.setText(Util.formataMoedaBRL(ganhoVO.getValor()));
		
		return itemView;
	}
 
}