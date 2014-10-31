package br.com.fiap.financas.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.com.fiap.R;
import br.com.fiap.financas.adapter.CategoriaAdapter;
import br.com.fiap.financas.common.dao.CategoriaDAO;
import br.com.fiap.financas.common.dao.GanhoDAO;
import br.com.fiap.financas.common.dao.GastoDAO;
import br.com.fiap.financas.common.dao.RegCatDAO;
import br.com.fiap.financas.common.vo.CategoriaVO;
import br.com.fiap.financas.common.vo.GanhoVO;
import br.com.fiap.financas.common.vo.GastoVO;
import br.com.fiap.financas.common.vo.RegCatVO;
import br.com.fiap.financas.util.Util;

public class CadastrarGastoActivity extends Activity {

	private ListView listViewCategorias;
	private GastoVO gasto = new GastoVO();
	private GanhoVO ganhoDescontar = new GanhoVO();
	private CategoriaVO categoria = new CategoriaVO();
	private TextView dataGanho;
	private EditText edtDescricao;
	private EditText edtValor;
	private Button btnSalvar;
	private Button btnVoltar;
	private EditText edtParcela;
	private EditText edtNumParcelas;
	private ArrayList<CategoriaVO> categoriasSelecionadas = new ArrayList<CategoriaVO>();
	private Spinner spnGanhos;
	private List<GanhoVO> listaGanhos;
	private List<String> listaGanhosString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		GanhoDAO ganhoDao = new GanhoDAO(this);
		final GastoDAO gastoDao = new GastoDAO(this);
		final RegCatDAO regCatDao = new RegCatDAO(this);
		CategoriaDAO catDao = new CategoriaDAO(this);		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_gasto);

		Bundle param = getIntent().getExtras();
		final String data = param.getString("data");
		dataGanho = (TextView) findViewById(R.id.lblDataGasto);
		dataGanho.setText(Util.imprimeDataFormatoBR(data));

		edtDescricao = (EditText) findViewById(R.id.fDescricaoGasto);
		edtValor = (EditText) findViewById(R.id.fValorGasto);
		edtParcela = (EditText) findViewById(R.id.fParcelaGasto);
		edtNumParcelas = (EditText) findViewById(R.id.fNumParcelasGasto);

		// ListView de categorias
		List<CategoriaVO> listaCategorias = catDao.selectAll();
		listViewCategorias = (ListView) findViewById(R.id.listViewCategorias);
		listViewCategorias.setAdapter(new CategoriaAdapter(this, listaCategorias));
		// metodo para mostrar todos as categorias no listview
		calculeHeightListView();
		
		
		// Populando spinner de ganhos
		listaGanhos = ganhoDao.selectAll();
		listaGanhosString = new ArrayList<String>();
		for (GanhoVO ganho: listaGanhos){
			listaGanhosString.add(ganho.getDescricao() + " de " + Util.imprimeDataFormatoBR(ganho.getDataFormatted()));
		}
		
		
		if (listaGanhosString.isEmpty()) {
			Log.i("Spinner", "lista ganhos vazia..");
		}
		spnGanhos = (Spinner) findViewById(R.id.spnGanhoSelecao);
		//ArrayAdapter<String> ganhoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaGanhosString);
		ArrayAdapter<GanhoVO> ganhoAdapter = new ArrayAdapter<GanhoVO>(this, android.R.layout.simple_spinner_dropdown_item, listaGanhos);
		spnGanhos.setAdapter(ganhoAdapter);
		spnGanhos.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				ganhoDescontar = (GanhoVO) parent.getItemAtPosition(position);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});

		
		listViewCategorias.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				CategoriaAdapter catAdapter = ((CategoriaAdapter) parent.getAdapter());
				
				CategoriaVO catVo = (CategoriaVO) catAdapter.getItem(position);

				Log.i("Teste onitemclick list", catVo.getDescricao());
				
				CheckedTextView ctv = (CheckedTextView) ((LinearLayout) view).findViewById(R.id.categoriaItem);
				
			    if(!ctv.isChecked()) {
			        if(!categoriasSelecionadas.contains(catVo)) {
			        	categoriasSelecionadas.add(catVo);			        	
			        }
			    } else {
			         if(categoriasSelecionadas.contains(catVo))
			            categoriasSelecionadas.remove(catVo);
			    }
			    
				if(categoriasSelecionadas.contains(catVo)) {
				    ctv.setChecked(true);
				} else {
				    ctv.setChecked(false);
				}				
				
				
			}
		});

		btnSalvar = (Button) findViewById(R.id.btnSalvarGasto);
		btnSalvar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (edtDescricao.getText().toString().length() == 0){
					edtDescricao.setError(getString(R.string.campo_obrigatorio));
					edtDescricao.requestFocus();
				} else	
				if (edtValor.getText().toString().length() == 0){
					edtValor.setError(getString(R.string.campo_obrigatorio));
					edtValor.requestFocus();
				} 
				else {				
					
					gasto.setDescricao(edtDescricao.getText().toString());
					gasto.setValor(Double.valueOf(edtValor.getText().toString()));
					gasto.setData(data);
					
					
					for (int i = 0; i < categoriasSelecionadas.size(); i++) {
						Log.i("Array categorias: ", String.valueOf(categoriasSelecionadas.get(i).getDescricao()));
					}
					gasto.setCategorias(categoriasSelecionadas);
					
					
					gasto.setGanhoDescontar(ganhoDescontar);
					
					if (!(edtParcela.getText().toString().length() == 0)){
						gasto.setParcela(Integer.valueOf(edtParcela.getText().toString()));						
					} else {
						gasto.setParcela(0);
					}
					
					if (!(edtNumParcelas.getText().toString().length() == 0)){
						gasto.setNumParcelas(Integer.valueOf(edtNumParcelas.getText().toString()));					
					} else {
						gasto.setNumParcelas(0);
					}
					
					gasto.setLocal("um lugar no meio do nada");
					
					gasto.setFoto("foto");

					
					Long id = gastoDao.insert(gasto);
					
					Integer idGasto = Integer.valueOf(id.toString());
					
					for (CategoriaVO catVO : categoriasSelecionadas) {
						RegCatVO regCatVO = new RegCatVO();
						regCatVO.setIdRegistro(idGasto);
						regCatVO.setIdCategoria(catVO.getId());
						
						regCatDao.insert(regCatVO);						
					}
					
					Toast.makeText(getApplicationContext(), "Cadastro realizado", Toast.LENGTH_SHORT).show();					

				}
			}
		});

		btnVoltar = (Button) findViewById(R.id.btnSairGasto);
		btnVoltar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
    private void calculeHeightListView() {  
        int totalHeight = 0;  
  
        ListAdapter adapter = listViewCategorias.getAdapter();  
        int lenght = adapter.getCount();  
  
        for (int i = 0; i < lenght; i++) {  
            View listItem = adapter.getView(i, null, listViewCategorias);  
            listItem.measure(0, 0);  
            totalHeight += listItem.getMeasuredHeight();  
        }  
  
        ViewGroup.LayoutParams params = listViewCategorias.getLayoutParams();  
        params.height = totalHeight  
                + (listViewCategorias.getDividerHeight() * (adapter.getCount() - 1));  
        listViewCategorias.setLayoutParams(params);  
        listViewCategorias.requestLayout();  
    }  	

}
