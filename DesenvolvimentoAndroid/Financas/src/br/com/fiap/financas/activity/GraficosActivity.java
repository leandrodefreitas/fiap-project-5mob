package br.com.fiap.financas.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import br.com.fiap.R;
import br.com.fiap.financas.common.vo.GanhoVO;
import br.com.fiap.financas.common.vo.GastoVO;
import br.com.fiap.financas.services.scn.GanhoSCN;
import br.com.fiap.financas.services.scn.GastoSCN;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Activity;

public class GraficosActivity extends Activity {
	public GregorianCalendar month;
	Integer tipoGrafico; // 1 = barra; 2 = pizza;
	WebView wvGrafico;
	String strURL;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graficos);
		
		month = (GregorianCalendar) GregorianCalendar.getInstance();
		TextView title = (TextView) findViewById(R.id.title);
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month)
				.toString().toUpperCase());
		
		RelativeLayout previous = (RelativeLayout) findViewById(R.id.previous);

		previous.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setPreviousMonth();
				refreshGrafico();
				if (tipoGrafico == 1) {
					montarGraficoBarra(month);
				} else if (tipoGrafico == 2) {
					montarGraficoPizza(month);
				}
			}
		});

		RelativeLayout next = (RelativeLayout) findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setNextMonth();
				refreshGrafico();
				if (tipoGrafico == 1) {
					montarGraficoBarra(month);
				} else if (tipoGrafico == 2) {
					montarGraficoPizza(month);
				}
			}
		});
		
		Button btnBarra = (Button) findViewById(R.id.btnGraficoBarra);
		btnBarra.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				montarGraficoBarra(month);
			}
		});
		
		Button btnPizza = (Button) findViewById(R.id.btnGraficoPizza);
		btnPizza.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				montarGraficoPizza(month);
			}
		});
		
		montarGraficoBarra(month);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	protected void montarGraficoPizza(GregorianCalendar dataSel) {
		tipoGrafico = 2;
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		String itemvalue = df.format(month.getTime());
		month.add(GregorianCalendar.DATE, 1);
		GastoSCN gastosA = new GastoSCN(getApplicationContext());
		List<GastoVO> gastos = gastosA.obterGastosPorMesEAno(itemvalue);
		
		ArrayList<String> categoria = new ArrayList<String>();
		ArrayList<Double> gastoValor = new ArrayList<Double>();
		for (int i = 0; i < gastos.size(); i++) {
			if (categoria.contains(gastos.get(i).getCategoriasString())) {
				int aux = categoria.indexOf(gastos.get(i).getCategoriasString());
				Double auxSoma = gastoValor.get(aux) + gastos.get(i).getValor();
				gastoValor.set(aux, auxSoma);
			} else {
				categoria.add(gastos.get(i).getCategoriasString());
				gastoValor.add(gastos.get(i).getValor());
			}
		}
		
		String stringCat = categoria.toString().replace("[", "").replace("]", "").replace(", ", "|");
		String stringVal = gastoValor.toString().replace("[", "").replace("]", "").replace(", ", ",");
		strURL="http://chart.apis.google.com/chart?" +
				"cht=p" +
				"&chd=t:" + stringVal +
				"&chs=320x200" +
				"&chdl=" + stringCat +
				"&chco=c60000";
		// grafico pizza 3d
		strURL =
		"https://chart.googleapis.com/chart?cht=p3&chs=320x100" +
		"&chd=t:" + stringVal +
		"&chl=" + stringCat;
		
		wvGrafico = (WebView) findViewById(R.id.wvGrafico);
		wvGrafico.loadUrl(strURL);
	}
	
	protected void montarGraficoBarra(GregorianCalendar dataSel) {
		tipoGrafico = 1;
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		String itemvalue = df.format(month.getTime());
		month.add(GregorianCalendar.DATE, 1);
		
		GanhoSCN ganhosA = new GanhoSCN(getApplicationContext());
		List<GanhoVO> ganhosMAtual = ganhosA.obterGanhosPorMesAno(itemvalue);
		List<GanhoVO> ganhosMPassado = ganhosA.obterGanhosPorMesAno(itemvalue);
		List<GanhoVO> ganhosMFuturo = ganhosA.obterGanhosPorMesAno(itemvalue);
		
		GastoSCN gastosA = new GastoSCN(getApplicationContext());
		List<GastoVO> gastosMAtual = gastosA.obterGastosPorMesEAno(itemvalue);
		List<GastoVO> gastosMPassado = gastosA.obterGastosPorMesEAno(itemvalue);
		List<GastoVO> gastosMFuturo = gastosA.obterGastosPorMesEAno(itemvalue);
		
		strURL="http://chart.apis.google.com/chart?" +
				"cht=bhg" +
				"&chs=350x400" +
				"&chd=t:100,50,115,80|10,20,15,30" +
				"&chxt=x,y" +
				"&chxl=1:|Janeiro|Fevereiro|Marco|Abril" +
				"&chxr=0,0,120" +
				"&chds=0,120" +
				"&chco=4D89F9" +
				"&chbh=35,0,15" +
				"&chg=8.33,0,5,0" +
				"&chco=0AFF8A,FF2351" +
				"&chdl=Ganhos|Gastos";

		wvGrafico = (WebView) findViewById(R.id.wvGrafico);
		wvGrafico.loadUrl(strURL);
	}
	
	protected void setNextMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMaximum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) + 1),
					month.getActualMinimum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH, month.get(GregorianCalendar.MONTH) + 1);
		}
	}

	protected void setPreviousMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH, month.get(GregorianCalendar.MONTH) - 1);
		}
	}
	
	public void refreshGrafico() {
		TextView title = (TextView) findViewById(R.id.title);
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month)
				.toString().toUpperCase());
	}

	
}
