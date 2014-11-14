package br.com.fiap.financas.activity;

import br.com.fiap.R;
import br.com.fiap.financas.services.scn.GanhoSCN;
import br.com.fiap.financas.services.scn.GastoSCN;
import br.com.fiap.financas.util.Util;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

public class AlarmSms extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) {
		
		GanhoSCN ganhoA = new GanhoSCN(context);
		GastoSCN gastoA = new GastoSCN(context);
		Double ganhoTotal = ganhoA.obterTotalGanhos();
		Double gastoTotal = gastoA.obterTotalGastos();
		Double saldo = ganhoTotal - gastoTotal;
		
		String telefone = intent.getStringExtra("PhoneNumber");
		
		if (telefone.equals("WhatsApp") || telefone.equals("") || telefone.equals(null) ) {
			Notification notifica = new Notification.Builder(context)
            .setContentTitle("Saldo negativo")
            .setContentText("Seu saldo está negativo, cadastre um ganho o quanto antes.")
            .setSmallIcon(R.drawable.rf_icon).build();

		    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		    notifica.flags |= Notification.FLAG_AUTO_CANCEL;
		    notificationManager.notify(0, notifica);
        } else {
        	String message="Seu saldo é de " + Util.formataMoedaBRL(saldo);
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(telefone, null, message, null, null);
        }
	}

}
