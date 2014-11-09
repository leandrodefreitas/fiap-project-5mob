package br.com.fiap.financas.services.scn;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import br.com.fiap.financas.common.dao.GastoDAO;
import br.com.fiap.financas.common.dao.RegCatDAO;
import br.com.fiap.financas.common.vo.CategoriaVO;
import br.com.fiap.financas.common.vo.GastoVO;
import br.com.fiap.financas.common.vo.RegCatVO;

public class GastoSCN {

	private Context context;

	public GastoSCN(Context context) {
		this.context = context;
	}

	public long salvarGasto(GastoVO gasto) {

		GastoDAO gastoDao = new GastoDAO(context);
		Long id = gastoDao.insert(gasto);
		gastoDao.close();

		RegCatDAO regCatDao = new RegCatDAO(context);
		Integer idGasto = Integer.valueOf(id.toString());

		for (CategoriaVO catVO : gasto.getCategorias()) {
			RegCatVO regCatVO = new RegCatVO();
			regCatVO.setIdRegistro(idGasto);
			regCatVO.setIdCategoria(catVO.getId());
			regCatVO.setTipo(GastoVO.GASTO);
			regCatDao.insert(regCatVO);
		}
		regCatDao.close();

		return id;
	}


	public List<GastoVO> obterTodosGastos() {
		GastoDAO gastoDao = new GastoDAO(context);
		List<GastoVO> gastos = gastoDao.selectAll();
		gastoDao.close();
		
		for(GastoVO gasto: gastos){
			gasto.setCategorias(obterCategoriasPorId(gasto.getId()));
		}
		
		return gastos;
	}

	public List<GastoVO> obterGastosPorMesEAno(String data) {
		
		String mes = data.substring(5,	7);
		String ano = data.substring(0, 4);
		
		GastoDAO gastoDao = new GastoDAO(context);
		List<GastoVO> gastos = gastoDao.selectByMesAno(mes, ano);
		gastoDao.close();
		
		for(GastoVO gasto: gastos){
			gasto.setCategorias(obterCategoriasPorId(gasto.getId()));
		}
		
		return gastos;
	}
	
	public List<GastoVO> obterGastosPorData(String data) {
		
		GastoDAO gastoDao = new GastoDAO(context);
		List<GastoVO> gastos = gastoDao.selectByData(data);
		gastoDao.close();
		
		for(GastoVO gasto: gastos){
			gasto.setCategorias(obterCategoriasPorId(gasto.getId()));
		}
		
		return gastos;
	}
	
	public Integer obterProximoId(){
		GastoDAO gastoDao = new GastoDAO(context);
		Integer maxId = gastoDao.selectMaxId();
		gastoDao.close();
		
		Integer proxId = maxId + 1;
		
		return proxId;
	}	
	
	public Double obterSomaGastosPorGanho(Integer idGanho){
		
		GastoDAO gastoDao = new GastoDAO(context);
		Double somaGastos = gastoDao.selectSumGastosByGanho(idGanho);
		gastoDao.close();
		
		return somaGastos;
	}	
	
	private List<CategoriaVO> obterCategoriasPorId(Integer id){
		
		RegCatDAO rcDao = new RegCatDAO(context);
		List<RegCatVO> rcLista = rcDao.selectByIdTipo(id, GastoVO.GASTO);
		rcDao.close();
		
		List<CategoriaVO> categorias = new ArrayList<CategoriaVO>();
		for (RegCatVO regCatVO : rcLista) {
			CategoriaSCN catSCN = new CategoriaSCN(context);
			categorias.add(catSCN.obterCategoriaPorId(regCatVO.getIdCategoria()));
		}
		
		return categorias;
	}	

}
