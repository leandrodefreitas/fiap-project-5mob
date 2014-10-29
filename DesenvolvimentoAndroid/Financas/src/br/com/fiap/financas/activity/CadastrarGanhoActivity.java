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
	private List<String> categorias = new ArrayList<String>();
	
	
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
		for (CategoriaVO categoria: lista){
			categorias.add(categoria.getDescricao());
		}
		
		spnCategoria.setAdapter(new ArrayAdapter<CategoriaVO>(getApplicationContext(), android.R.layout.simple_spinner_item, lista));
		
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
				
				ganho.setDescricao(edtDescricao.getText().toString());
				ganho.setValor(Double.valueOf(edtValor.getText().toString()));
				ganho.setData(data);
				ganho.setCategoria(categoria);
				ganho.setParcela(Integer.valueOf(edtParcela.getText().toString()));
				ganho.setNumParcelas(Integer.valueOf(edtNumParcelas.getText().toString()));
				
				Long id = ganhoDao.insert(ganho);
				
				regcat.setIdRegistro(Integer.valueOf(id.toString()));
				regcat.setIdCategoria(Integer.valueOf(categoria.getId()));
				
				regCatDao.insert(regcat);
				
				Log.i("Teste", edtDescricao.getText().toString());
				Log.i("Teste", edtValor.getText().toString());
				
				Toast.makeText(getApplicationContext(), "Cadastro realizado", Toast.LENGTH_SHORT).show();
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
