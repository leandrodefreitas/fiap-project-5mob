package br.com.fiap.financas.activity;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.R;
import br.com.fiap.financas.adapter.CategoriaAdapter;
import br.com.fiap.financas.common.dao.CategoriaDAO;
import br.com.fiap.financas.common.vo.CategoriaVO;
import br.com.fiap.financas.common.vo.GanhoVO;
import br.com.fiap.financas.common.vo.GastoVO;
import br.com.fiap.financas.common.vo.RegCatVO;
import br.com.fiap.financas.util.Util;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class CadastrarGastoActivity extends Activity {

	private ListView listViewCategorias;
	private GastoVO gasto = new GastoVO();
	private GanhoVO ganho = new GanhoVO();
	private RegCatVO regcat = new RegCatVO();
	private CategoriaVO categoria = new CategoriaVO();
	private TextView dataGanho;
	private EditText edtDescricao;
	private EditText edtValor;
	private Button btnSalvar;
	private Button btnVoltar;
	private EditText edtParcela;
	private EditText edtNumParcelas;
	private ArrayList<CategoriaVO> categorias = new ArrayList<CategoriaVO>();
	private Spinner spnGanhoSelecao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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

		
		// arrumar seleccao de categorias
		CategoriaDAO dao = new CategoriaDAO(this);
		List<CategoriaVO> lista = dao.selectAll();
		listViewCategorias = (ListView) findViewById(R.id.listViewCategorias);
		listViewCategorias.setAdapter(new CategoriaAdapter(this, lista));

		
		// arrumar spinner de selecao de ganhos / setar adapter com dao
		spnGanhoSelecao = (Spinner) findViewById(R.id.spnGanhoSelecao);

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
					
					
					gasto.setCategorias(categorias);
					
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

}
