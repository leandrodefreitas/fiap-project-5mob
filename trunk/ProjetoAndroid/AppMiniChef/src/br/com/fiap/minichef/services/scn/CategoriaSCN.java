package br.com.fiap.minichef.services.scn;

import java.util.List;

import android.content.Context;
import br.com.fiap.minichef.common.dao.CategoriaDAO;
import br.com.fiap.minichef.common.vo.CategoriaVO;

public class CategoriaSCN {

	private Context context;

	public CategoriaSCN(Context context) {
		this.context = context;
	}
	
	public long salvarCategoria(CategoriaVO categoria) {
		
		CategoriaDAO ingDao = new CategoriaDAO(context);
		Long id = ingDao.insert(categoria);
		ingDao.close();
		
		return id;
	}	

	public List<CategoriaVO> obterTodasCategorias() {
		CategoriaDAO categoriaDao = new CategoriaDAO(context);
		List<CategoriaVO> categorias = categoriaDao.selectAll();
		categoriaDao.close();
		return categorias;
	}
	
	public CategoriaVO obterCategoriaPorId(Integer id) {
		
		CategoriaDAO ingDao = new CategoriaDAO(context);
		CategoriaVO categoria = ingDao.selectById(id);
		ingDao.close();
		return categoria;
	}	
	
	
}
