package br.com.fiap.financas.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import br.com.fiap.R;
import br.com.fiap.financas.adapter.CategoriaAdapter;
import br.com.fiap.financas.common.dao.CategoriaDAO;
import br.com.fiap.financas.common.vo.CategoriaVO;
import br.com.fiap.financas.services.scn.CategoriaSCN;

public class CategoriasActivity extends Activity {

	private CategoriaVO categoria = new CategoriaVO();
	
	private CategoriaSCN controleCategoria;
	
	private EditText edtCategoria; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categorias);
    }
    
    public void onClickNova(View v){
    	
    	setContentView(R.layout.cadastro_categoria);

    	Toast.makeText(getApplicationContext(), "Nova Categoria", Toast.LENGTH_SHORT).show();
    }

    public void onClickLista(View v){
    	
    	setContentView(R.layout.categoria_lista);
    	
    	ListView lvCategorias = (ListView) findViewById(R.id.lvCategoriasLista);
    	
    	List<CategoriaVO> listaCategorias = new ArrayList<CategoriaVO>();
    	controleCategoria = new CategoriaSCN(getApplicationContext());
    	listaCategorias = controleCategoria.obterTodasCategorias();
    	ArrayAdapter<CategoriaVO> catAdapter = new ArrayAdapter<CategoriaVO>(this,android.R.layout.simple_spinner_dropdown_item, listaCategorias);
    	
    	lvCategorias.setAdapter(catAdapter);
    	
  	
    	
    	Toast.makeText(getApplicationContext(), "Lista Categoria", Toast.LENGTH_SHORT).show();
    }
    
    
    public void onSalvarCategoria(View v){
    	
    	edtCategoria = (EditText) findViewById(R.id.fCategoria);
    	
    	
		if (edtCategoria.getText().toString().length() == 0){
			edtCategoria.setError(getString(R.string.campo_obrigatorio));
			edtCategoria.requestFocus();
		} else {
			
	    	categoria.setDescricao(edtCategoria.getText().toString());
	    	
	    	controleCategoria = new CategoriaSCN(getApplicationContext());
	    	long id = controleCategoria.salvarCategoria(categoria); 	
	    	
	    	if (id != -1) {
	        	Toast.makeText(getApplicationContext(), "Categoria salva", Toast.LENGTH_SHORT).show();
	        	setContentView(R.layout.categorias);
	    	}  	
	    	
		}

    }
    
    public void onSairCategoria(View v){
    	setContentView(R.layout.categorias);    	
    }
}
