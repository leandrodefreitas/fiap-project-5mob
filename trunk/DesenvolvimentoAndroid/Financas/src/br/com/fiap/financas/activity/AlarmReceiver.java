package br.com.fiap.financas.activity;

import br.com.fiap.financas.services.scn.GanhoSCN;
import br.com.fiap.financas.services.scn.GastoSCN;
import br.com.fiap.financas.util.Util;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

public class AlarmReceiver extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) {
		
		GanhoSCN ganhoA = new GanhoSCN(context);
		GastoSCN gastoA = new GastoSCN(context);
		Double ganhoTotal = ganhoA.obterTotalGanhos();
		Double gastoTotal = gastoA.obterTotalGastos();
		Double saldo = ganhoTotal - gastoTotal;
		
		String phoneNumberReciver=intent.getStringExtra("PhoneNumber");
        String message="Seu saldo é de " + Util.formataMoedaBRL(saldo);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumberReciver, null, message, null, null);
	}

}
