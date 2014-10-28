package br.com.fiap.financas.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import br.com.fiap.R;
import br.com.fiap.financas.common.dao.CategoriaDAO;
import br.com.fiap.financas.common.vo.CategoriaVO;

public class CategoriasActivity extends Activity {

	private CategoriaVO categoria = new CategoriaVO();
	
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
    	
    	Intent i = new Intent(this, ListarCategoriasActivity.class);
    	startActivity(i);
    	
    	Toast.makeText(getApplicationContext(), "Lista Categoria", Toast.LENGTH_SHORT).show();
    }
    
    
    public void onSalvarCategoria(View v){
    	
    	edtCategoria = (EditText) findViewById(R.id.fCategoria);
    	
    	categoria.setDescricao(edtCategoria.getText().toString());
    	
    	CategoriaDAO dao = new CategoriaDAO(this);
    	long id = dao.insert(categoria);   	
    	
    	if (id != -1) {
        	Toast.makeText(getApplicationContext(), "Categoria salva", Toast.LENGTH_SHORT).show();
        	setContentView(R.layout.categorias);
    	}  	
    }
}
