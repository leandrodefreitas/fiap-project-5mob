package br.com.fiap.financas.services.scn;

import java.util.List;

import android.content.Context;
import br.com.fiap.financas.common.dao.CategoriaDAO;
import br.com.fiap.financas.common.vo.CategoriaVO;

public class CategoriaSCN {

	private Context context;

	public CategoriaSCN(Context context) {
		this.context = context;
	}
	
	public long salvarCategoria(CategoriaVO categoria) {
		
		CategoriaDAO catDao = new CategoriaDAO(context);
		Long id = catDao.insert(categoria);
		catDao.close();
		
		return id;
	}	

	public List<CategoriaVO> obterTodasCategorias() {
		CategoriaDAO categoriaDao = new CategoriaDAO(context);
		List<CategoriaVO> categorias = categoriaDao.selectAll();
		categoriaDao.close();
		return categorias;
	}
	
	public CategoriaVO obterCategoriaPorId(Integer id) {
		
		CategoriaDAO catDao = new CategoriaDAO(context);
		CategoriaVO categoria = catDao.selectById(id);
		catDao.close();
		return categoria;
	}	
	
	
}
