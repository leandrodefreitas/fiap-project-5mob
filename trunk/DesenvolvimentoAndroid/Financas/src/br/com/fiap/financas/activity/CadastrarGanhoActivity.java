package br.com.fiap.financas.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.com.fiap.R;
import br.com.fiap.financas.common.dao.CategoriaDAO;
import br.com.fiap.financas.common.dao.GanhoDAO;
import br.com.fiap.financas.common.dao.RegCatDAO;
import br.com.fiap.financas.common.vo.CategoriaVO;
import br.com.fiap.financas.common.vo.GanhoVO;
import br.com.fiap.financas.common.vo.RegCatVO;

public class CadastrarGanhoActivity extends Activity{
	
	private GanhoVO ganho = new GanhoVO();
	private RegCatVO regcat = new RegCatVO();
	private CategoriaVO categoria = new CategoriaVO();
	private TextView dataGanho;
	private EditText edtDescricao;
	private EditText edtValor;
	private Button btnSalvar;
	private Button btnVoltar;
	private Spinner spnCategoria;
	private EditText edtParcela;
	private EditText edtNumParcelas;
	private List<CategoriaVO> lista;
	private List<String> categorias;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_ganho);
		
		final GanhoDAO ganhoDao = new GanhoDAO(this);
		final RegCatDAO regCatDao = new RegCatDAO(this);
		final CategoriaDAO catDao = new CategoriaDAO(this);
		
		Bundle param = getIntent().getExtras();
		final String data = param.getString("data");
		dataGanho = (TextView) findViewById(R.id.lblDataGanho);
		Log.i("Teste", data);
		dataGanho.setText(data);
		
		edtDescricao = (EditText) findViewById(R.id.fDescricaoGanho);
		edtValor = (EditText) findViewById(R.id.fValorGanho);
		
		//populando spinner de categorias
		lista = catDao.selectAll();
		categorias = new ArrayList<String>();
		for (CategoriaVO categoria: lista){
			categorias.add(categoria.getDescricao());
		}
		
		if (categorias.isEmpty()) {
			Log.i("Spinner", "lista categorias vazia..");
		}
		
		spnCategoria = (Spinner) findViewById(R.id.spnCategoriaGanho);
		
		ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
		
		spnCategoria.setAdapter(catAdapter);
		
		spnCategoria.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				String selecionado = parent.getItemAtPosition(position).toString();
				for (CategoriaVO cat : lista){
					if (cat.getDescricao() == selecionado){
						categoria = cat;
					}
				}
				Log.i("Spinner", "Item selecionado: " + categoria.getDescricao() + " com id: " + categoria.getId());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
				
			}
		});
		
		
		edtParcela = (EditText) findViewById(R.id.fParcelaGanho);
		edtNumParcelas = (EditText) findViewById(R.id.fNumParcelasGanho);
		
		btnSalvar = (Button) findViewById(R.id.btnSalvarGanho);
		btnSalvar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				if (edtDescricao.getText().toString().length() == 0){
					edtDescricao.setError("Campo obrigatório");
					edtDescricao.requestFocus();
				} else	
				if (edtValor.getText().toString().length() == 0){
					edtValor.setError("Campo obrigatório");
					edtValor.requestFocus();
				} 
				else {
								
					ganho.setDescricao(edtDescricao.getText().toString());
					ganho.setValor(Double.valueOf(edtValor.getText().toString()));
					ganho.setData(data);
					ganho.setCategoria(categoria);
					
					if (!(edtParcela.getText().toString().length() == 0)){
						ganho.setParcela(Integer.valueOf(edtParcela.getText().toString()));						
					} else {
						ganho.setParcela(0);
					}
					
					if (!(edtNumParcelas.getText().toString().length() == 0)){
						ganho.setNumParcelas(Integer.valueOf(edtNumParcelas.getText().toString()));					
					} else {
						ganho.setNumParcelas(0);
					}

					Long id = ganhoDao.insert(ganho);
					
					regcat.setIdRegistro(Integer.valueOf(id.toString()));
					regcat.setIdCategoria(Integer.valueOf(categoria.getId()));
					
					regCatDao.insert(regcat);
					
					Toast.makeText(getApplicationContext(), "Cadastro realizado", Toast.LENGTH_SHORT).show();
					
				}
			}
		});
		
		btnVoltar = (Button) findViewById(R.id.btnSairGanho);
		btnVoltar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();		
			}
		});
		
	}
}
