package br.com.fiap.financas.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import br.com.fiap.R;
import br.com.fiap.financas.adapter.FinancasGastosAdapter;
import br.com.fiap.financas.common.vo.CategoriaVO;
import br.com.fiap.financas.common.vo.GastoVO;
import br.com.fiap.financas.services.scn.CategoriaSCN;
import br.com.fiap.financas.services.scn.GastoSCN;

public class CategoriasActivity extends Activity {

	private CategoriaVO categoria = new CategoriaVO();
	
	private CategoriaSCN controleCategoria;
	
	private EditText edtCategoria; 
	
	private ListView lista;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categorias);
        listarCategorias();
    }
    
    public void onClickNova(View v){
    	setContentView(R.layout.cadastro_categoria);
    	Toast.makeText(getApplicationContext(), "Nova Categoria", Toast.LENGTH_SHORT).show();
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
	        	listarCategorias();
	    	}  	
		}
    }
    
    public void onSairCategoria(View v) {
    	finish();
    }
    
    public void voltarCategoria(View v) {
    	setContentView(R.layout.categorias);
    	listarCategorias();
    }
    
    private void listarCategorias() {
    	ListView lvCategorias = (ListView) findViewById(R.id.lvCategoriasLista);
    	
    	List<CategoriaVO> listaCategorias = new ArrayList<CategoriaVO>();
    	controleCategoria = new CategoriaSCN(getApplicationContext());
    	listaCategorias = controleCategoria.obterTodasCategorias();
    	ArrayAdapter<CategoriaVO> catAdapter = new ArrayAdapter<CategoriaVO>(this,android.R.layout.simple_spinner_dropdown_item, listaCategorias);
    	
    	lvCategorias.setAdapter(catAdapter);
    	
    	lvCategorias.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				categoria = (CategoriaVO) parent.getItemAtPosition(position);
				
				setContentView(R.layout.categoria_lista);
				List<GastoVO> gastosList = new ArrayList<GastoVO>();
		    	GastoSCN gastoSCN =  new GastoSCN(getApplicationContext());
		    	gastosList = gastoSCN.obterGastosPorCategoria(categoria);
		    	
				lista = (ListView) findViewById(R.id.lvGastosCategorias);
				FinancasGastosAdapter adapter = new FinancasGastosAdapter(getApplicationContext(), gastosList);
				lista.setAdapter(adapter);
				
				Toast.makeText(getApplicationContext(), "Lista de gastos por categoria", Toast.LENGTH_SHORT).show();
				
			}
		});
    	
    }
}
