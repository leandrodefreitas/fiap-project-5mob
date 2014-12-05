package br.com.fiap.minichef.services.scn;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import br.com.fiap.minichef.common.dao.ReceitaCategoriaDAO;
import br.com.fiap.minichef.common.dao.ReceitaDAO;
import br.com.fiap.minichef.common.dao.ItemIngredienteDAO;
import br.com.fiap.minichef.common.vo.CategoriaVO;
import br.com.fiap.minichef.common.vo.IngredienteVO;
import br.com.fiap.minichef.common.vo.ReceitaCategoriaVO;
import br.com.fiap.minichef.common.vo.ReceitaVO;
import br.com.fiap.minichef.common.vo.ItemIngredienteVO;

public class ReceitaSCN {

	private Context context;

	public ReceitaSCN(Context context) {
		this.context = context;
	}

	public long salvarReceita(ReceitaVO receita) {

		ReceitaDAO receitaDao = new ReceitaDAO(context);
		Long id = receitaDao.insert(receita);
		receitaDao.close();

		ItemIngredienteDAO itemIngredienteDao = new ItemIngredienteDAO(context);
		Integer idReceita = Integer.valueOf(id.toString());

		for (IngredienteVO ingVO : receita.getIngredientes()) {
			ItemIngredienteVO itemIngredienteVO = new ItemIngredienteVO();
			itemIngredienteVO.setIdReceita(idReceita);
			itemIngredienteVO.setIdIngrediente(ingVO.getId());
			itemIngredienteVO.setTipo(ReceitaVO.RECEITA);
			itemIngredienteVO.setQuantidade(ingVO.getQuantidade());
			itemIngredienteVO.setUnidadeMedida(ingVO.getUnidadeMedida());
			itemIngredienteDao.insert(itemIngredienteVO);
		}
		itemIngredienteDao.close();
		
		ReceitaCategoriaDAO receitaCatDAO = new ReceitaCategoriaDAO(context);
		for (CategoriaVO catVO : receita.getCategorias()) {
			ReceitaCategoriaVO recCatVO = new ReceitaCategoriaVO();
			recCatVO.setIdReceita(idReceita);
			recCatVO.setIdCategoria(catVO.getId());
			recCatVO.setTipo(ReceitaVO.RECEITA);
			receitaCatDAO.insert(recCatVO);
		}
		receitaCatDAO.close();

		return id;
	}

	public List<ReceitaVO> obterTodosReceitas() {
		ReceitaDAO receitaDao = new ReceitaDAO(context);
		List<ReceitaVO> receitas = receitaDao.selectAll();
		receitaDao.close();
				
		return receitas;
	}

	public List<ReceitaVO> obterReceitasPorMesEAno(String data) {
		
		String mes = data.substring(5,	7);
		String ano = data.substring(0, 4);
		
		ReceitaDAO receitaDao = new ReceitaDAO(context);
		List<ReceitaVO> receitas = receitaDao.selectByMesAno(mes, ano);
		receitaDao.close();
		
		return receitas;
	}
	
	public List<ReceitaVO> obterReceitasPorData(String data) {
		
		ReceitaDAO receitaDao = new ReceitaDAO(context);
		List<ReceitaVO> receitas = receitaDao.selectByData(data);
		receitaDao.close();
		
		return receitas;
	}
	
	public Integer obterProximoId(){
		ReceitaDAO receitaDao = new ReceitaDAO(context);
		Integer maxId = receitaDao.selectMaxId();
		receitaDao.close();
		
		Integer proxId = maxId + 1;
		
		return proxId;
	}
	
	public List<IngredienteVO> obterIngredientesPorId(Integer id){
		
		ItemIngredienteDAO itemIngDao = new ItemIngredienteDAO(context);
		List<ItemIngredienteVO> itemIngLista = itemIngDao.selectByIdTipo(id, ReceitaVO.RECEITA);
		itemIngDao.close();
		
		List<IngredienteVO> ingredientes = new ArrayList<IngredienteVO>();
		for (ItemIngredienteVO itemIngredienteVO : itemIngLista) {
			IngredienteSCN ingSCN = new IngredienteSCN(context);
			IngredienteVO ingredienteVO = new IngredienteVO();
			ingredienteVO = ingSCN.obterIngredientePorId(itemIngredienteVO.getIdIngrediente());
			ingredienteVO.setQuantidade(itemIngredienteVO.getQuantidade());
			ingredienteVO.setUnidadeMedida(itemIngredienteVO.getUnidadeMedida());
			ingredientes.add(ingredienteVO);
		}
		
		return ingredientes;
	}
	
	public List<ReceitaVO> obterReceitasPorIngrediente(IngredienteVO ingrediente) {
		
		// pegar a lista de ingredientes-receitas
		ItemIngredienteDAO itemIngDao = new ItemIngredienteDAO(context);
		List<ItemIngredienteVO> rcList = itemIngDao.selectByIdIngrediente(ingrediente.getId(), ReceitaVO.RECEITA);
		itemIngDao.close();
		
		ReceitaDAO receitaDao = new ReceitaDAO(context);
		List<ReceitaVO> receitas = new ArrayList<ReceitaVO>();
		
		for (ItemIngredienteVO itemIngredienteVO : rcList) {
			ReceitaVO receita = new ReceitaVO();
			receita = receitaDao.selectById(itemIngredienteVO.getIdReceita());
			receitas.add(receita);
		}
		receitaDao.close();
		
		return receitas;
	}
	
	public List<ReceitaVO> obterReceitasPorCategoria(CategoriaVO categoria) {
		
		// pegar a lista de categorias-receitas
		ReceitaCategoriaDAO recCatDao = new ReceitaCategoriaDAO(context);
		List<ReceitaCategoriaVO> rcList = recCatDao.selectByIdCategoria(categoria.getId(), ReceitaVO.RECEITA);
		recCatDao.close();
		
		ReceitaDAO receitaDao = new ReceitaDAO(context);
		List<ReceitaVO> receitas = new ArrayList<ReceitaVO>();
		
		for (ReceitaCategoriaVO receitaCategoriaVO : rcList) {
			ReceitaVO receita = new ReceitaVO();
			receita = receitaDao.selectById(receitaCategoriaVO.getIdReceita());
			receitas.add(receita);
		}
		receitaDao.close();
		
		return receitas;
	}
	
	public Boolean verSeTemReceita(String nome) {
		Boolean retorno;
		ReceitaDAO receitaDao = new ReceitaDAO(context);
		retorno = receitaDao.checksForByNome(nome);
		return retorno;
	}

}
