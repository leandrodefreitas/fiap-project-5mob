package br.com.fiap.financas.services.scn;

import java.util.List;

import android.content.Context;
import br.com.fiap.financas.common.dao.GastoDAO;
import br.com.fiap.financas.common.vo.GastoVO;

public class RegistroSCN {

	private Context context;

	public RegistroSCN(Context context) {
		this.context = context;
	}

	public void salvarRegistro(GastoVO registro) {
		GastoDAO dao = new GastoDAO(context);
		dao.insert(registro);
		dao.close();
	}

	public List<GastoVO> obterRegistrosPorTipo(int tipo) {
		GastoDAO dao = new GastoDAO(context);
		List<GastoVO> registro = dao.selectByType(tipo);
		dao.close();
		return registro;
	}

}
