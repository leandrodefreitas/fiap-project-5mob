package br.com.fiap.financas.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.com.fiap.R;
import br.com.fiap.financas.adapter.CalendarAdapter;
import br.com.fiap.financas.common.vo.GanhoVO;
import br.com.fiap.financas.common.vo.GastoVO;
import br.com.fiap.financas.services.scn.GanhoSCN;
import br.com.fiap.financas.services.scn.GastoSCN;
import br.com.fiap.financas.util.Util;

public class CalendarActivity extends Activity {
	public GregorianCalendar month, itemmonth;

	public CalendarAdapter adapter;
	public Handler handler;
	public ArrayList<String> itemsGanho;
	public ArrayList<String> itemsGasto;
	
	private static final int GANHO_ID = Menu.FIRST;
	private static final int GASTO_ID = Menu.FIRST + 1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar);
		Locale.setDefault(Locale.US);
		month = (GregorianCalendar) GregorianCalendar.getInstance();
		itemmonth = (GregorianCalendar) month.clone();

		itemsGanho = new ArrayList<String>();
		itemsGasto = new ArrayList<String>();
		adapter = new CalendarAdapter(this, month);

		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(adapter);
		
		//registerForContextMenu(gridview);

		handler = new Handler();
		handler.post(calendarUpdater);

		TextView title = (TextView) findViewById(R.id.title);
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month)
				.toString().toUpperCase());

		RelativeLayout previous = (RelativeLayout) findViewById(R.id.previous);

		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setPreviousMonth();
				refreshCalendar();
			}
		});

		RelativeLayout next = (RelativeLayout) findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setNextMonth();
				refreshCalendar();

			}
		});

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				((CalendarAdapter) parent.getAdapter()).setSelected(v);
				String selectedGridDate = CalendarAdapter.dayString
						.get(position);
				String[] separatedTime = selectedGridDate.split("-");
				String gridvalueString = separatedTime[2].replaceFirst("^0*",
						"");
				int gridvalue = Integer.parseInt(gridvalueString);
				if ((gridvalue > 10) && (position < 8)) {
					setPreviousMonth();
					refreshCalendar();
				} else if ((gridvalue < 7) && (position > 28)) {
					setNextMonth();
					refreshCalendar();
				}
				((CalendarAdapter) parent.getAdapter()).setSelected(v);

				showToast(Util.imprimeDataFormatoBR(selectedGridDate));

			}
		});
		
		gridview.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				
				AdapterContextMenuInfo info =  (AdapterContextMenuInfo) menuInfo;
				String selectedGridDate = CalendarAdapter.dayString
				.get(info.position);
				menu.setHeaderTitle("Registro " + Util.imprimeDataFormatoBR(selectedGridDate));
				menu.add(0, GANHO_ID, 0, "Novo Ganho");
				menu.add(0, GASTO_ID, 0, "Novo Gasto");				
			}
		}) ;
		
		
	}

	protected void setNextMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMaximum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) + 1),
					month.getActualMinimum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) + 1);
		}

	}

	protected void setPreviousMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) - 1);
		}

	}

	protected void showToast(String string) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

	}

	public void refreshCalendar() {
		TextView title = (TextView) findViewById(R.id.title);

		adapter.refreshDays();
		adapter.notifyDataSetChanged();
		handler.post(calendarUpdater);

		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month)
				.toString().toUpperCase());

	}

	public Runnable calendarUpdater = new Runnable() {

		@Override
		public void run() {
			itemsGanho.clear();
			itemsGasto.clear();
			itemmonth = (GregorianCalendar) month.clone();
			
			Double ganhoTotal = 0.0;
			Double gastoTotal = 0.0;
			Double saldo = 0.0;

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			String itemvalue;
			
			itemvalue = df.format(itemmonth.getTime());
			itemmonth.add(GregorianCalendar.DATE, 1);
			
			GanhoSCN ganhosA = new GanhoSCN(getApplicationContext());
			List<GanhoVO> ganhos = ganhosA.obterGanhosPorMesAno(itemvalue);
			for (int i = 0; i < ganhos.size(); i++) {
				String itemGanho = df.format(ganhos.get(i).getData().getTime());
				ganhoTotal += ganhos.get(i).getValor();
				itemsGanho.add(itemGanho);
			}
			
			GastoSCN gastosA = new GastoSCN(getApplicationContext());
			List<GastoVO> gastos = gastosA.obterGastosPorMesEAno(itemvalue);
			for (int i = 0; i < gastos.size(); i++) {
				String itemGasto = df.format(gastos.get(i).getData().getTime());
				gastoTotal += gastos.get(i).getValor();
				itemsGasto.add(itemGasto);
			}
			
			saldo = ganhoTotal - gastoTotal;
			
			TextView lblganho = (TextView) findViewById(R.id.lblTotalGanhos);
			lblganho.setText("Total de ganhos: " + ganhoTotal.toString());
			TextView lblgasto = (TextView) findViewById(R.id.lblTotalGastos);
			lblgasto.setText("Total de gastos: " + gastoTotal.toString());
			TextView lblsaldo = (TextView) findViewById(R.id.lblSaldo);
			lblsaldo.setText("Saldo: " + saldo.toString());

			adapter.setGanhos(itemsGanho);
			adapter.setGastos(itemsGasto);
			adapter.notifyDataSetChanged();
		}
	};


	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		// para pegar o item que foi selecionado
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    int index = info.position;
	    
		if (item.getItemId() == GANHO_ID) {
			
			Log.i("teste","acao1 " + GANHO_ID);
			String selectedGridDate = CalendarAdapter.dayString
					.get(index);
			showToast(selectedGridDate);
			Intent i = new Intent(CalendarActivity.this, CadastrarGanhoActivity.class);
			i.putExtra("data", selectedGridDate);
			startActivity(i);
			
		} else if (item.getItemId() == GASTO_ID) {
			
			Log.i("teste","acao2 " + GASTO_ID);
			String selectedGridDate = CalendarAdapter.dayString
					.get(index);
			showToast(selectedGridDate);
			Intent i = new Intent(CalendarActivity.this, CadastrarGastoActivity.class);
			i.putExtra("data", selectedGridDate);
			startActivity(i);
			
		} else {
			return false;
		}
		return true;
	}

}
