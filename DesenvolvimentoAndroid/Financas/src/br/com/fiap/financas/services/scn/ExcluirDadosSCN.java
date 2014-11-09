package br.com.fiap.financas.services.scn;

import br.com.fiap.financas.common.dao.CategoriaDAO;
import br.com.fiap.financas.common.dao.GanhoDAO;
import br.com.fiap.financas.common.dao.GastoDAO;
import br.com.fiap.financas.common.dao.RegCatDAO;
import android.content.Context;

public class ExcluirDadosSCN {
	
	private Context context;
	
	public ExcluirDadosSCN(Context context){
		this.context = context;		
	}
	
	public void excluiDadosGanhos(){
		GanhoDAO ganhoDao = new GanhoDAO(context);
		ganhoDao.deleteAll();
		ganhoDao.close();
	}
	
	public void excluiDadosGastos(){
		GastoDAO gastoDao = new GastoDAO(context);
		gastoDao.deleteAll();
		gastoDao.close();		
	}
	
	public void excluiDadosCategorias(){
		CategoriaDAO catDao = new CategoriaDAO(context);
		catDao.deleteAll();
		catDao.close();
	}
	
	public void excluiDadosRegCat(){
		RegCatDAO rcDao = new RegCatDAO(context);
		rcDao.deleteAll();
		rcDao.close();
	}
	
}
