package br.com.fiap.financas.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.fiap.R;
import br.com.fiap.financas.common.vo.GastoVO;
import br.com.fiap.financas.util.Constantes;
import br.com.fiap.financas.util.Util;
 
public class FinancasGastosAdapter extends BaseAdapter {
 
	// Declare Variables
	Context context;
	List<GastoVO> lstGasto;
	LayoutInflater inflater;

	public FinancasGastosAdapter(Context context, List<GastoVO> lstGasto) {
		this.context = context;
		this.lstGasto = lstGasto;
	}

	public int getCount() {
		return lstGasto.size();
	}

	public Object getItem(int location) {
		return lstGasto.get(location);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		// Declare Variables
		TextView txtDescricao;
		TextView txtData;
		TextView txtCategoria;
		TextView txtValor;
		TextView txtGanhoDesconto;

		View itemView = convertView;		
		
		if (itemView == null){
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    itemView = inflater.inflate(R.layout.gastos_listview_item, parent, false);
		}

		// Locate the TextViews in gastos_listview_item.xml
		txtDescricao = (TextView) itemView.findViewById(R.id.descricao);
		txtData = (TextView) itemView.findViewById(R.id.data);
		txtCategoria = (TextView) itemView.findViewById(R.id.categoria);
		txtValor = (TextView) itemView.findViewById(R.id.valor);
		txtGanhoDesconto = (TextView) itemView.findViewById(R.id.ganho_desconto);
		
		GastoVO gastoVO = lstGasto.get(position);
		
		if (gastoVO.getParcela() == 0 && gastoVO.getNumParcelas() == 0) {
			txtDescricao.setText(gastoVO.getDescricao());
		} else {
			txtDescricao.setText(gastoVO.getDescricao() + Constantes.ESPACO + 
					gastoVO.getParcela() + Constantes.BARRA + gastoVO.getNumParcelas());			
		}
		txtData.setText(Util.formataDiaDaSemana(gastoVO.getData()) + Constantes.HIFEN_COM_ESPACO + 
				Util.imprimeDataFormatoBR(gastoVO.getDataFormatted()));
		txtCategoria.setText(gastoVO.getCategoriasString());
		txtValor.setText(Util.formataMoedaBRL(gastoVO.getValor()));
		txtGanhoDesconto.setText(gastoVO.getGanhoDescontar().getDescricao());
		
		return itemView;
	}
 
}