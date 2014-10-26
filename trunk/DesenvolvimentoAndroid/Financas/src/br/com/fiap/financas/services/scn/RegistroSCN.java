package br.com.fiap.financas.services.scn;

import java.util.List;

import android.content.Context;
import br.com.fiap.financas.common.dao.RegistroDAO;
import br.com.fiap.financas.common.vo.RegistroVO;

public class RegistroSCN {

	private Context context;

	public RegistroSCN(Context context) {
		this.context = context;
	}

	public void salvarRegistro(RegistroVO registro) {
		RegistroDAO dao = new RegistroDAO(context);
		dao.insert(registro);
		dao.close();
	}

	public List<RegistroVO> obterRegistrosPorTipo(int tipo) {
		RegistroDAO dao = new RegistroDAO(context);
		List<RegistroVO> registro = dao.selectByType(tipo);
		dao.close();
		return registro;
	}

}
