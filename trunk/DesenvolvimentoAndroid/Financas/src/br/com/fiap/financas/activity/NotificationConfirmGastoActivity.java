package br.com.fiap.financas.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.fiap.R;
import br.com.fiap.financas.common.vo.GastoVO;
import br.com.fiap.financas.services.scn.GastoSCN;
import br.com.fiap.financas.util.Util;

public class NotificationConfirmGastoActivity extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_confirm);
        
        GastoVO gastoC = ((GastoVO)getIntent().getSerializableExtra("vo"));
        
        TextView titulo = (TextView) findViewById(R.id.lblTitulo);
        TextView descricao = (TextView) findViewById(R.id.lblDescricao);
        TextView data = (TextView) findViewById(R.id.lblData);
        TextView valor = (TextView) findViewById(R.id.lblValor);
        
        titulo.setText("Deseja confirmar esse novo gasto?");
        descricao.setText(gastoC.getDescricao());
        valor.setText(Util.formataMoedaBRL(gastoC.getValor()));
        data.setText(Util.formataDataPadrao(gastoC.getData()));
        
        if (gastoC.getFoto() != null) {
        	Bitmap bitmap = BitmapFactory.decodeFile(gastoC.getFoto());
            ImageView ivFoto = (ImageView) findViewById(R.id.ivFoto);
    		ivFoto.setImageBitmap(bitmap);
        }
        
        Button btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        
        Button btnConfirmar = (Button) findViewById(R.id.btnConfirmar);
        btnConfirmar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GastoSCN controle = new GastoSCN(getApplicationContext());
				Long id = controle.salvarGasto(((GastoVO)getIntent().getSerializableExtra("vo")));
				
		    	if (id != -1) {
		    		Toast.makeText(getApplicationContext(), "Gasto cadastrado.", Toast.LENGTH_SHORT).show();
		    		//TODO Criar notifications
		    	} else {
		    		Toast.makeText(getApplicationContext(), "Erro no cadastro do Gasto. Tente novamente.", Toast.LENGTH_SHORT).show();				    		
		    	}
		    	finish();
			}
		});
        
    }

}
