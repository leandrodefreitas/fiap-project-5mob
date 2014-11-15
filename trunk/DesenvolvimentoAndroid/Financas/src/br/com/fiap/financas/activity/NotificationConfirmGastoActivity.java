package br.com.fiap.financas.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
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
import br.com.fiap.financas.services.scn.GanhoSCN;
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
        	ImageView ivFoto = (ImageView) findViewById(R.id.ivFoto);
        	
        	BitmapFactory.Options o2 = new BitmapFactory.Options();
	        o2.inSampleSize = 10;
	        o2.inScaled = true;
        	
        	Bitmap bitmap = BitmapFactory.decodeFile(gastoC.getFoto(), o2);
    		ivFoto.setImageBitmap(bitmap);
        }
        
        Button btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Gasto cancelado.", Toast.LENGTH_SHORT).show();
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
		    		GastoSCN gastoScn = new GastoSCN(getApplicationContext());
					GanhoSCN ganhoScn = new GanhoSCN(getApplicationContext());
					Double ganhoTotal = ganhoScn.obterTotalGanhos();
					Double gastoTotal = gastoScn.obterTotalGastos();
					Double saldo = ganhoTotal - gastoTotal;
					if (saldo < 0) { 
						createNotificationSaldoNegativo(); 
					}
		    	} else {
		    		Toast.makeText(getApplicationContext(), "Erro no cadastro do Gasto. Tente novamente.", Toast.LENGTH_SHORT).show();				    		
		    	}
		    	finish();
			}
		});
        
    }
	
	public void createNotificationSaldoNegativo(){

        Notification notifica = new Notification.Builder(this)
                .setContentTitle("Saldo negativo")
                .setContentText("Seu saldo está negativo, cadastre um ganho o quanto antes.")
                .setSmallIcon(R.drawable.rf_icon).setAutoCancel(true).build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifica.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notifica);
    }

}
