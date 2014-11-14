package br.com.fiap.financas.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import br.com.fiap.R;
import br.com.fiap.financas.common.vo.GanhoVO;
import br.com.fiap.financas.services.scn.GanhoSCN;
import br.com.fiap.financas.util.Util;

public class NotificationConfirmGanhoActivity extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_confirm);
        
        GanhoVO ganhoC = ((GanhoVO)getIntent().getSerializableExtra("vo"));
        
        TextView titulo = (TextView) findViewById(R.id.lblTitulo);
        TextView descricao = (TextView) findViewById(R.id.lblDescricao);
        TextView data = (TextView) findViewById(R.id.lblData);
        TextView valor = (TextView) findViewById(R.id.lblValor);
        
        titulo.setText("Deseja confirmar esse novo ganho?");
        descricao.setText(ganhoC.getDescricao());
        valor.setText(Util.formataMoedaBRL(ganhoC.getValor()));
        data.setText(Util.formataDataPadrao(ganhoC.getData()));
        
        Button btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Ganho cancelado.", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
        
        Button btnConfirmar = (Button) findViewById(R.id.btnConfirmar);
        btnConfirmar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GanhoSCN controle = new GanhoSCN(getApplicationContext());
				Long id = controle.salvarGanho((GanhoVO) getIntent().getSerializableExtra("vo"));
				
		    	if (id != -1) {
		    		Toast.makeText(getApplicationContext(), "Ganho cadastrado.", Toast.LENGTH_SHORT).show();
		    	} else {
		    		Toast.makeText(getApplicationContext(), "Erro no cadastro do Ganho. Tente novamente.", Toast.LENGTH_SHORT).show();				    		
		    	}
		    	finish();
			}
		});
        
    }

}
