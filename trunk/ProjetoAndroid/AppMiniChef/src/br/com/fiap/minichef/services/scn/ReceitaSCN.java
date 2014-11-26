package br.com.fiap.minichef.services.scn;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import br.com.fiap.minichef.common.dao.ReceitaDAO;
import br.com.fiap.minichef.common.dao.ItemIngredienteDAO;
import br.com.fiap.minichef.common.vo.IngredienteVO;
import br.com.fiap.minichef.common.vo.ReceitaVO;
import br.com.fiap.minichef.common.vo.ItemIngredienteVO;

public class ReceitaSCN {

	private Context context;

	public ReceitaSCN(Context context) {
		this.context = context;
	}

	public long salvarReceita(ReceitaVO gasto) {

		ReceitaDAO receitaDao = new ReceitaDAO(context);
		Long id = receitaDao.insert(gasto);
		receitaDao.close();

		ItemIngredienteDAO itemIngredienteDao = new ItemIngredienteDAO(context);
		Integer idReceita = Integer.valueOf(id.toString());

		for (IngredienteVO catVO : gasto.getIngredientes()) {
			ItemIngredienteVO itemIngredienteVO = new ItemIngredienteVO();
			itemIngredienteVO.setIdReceita(idReceita);
			itemIngredienteVO.setIdIngrediente(catVO.getId());
			itemIngredienteVO.setTipo(ReceitaVO.GASTO);
			itemIngredienteDao.insert(itemIngredienteVO);
		}
		itemIngredienteDao.close();

		return id;
	}

	public List<ReceitaVO> obterTodosReceitas() {
		ReceitaDAO receitaDao = new ReceitaDAO(context);
		List<ReceitaVO> gastos = receitaDao.selectAll();
		receitaDao.close();
				
		return gastos;
	}

	public List<ReceitaVO> obterReceitasPorMesEAno(String data) {
		
		String mes = data.substring(5,	7);
		String ano = data.substring(0, 4);
		
		ReceitaDAO receitaDao = new ReceitaDAO(context);
		List<ReceitaVO> gastos = receitaDao.selectByMesAno(mes, ano);
		receitaDao.close();
		
		return gastos;
	}
	
	public List<ReceitaVO> obterReceitasPorData(String data) {
		
		ReceitaDAO receitaDao = new ReceitaDAO(context);
		List<ReceitaVO> gastos = receitaDao.selectByData(data);
		receitaDao.close();
		
		return gastos;
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
		List<ItemIngredienteVO> itemIngLista = itemIngDao.selectByIdTipo(id, ReceitaVO.GASTO);
		itemIngDao.close();
		
		List<IngredienteVO> ingredientes = new ArrayList<IngredienteVO>();
		for (ItemIngredienteVO itemIngredienteVO : itemIngLista) {
			IngredienteSCN ingSCN = new IngredienteSCN(context);
			ingredientes.add(ingSCN.obterIngredientePorId(itemIngredienteVO.getIdIngrediente()));
		}
		
		return ingredientes;
	}
	
	public Double obterTotalReceitasPorMesAno(String data) {
		Double total = 0.0;
		String mes = data.substring(5,	7);
		String ano = data.substring(0, 4);
		ReceitaDAO receitaDao = new ReceitaDAO(context);
		List<ReceitaVO> gastos = receitaDao.selectByMesAno(mes, ano);
		receitaDao.close();
		for(ReceitaVO gasto: gastos){
			total += gasto.getValor();
		}
		return total;
	}
	
	public Double obterTotalReceitasPorData(String data) {
		ReceitaDAO receitaDao = new ReceitaDAO(context);
		List<ReceitaVO> gastos = receitaDao.selectByData(data);
		receitaDao.close();
		Double total = 0.0;
		for(ReceitaVO gasto: gastos){
			total += gasto.getValor();
		}
		return total;
	}
	
	public Double obterTotalReceitas() {
		ReceitaDAO receitaDao = new ReceitaDAO(context);
		List<ReceitaVO> gastos = receitaDao.selectAll();
		receitaDao.close();
		Double total = 0.0;
		for(ReceitaVO gasto: gastos){
			total += gasto.getValor();
		}
		return total;
	}
	
	
	public List<ReceitaVO> obterReceitasPorIngrediente(IngredienteVO ingrediente) {
		
		
		// pegar a lista de ingredientes-receitas
		ItemIngredienteDAO itemIngDao = new ItemIngredienteDAO(context);
		List<ItemIngredienteVO> rcList = itemIngDao.selectByIdIngrediente(ingrediente.getId(), ReceitaVO.GASTO);
		itemIngDao.close();
		
		ReceitaDAO receitaDao = new ReceitaDAO(context);
		List<ReceitaVO> gastos = new ArrayList<ReceitaVO>();
		
		for (ItemIngredienteVO itemIngredienteVO : rcList) {
			ReceitaVO gasto = new ReceitaVO();
			gasto = receitaDao.selectById(itemIngredienteVO.getIdReceita());
			gastos.add(gasto);
		}
		receitaDao.close();
		
		return gastos;
	}

}
