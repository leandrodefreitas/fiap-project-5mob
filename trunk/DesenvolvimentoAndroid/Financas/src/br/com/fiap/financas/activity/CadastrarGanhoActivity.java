package br.com.fiap.financas.activity;

import br.com.fiap.R;
import br.com.fiap.financas.common.dao.RegistroDAO;
import br.com.fiap.financas.common.vo.RegistroVO;
import br.com.fiap.financas.common.vo.RegistroVO.Tipo;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CadastrarGanhoActivity extends Activity{
	
	private static final Tipo ganho = Tipo.GANHO;	
	private RegistroVO registroGanho = new RegistroVO();
	private TextView dataGanho;
	private EditText edtDescricao;
	private EditText edtValor;
	private Button btnSalvar;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_ganho);
		
		final RegistroDAO dao = new RegistroDAO(this);
		
		Bundle param = getIntent().getExtras();
		String data = param.getString("data");
		dataGanho = (TextView) findViewById(R.id.lblDataGanho);
		Log.i("Teste", data);
		dataGanho.setText(data);
		
		edtDescricao = (EditText) findViewById(R.id.fDescricaoGanho);
		edtValor = (EditText) findViewById(R.id.fValorGanho);
		
		btnSalvar = (Button) findViewById(R.id.btnSalvarGanho);
		btnSalvar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				registroGanho.setTipo(ganho);
				registroGanho.setDescricao(edtDescricao.getText().toString());
				registroGanho.setValor(Double.valueOf(edtValor.getText().toString()));
				
				//registroGanho.setCodigo(1);
				
				dao.insert(registroGanho);
				Log.i("Teste", edtDescricao.getText().toString());
				Log.i("Teste", edtValor.getText().toString());
				
				Toast.makeText(getApplicationContext(), "Cadastro realizado", Toast.LENGTH_SHORT).show();
			}
		});
		
	}
}
