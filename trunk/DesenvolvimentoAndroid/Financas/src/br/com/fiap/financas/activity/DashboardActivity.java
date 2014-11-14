package br.com.fiap.financas.activity;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import br.com.fiap.R;
import br.com.fiap.financas.common.dao.DataSource;
import br.com.fiap.financas.services.scn.ExcluirDadosSCN;
import br.com.fiap.financas.util.Backup;

public class DashboardActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
		
		executarAlarm();
		
	}

	public void onClickCategorias(View v) {
		trace("Categorias");
		Intent i = new Intent(DashboardActivity.this, CategoriasActivity.class);
		startActivity(i);
	}

	public void onClickFinancas(View v) {
		trace("Finanças");
		Intent i = new Intent(DashboardActivity.this, FinancasFragActivity.class);
		startActivity(i);
	}
	
	public void onClickGraficos(View v) {
		trace("Gráficos");
		Intent i = new Intent(DashboardActivity.this, GraficosActivity.class);
		startActivity(i);
	}

	public void onClickBackup(View v) {
		
		new AlertDialog.Builder(this)
    	.setTitle("Backup")
    	.setMessage("Deseja realmente realizar o backup?")
    	.setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			try {
    				
    				File databasePath = getApplicationContext().getDatabasePath(DataSource.DATABASE_NAME).getParentFile();
    				String backupPathString = (Environment.getExternalStorageDirectory( ).getPath( ) + File.separator + "Backup");
    				File backupPath = new File(backupPathString);
    				
    				Backup bkp = new Backup();
					bkp.copyDirectory(databasePath, backupPath);
					
					trace("Backup dos dados realizado");
					
				} catch (IOException e) {
					e.printStackTrace();
				}
    			
    		}
    	})
    	.setNegativeButton(getString(R.string.nao), null)
    	.show();
		
	}
	
	public void onClickExcluirDados(View v) {
		
		new AlertDialog.Builder(this)
        	.setTitle("Excluir registros!")
        	.setMessage("Deseja realmente excluir todos os registros?")
        	.setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
        		@Override
        		public void onClick(DialogInterface dialog, int which) {
        			
        			ExcluirDadosSCN base = new ExcluirDadosSCN(getApplicationContext());
        			base.excluiDadosRegCat();
        			base.excluiDadosCategorias();
        			base.excluiDadosGanhos();
        			base.excluiDadosGastos();
        			
        			trace("Registros excluídos.");    
        		}
        	})
        	.setNegativeButton(getString(R.string.nao), null)
        	.show();
		
	}
	
	private AlertDialog alerta;

	@SuppressLint("InflateParams")
	public void onClickInfo(View v) {
		//LayoutInflater é utilizado para inflar nosso layout em uma view. 
		//-pegamos nossa instancia da classe 
		LayoutInflater li = getLayoutInflater(); 
		//inflamos o layout LAYOUT na view 
		View view = li.inflate(R.layout.informacoes, null); 
		//definimos para o botão do layout um clickListener 
		view.findViewById(R.id.btVoltar).setOnClickListener(new View.OnClickListener() { 
			public void onClick(View arg0) { alerta.dismiss(); } 
		}); 
		AlertDialog.Builder builder = new AlertDialog.Builder(this); 
		builder.setTitle(getString(R.string.informacoes));
		builder.setIcon(R.drawable.ic_informacoes);
		builder.setView(view); 
		alerta = builder.create(); 
		alerta.show();
	
	}
	
	public void onClickCalendario(View v) {
		Intent i = new Intent(DashboardActivity.this, CalendarActivity.class);
		startActivity(i);
	}
	
	private void trace(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
	
	public void onClickMapa(View v) {
		Intent i = new Intent(DashboardActivity.this, MapaActivity.class);
		startActivity(i);
	}
	
	public void executarAlarm(){
        
        TelephonyManager tm = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE); 
        String number = tm.getLine1Number();
        number = "964095505";
        if (number.equals("") || number.equals(null)) {
        	AccountManager am = AccountManager.get(this);
        	Account[] accounts = am.getAccounts();
        	String acname, actype = ""; String phoneNumber = "";

        	for (Account ac : accounts) {
        	    acname = ac.name;
        	    actype = ac.type;
        	    System.out.println(ac.toString());
        	    if(actype.equals("com.whatsapp")){
            	    phoneNumber = ac.toString();
            	    System.out.println(phoneNumber +"Accounts : " + acname + ", " + actype);
            	}
        	}
        }
        
        AlarmManager alarmMgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmSms.class);
        intent.putExtra("PhoneNumber", number);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 60 * 24, alarmIntent);
        
        /*
        boolean alarmeAtivo = (PendingIntent.getBroadcast(this, 0, new Intent("ALARME_DISPARADO"), PendingIntent.FLAG_NO_CREATE) == null);
		
		if(alarmeAtivo){
			Log.i("Script", "Novo alarme");
			
			Intent intent1 = new Intent("ALARME_DISPARADO");
			PendingIntent p = PendingIntent.getBroadcast(this, 0, intent1, Intent.FLAG_ACTIVITY_NEW_TASK);
			
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(System.currentTimeMillis());
			c.add(Calendar.SECOND, 3);
			
			
			Calendar calStart = new GregorianCalendar();
			calStart.setTime(new Date());
			calStart.set(Calendar.HOUR_OF_DAY, 12);
			calStart.set(Calendar.MINUTE, 0);
			calStart.set(Calendar.SECOND, 0);
			calStart.set(Calendar.MILLISECOND, 0);
			calStart.add(Calendar.DAY_OF_YEAR, 1); 
			
			Log.i("Script", "Data é "+ calStart.getTime().toString());
			
			long interval = AlarmManager.INTERVAL_DAY;
			
			AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);
			alarme.setRepeating(AlarmManager.RTC_WAKEUP, calStart.getTimeInMillis(), interval, p);
			
		}
		else{
			Log.i("Script", "Alarme já ativo");
		}*/
    }
	
}