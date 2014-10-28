package br.com.fiap.financas.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.com.fiap.R;
import br.com.fiap.financas.common.dao.GanhoDAO;
import br.com.fiap.financas.common.dao.RegCatDAO;
import br.com.fiap.financas.common.vo.GanhoVO;
import br.com.fiap.financas.common.vo.RegCatVO;

public class CadastrarGanhoActivity extends Activity{
	
	private GanhoVO ganho = new GanhoVO();
	private RegCatVO regcat = new RegCatVO();
	private TextView dataGanho;
	private EditText edtDescricao;
	private EditText edtValor;
	private Button btnSalvar;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_ganho);
		
		final GanhoDAO ganhoDao = new GanhoDAO(this);
		final RegCatDAO regCatDao = new RegCatDAO(this);
		
		Bundle param = getIntent().getExtras();
		final String data = param.getString("data");
		dataGanho = (TextView) findViewById(R.id.lblDataGanho);
		Log.i("Teste", data);
		dataGanho.setText(data);
		
		edtDescricao = (EditText) findViewById(R.id.fDescricaoGanho);
		edtValor = (EditText) findViewById(R.id.fValorGanho);
		
		btnSalvar = (Button) findViewById(R.id.btnSalvarGanho);
		btnSalvar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ganho.setDescricao(edtDescricao.getText().toString());
				ganho.setValor(Double.valueOf(edtValor.getText().toString()));
				ganho.setData(data);
				//ganho.setCategoria();
				ganho.setParcela(Integer.valueOf("1"));
				ganho.setNumParcelas(Integer.valueOf("10"));
				
				Long id = ganhoDao.insert(ganho);
				
				regcat.setIdRegistro(Integer.valueOf(id.toString()));
				regcat.setIdCategoria(Integer.valueOf("1"));
				
				regCatDao.insert(regcat);
				
				Log.i("Teste", edtDescricao.getText().toString());
				Log.i("Teste", edtValor.getText().toString());
				
				Toast.makeText(getApplicationContext(), "Cadastro realizado", Toast.LENGTH_SHORT).show();
			}
		});
		
	}
}
