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
import android.annotation.SuppressLint;
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
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month).toString().toUpperCase());
		
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
	
	@SuppressLint("SimpleDateFormat")
	protected void montarGraficoBarra(GregorianCalendar dataSel) {
		tipoGrafico = 1;
		GregorianCalendar mesAuxP1 = setMesPassado(dataSel);
		GregorianCalendar mesAuxP2 = setMesPassado(dataSel);
		GregorianCalendar mesAuxP3 = setMesPassado(dataSel);
		GregorianCalendar mesAuxF1 = setMesProximo(dataSel);
		GregorianCalendar mesAuxF2 = setMesProximo(dataSel);
		GregorianCalendar mesAuxF3 = setMesProximo(dataSel);
		
		mesAuxP1 = setMesPassado(mesAuxP1);
		mesAuxP2 = setMesPassado(mesAuxP1);
		mesAuxP3 = setMesPassado(mesAuxP2);
		mesAuxF1 = setMesProximo(mesAuxF1);
		mesAuxF2 = setMesProximo(mesAuxF1);
		mesAuxF3 = setMesProximo(mesAuxF2);
		
		Locale.setDefault(new Locale("pt", "BR"));
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		String mesAtual = df.format(dataSel.getTime());
		String mesPassado1 = df.format(mesAuxP1.getTime());
		String mesPassado2 = df.format(mesAuxP2.getTime());
		String mesPassado3 = df.format(mesAuxP3.getTime());
		String mesFuturo1 = df.format(mesAuxF1.getTime());
		String mesFuturo2 = df.format(mesAuxF2.getTime());
		String mesFuturo3 = df.format(mesAuxF3.getTime());
		
		GanhoSCN ganhosA = new GanhoSCN(getApplicationContext());
		Double ganhosTA = ganhosA.obterTotalGanhosPorMesAno(mesAtual);
		Double ganhosTP1 = ganhosA.obterTotalGanhosPorMesAno(mesPassado1);
		Double ganhosTP2 = ganhosA.obterTotalGanhosPorMesAno(mesPassado2);
		Double ganhosTP3 = ganhosA.obterTotalGanhosPorMesAno(mesPassado3);
		Double ganhosTF1 = ganhosA.obterTotalGanhosPorMesAno(mesFuturo1);
		Double ganhosTF2 = ganhosA.obterTotalGanhosPorMesAno(mesFuturo2);
		Double ganhosTF3 = ganhosA.obterTotalGanhosPorMesAno(mesFuturo3);
		
		GastoSCN gastosA = new GastoSCN(getApplicationContext());
		Double gastosTA = gastosA.obterTotalGastosPorMesAno(mesAtual);
		Double gastosTP1 = gastosA.obterTotalGastosPorMesAno(mesPassado1);
		Double gastosTP2 = gastosA.obterTotalGastosPorMesAno(mesPassado2);
		Double gastosTP3 = gastosA.obterTotalGastosPorMesAno(mesPassado3);
		Double gastosTF1 = gastosA.obterTotalGastosPorMesAno(mesFuturo1);
		Double gastosTF2 = gastosA.obterTotalGastosPorMesAno(mesFuturo2);
		Double gastosTF3 = gastosA.obterTotalGastosPorMesAno(mesFuturo3);
		
		DateFormat dtitulo = new SimpleDateFormat("MMMM yyyy");
		
		String tituloMeses = "|" + dtitulo.format(mesAuxP3.getTime()) + "|" + dtitulo.format(mesAuxP2.getTime()) + "|" + 
				dtitulo.format(mesAuxP1.getTime()) + "|" + dtitulo.format(dataSel.getTime()) + "|" + dtitulo.format(mesAuxF1.getTime()) + "|" + 
				dtitulo.format(mesAuxF2.getTime()) + "|" + dtitulo.format(mesAuxF3.getTime());
		String valorMeses = ganhosTP3.toString() + "," + ganhosTP2.toString() + "," + ganhosTP1.toString() + "," + 
				ganhosTA.toString() + "," + ganhosTF1.toString() + "," + ganhosTF2.toString() + "," + ganhosTF3.toString() + "|" + 
				gastosTP3.toString() + "," + gastosTP2.toString() + "," + gastosTP1.toString() + "," +
				gastosTA.toString() + "," + gastosTF1.toString() + "," + gastosTF2.toString() + "," + gastosTF3.toString();
		
		strURL="http://chart.apis.google.com/chart?" +
				"cht=bhg" +
				"&chs=350x400" +
				"&chd=t:"+ valorMeses +
				"&chxt=x,y" +
				"&chxl=1:" + tituloMeses +
				"&chxr=0,0,5000" +
				"&chds=0,5000" +
				"&chco=4D89F9" +
				"&chbh=25,0,5" +
				"&chg=8.33,0,5,0" +
				"&chco=0AFF8A,FF2351" +
				"&chdl=Ganhos|Gastos";

		wvGrafico = (WebView) findViewById(R.id.wvGrafico);
		wvGrafico.loadUrl(strURL);
	}
	
	protected void setNextMonth() {
		if (month.get(GregorianCalendar.MONTH) == month.getActualMaximum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) + 1), month.getActualMinimum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH, month.get(GregorianCalendar.MONTH) + 1);
		}
	}

	protected void setPreviousMonth() {
		if (month.get(GregorianCalendar.MONTH) == month.getActualMinimum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) - 1), month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH, month.get(GregorianCalendar.MONTH) - 1);
		}
	}
	
	protected GregorianCalendar setMesProximo(GregorianCalendar data) {
		GregorianCalendar mes = data;
		if (mes.get(GregorianCalendar.MONTH) == mes.getActualMaximum(GregorianCalendar.MONTH)) {
			mes.set((mes.get(GregorianCalendar.YEAR) + 1), mes.getActualMinimum(GregorianCalendar.MONTH), 1);
		} else {
			mes.set(GregorianCalendar.MONTH, mes.get(GregorianCalendar.MONTH) + 1);
		}
		return mes;
	}
	
	protected GregorianCalendar setMesPassado(GregorianCalendar data) {
		GregorianCalendar mes = data;
		if (mes.get(GregorianCalendar.MONTH) == mes.getActualMinimum(GregorianCalendar.MONTH)) {
			mes.set((mes.get(GregorianCalendar.YEAR) - 1),mes.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			mes.set(GregorianCalendar.MONTH, mes.get(GregorianCalendar.MONTH) - 1);
		}
		return mes;
	}
	
	public void refreshGrafico() {
		TextView title = (TextView) findViewById(R.id.title);
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month).toString().toUpperCase());
	}

	
}
