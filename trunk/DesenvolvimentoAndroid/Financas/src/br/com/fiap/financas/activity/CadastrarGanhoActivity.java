package br.com.fiap.financas.activity;

import java.util.List;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
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
import br.com.fiap.financas.common.vo.CategoriaVO;
import br.com.fiap.financas.common.vo.GanhoVO;
import br.com.fiap.financas.services.scn.CategoriaSCN;
import br.com.fiap.financas.util.Util;

public class CadastrarGanhoActivity extends Activity {

	private GanhoVO ganho = new GanhoVO();
	private CategoriaVO categoria = new CategoriaVO();
	private TextView dataGanho;
	private EditText edtDescricao;
	private EditText edtValor;
	private Button btnSalvar;
	private Button btnVoltar;
	private Spinner spnCategoria;
	private EditText edtParcela;
	private EditText edtNumParcelas;
	private List<CategoriaVO> listaCategorias;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_ganho);

		Bundle param = getIntent().getExtras();
		final String data = param.getString("data");
		dataGanho = (TextView) findViewById(R.id.lblDataGanho);
		Log.i("Teste", data);
		Log.i("Teste", Util.imprimeDataFormatoBR(data));
		dataGanho.setText(Util.imprimeDataFormatoBR(data));

		edtDescricao = (EditText) findViewById(R.id.fDescricaoGanho);
		edtValor = (EditText) findViewById(R.id.fValorGanho);

		// populando spinner de categorias
		CategoriaSCN controleCategoria = new CategoriaSCN(
				getApplicationContext());
		listaCategorias = controleCategoria.obterTodasCategorias();

		if (listaCategorias.isEmpty()) {
			Log.i("Spinner", "lista categorias vazia..");
			Toast.makeText(getApplicationContext(),
					"Nenhuma categoria cadastrada...", Toast.LENGTH_SHORT).show();
			finish();
		}

		spnCategoria = (Spinner) findViewById(R.id.spnCategoriaGanho);
		ArrayAdapter<CategoriaVO> catAdapter = new ArrayAdapter<CategoriaVO>(
				this, android.R.layout.simple_spinner_dropdown_item,
				listaCategorias);
		spnCategoria.setAdapter(catAdapter);
		spnCategoria.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				String selecionado = parent.getItemAtPosition(position)
						.toString();
				for (CategoriaVO cat : listaCategorias) {
					if (cat.getDescricao() == selecionado) {
						categoria = cat;
					}
				}
				Log.i("Spinner",
						"Item selecionado: " + categoria.getDescricao()
								+ " com id: " + categoria.getId());
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
				
				ganho = new GanhoVO();

				if (edtDescricao.getText().toString().length() == 0) {
					edtDescricao
							.setError(getString(R.string.campo_obrigatorio));
					edtDescricao.requestFocus();
				} else if (edtValor.getText().toString().length() == 0) {
					edtValor.setError(getString(R.string.campo_obrigatorio));
					edtValor.requestFocus();
				} else {

					ganho.setDescricao(edtDescricao.getText().toString());
					ganho.setValor(Double
							.valueOf(edtValor.getText().toString()));
					ganho.setData(data);
					ganho.setCategoria(categoria);

					if (!(edtParcela.getText().toString().length() == 0)) {
						ganho.setParcela(Integer.valueOf(edtParcela.getText()
								.toString()));
					} else {
						ganho.setParcela(0);
					}

					if (!(edtNumParcelas.getText().toString().length() == 0)) {
						ganho.setNumParcelas(Integer.valueOf(edtNumParcelas
								.getText().toString()));
					} else {
						ganho.setNumParcelas(0);
					}
					
					createNotificationConfirm();
					
					finish();
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
	
	public void createNotificationConfirm(){
    	
        Intent intent = new Intent(this, NotificationConfirmGanhoActivity.class);
        intent.putExtra("vo", ganho);
        PendingIntent pIntent = PendingIntent.getActivity(this,0,intent,Intent.FLAG_ACTIVITY_NEW_TASK);

        Notification notf = new Notification.Builder(this)
                .setContentTitle("Novo ganho")
                .setContentText("Confirme ou cancele esse novo registro.").setSmallIcon(R.drawable.rf_icon)
                .setContentIntent(pIntent)
                .addAction(R.drawable.appicon, "btn1", pIntent).build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notf.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, notf);
    }
	
}
