package br.com.fiap.minichef.services.scn;

import java.util.List;

import android.content.Context;
import br.com.fiap.minichef.common.dao.IngredienteDAO;
import br.com.fiap.minichef.common.vo.IngredienteVO;

public class IngredienteSCN {

	private Context context;

	public IngredienteSCN(Context context) {
		this.context = context;
	}
	
	public long salvarIngrediente(IngredienteVO ingrediente) {
		
		IngredienteDAO ingDao = new IngredienteDAO(context);
		Long id = ingDao.insert(ingrediente);
		ingDao.close();
		
		return id;
	}	

	public List<IngredienteVO> obterTodosIngredientes() {
		IngredienteDAO ingredienteDao = new IngredienteDAO(context);
		List<IngredienteVO> ingredientes = ingredienteDao.selectAll();
		ingredienteDao.close();
		return ingredientes;
	}
	
	public IngredienteVO obterIngredientePorId(Integer id) {
		IngredienteDAO ingDao = new IngredienteDAO(context);
		IngredienteVO ingrediente = ingDao.selectById(id);
		ingDao.close();
		return ingrediente;
	}
	
	public IngredienteVO obterIngredientePorNome(String nome) {
		IngredienteDAO ingDao = new IngredienteDAO(context);
		IngredienteVO ingrediente = ingDao.selectByNome(nome);
		ingDao.close();
		return ingrediente;
	}
	
	public Boolean verSetemIngrediente(String nome) {
		IngredienteDAO ingDao = new IngredienteDAO(context);
		IngredienteVO ingrediente = ingDao.selectByNome(nome);
		ingDao.close();
		if (ingrediente != null) {
			return true;
		} else {
			return false;
		}
	}
	
	
}
