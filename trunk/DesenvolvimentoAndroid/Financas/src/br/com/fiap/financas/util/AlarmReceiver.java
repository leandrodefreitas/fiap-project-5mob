package br.com.fiap.financas.util;

import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import br.com.fiap.R;
import br.com.fiap.financas.activity.DashboardActivity;
import br.com.fiap.financas.common.vo.GanhoVO;
import br.com.fiap.financas.common.vo.GastoVO;
import br.com.fiap.financas.services.scn.GanhoSCN;
import br.com.fiap.financas.services.scn.GastoSCN;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.i("Script", "Disparando Alarme");
		
		Double ganhoTotal = 0.0;
		Double gastoTotal = 0.0;
		Double saldo = 0.0;

		GanhoSCN ganhosA = new GanhoSCN(context);
		List<GanhoVO> ganhos = ganhosA.obterTodosGanhos();
		for (int i = 0; i < ganhos.size(); i++) {
			ganhoTotal += ganhos.get(i).getValor();
		}
		
		GastoSCN gastosA = new GastoSCN(context);
		List<GastoVO> gastos = gastosA.obterTodosGastos();
		for (int i = 0; i < gastos.size(); i++) {
			gastoTotal += gastos.get(i).getValor();
		}
		
		saldo = ganhoTotal - gastoTotal;
		
		
		gerarNotificacao(context, new Intent(context, DashboardActivity.class), "Nova mensagem", "Seu Saldo", "Saldo Atual: " + Util.formataMoedaBRL(saldo));
	}
	
	
	public void gerarNotificacao(Context context, Intent intent, CharSequence ticker, CharSequence titulo, CharSequence descricao){
		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		PendingIntent p = PendingIntent.getActivity(context, 0, intent, 0);
		
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		builder.setTicker(ticker);
		builder.setContentTitle(titulo);
		builder.setContentText(descricao);
		builder.setSmallIcon(R.drawable.rf_icon);
		builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));
		builder.setContentIntent(p);
		
		Notification n = builder.build();
		//n.vibrate = new long[]{150, 300, 150, 600};
		n.flags = Notification.FLAG_AUTO_CANCEL;
		nm.notify(R.drawable.ic_launcher, n);
		
	}
}
