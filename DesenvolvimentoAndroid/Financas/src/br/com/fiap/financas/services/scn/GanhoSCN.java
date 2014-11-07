package br.com.fiap.financas.services.scn;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import br.com.fiap.financas.common.dao.GanhoDAO;
import br.com.fiap.financas.common.dao.RegCatDAO;
import br.com.fiap.financas.common.vo.CategoriaVO;
import br.com.fiap.financas.common.vo.GanhoVO;
import br.com.fiap.financas.common.vo.RegCatVO;

public class GanhoSCN {

	private Context context;

	public GanhoSCN(Context context) {
		this.context = context;
	}

	public long salvarGanho(GanhoVO ganho) {
		
		GanhoDAO ganhoDao = new GanhoDAO(context);
		Long id = ganhoDao.insert(ganho);
		ganhoDao.close();

		RegCatDAO regCatDao = new RegCatDAO(context);
		Integer idGanho = Integer.valueOf(id.toString());
		RegCatVO regCatVO = new RegCatVO();
		regCatVO.setIdRegistro(idGanho);
		regCatVO.setIdCategoria(ganho.getCategoria().getId());
		regCatVO.setTipo(GanhoVO.GANHO);
		regCatDao.insert(regCatVO);
		regCatDao.close();

		return id;
	}

	public List<GanhoVO> obterTodosGanhos() {
		GanhoDAO ganhoDao = new GanhoDAO(context);
		List<GanhoVO> ganhos = ganhoDao.selectAll();
		ganhoDao.close();
		
		for (GanhoVO ganhoVO : ganhos) {
			CategoriaVO catVo = obterCategoriasPorId(ganhoVO.getId()).get(0);
			ganhoVO.setCategoria(catVo);
		}
		
		return ganhos;
	}
	
	public List<GanhoVO> obterGanhosPorMesAno(String data){
		
		String mes = data.substring(5,	7);
		String ano = data.substring(0, 4);
		
		GanhoDAO ganhoDao = new GanhoDAO(context);
		List<GanhoVO> ganhos = ganhoDao.selectByMesAno(mes, ano);
		ganhoDao.close();
		
		for (GanhoVO ganhoVO : ganhos) {
			
			CategoriaVO catVo = obterCategoriasPorId(ganhoVO.getId()).get(0);
			
			ganhoVO.setCategoria(catVo);
		}
		
		return ganhos;
	}
	
	public List<GanhoVO> obterGanhosPorData(String data){
		
		GanhoDAO ganhoDao = new GanhoDAO(context);
		List<GanhoVO> ganhos = ganhoDao.selectByData(data);
		ganhoDao.close();
		
		for (GanhoVO ganhoVO : ganhos) {
			
			
			try {
				CategoriaVO catVo = obterCategoriasPorId(ganhoVO.getId()).get(0);
				
				if(catVo != null){
					ganhoVO.setCategoria(catVo);
				}else{
					Log.i(GanhoSCN.class + " - GANHOS", "CATEGORIA NÃO ENCONTRADA.");
				}
				
			} catch (Exception e) {
				Log.i(GanhoSCN.class + " - GANHOS", "ERRO NA CONSULTA DE CATEGORIAS PARA GANHOS" + e);
			}
			
		}
		
		return ganhos;
	}
	
	private List<CategoriaVO> obterCategoriasPorId(Integer id){
		
		RegCatDAO rcDao = new RegCatDAO(context);
		List<RegCatVO> rcLista = rcDao.selectByIdTipo(id, GanhoVO.GANHO);
		rcDao.close();
		
		CategoriaSCN catSCN = new CategoriaSCN(context);		
		List<CategoriaVO> categorias = new ArrayList<CategoriaVO>();
		for (RegCatVO regCatVO : rcLista) {
			categorias.add(catSCN.obterCategoriaPorId(regCatVO.getIdCategoria()));
		}
		
		return categorias;
	}	
	
	
}
