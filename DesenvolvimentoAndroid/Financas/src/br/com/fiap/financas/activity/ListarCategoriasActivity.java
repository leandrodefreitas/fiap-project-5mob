package br.com.fiap.financas.activity;

import java.util.List;

import br.com.fiap.financas.adapter.CategoriaAdapter;
import br.com.fiap.financas.common.dao.CategoriaDAO;
import br.com.fiap.financas.common.vo.CategoriaVO;
import android.app.ListActivity;
import android.os.Bundle;

public class ListarCategoriasActivity extends ListActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
    	CategoriaDAO dao = new CategoriaDAO(this);
    	
    	List<CategoriaVO> lista = dao.selectAll();
    	
    	setListAdapter(new CategoriaAdapter(this, lista));
		
	}

}
