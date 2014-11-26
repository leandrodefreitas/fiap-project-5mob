package br.com.fiap.minichef.services.scn;

import br.com.fiap.minichef.common.dao.IngredienteDAO;
import br.com.fiap.minichef.common.dao.ReceitaDAO;
import br.com.fiap.minichef.common.dao.ItemIngredienteDAO;
import android.content.Context;

public class ExcluirDadosSCN {
	
	private Context context;
	
	public ExcluirDadosSCN(Context context){
		this.context = context;		
	}
	
	public void excluiDadosReceitas(){
		ReceitaDAO gastoDao = new ReceitaDAO(context);
		gastoDao.deleteAll();
		gastoDao.close();		
	}
	
	public void excluiDadosIngredientes(){
		IngredienteDAO ingDao = new IngredienteDAO(context);
		ingDao.deleteAll();
		ingDao.close();
	}
	
	public void excluiDadosItemIngredientes(){
		ItemIngredienteDAO itemIngDao = new ItemIngredienteDAO(context);
		itemIngDao.deleteAll();
		itemIngDao.close();
	}
	
}
