package br.com.fiap.financas.activity;

import br.com.fiap.R;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;
import android.app.Activity;

public class GraficosActivity extends Activity {
	
	WebView wvGrafico;
	String strURL;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graficos);
		
		strURL="http://chart.apis.google.com/chart?"+
				"chxl=0:|Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec" +
				"&chxt=x,y" +
				"&chs=300x300" +
				"&cht=r" +
				"&chco=FF0000" +
				"&chd=t:63,64,67,73,77,81,85,86,85,81,74,67,63" +
				"&chls=2,4,0" +
				"&chm=B,FF000080,0,0,0";
		
		strURL="http://chart.apis.google.com/chart?" +
				"cht=p" +
				"&chd=t:45,20,20,15,45,55" +
				"&chs=320x200" +
				"&chdl=IE6|Firefox|IE7|Chrome|Safari|Opera" +
				"&chco=c60000";
		

		/*strURL = "https://chart.googleapis.com/chart?" + "cht=lc&" + 
					"chxt=x,y&" + // imprime os valores dos eixos X, Y
					"chs=300x300&" + // define o tamanho da imagem
					"chd=t:10,45,5,10,13,26&" + // valor de cada coluna do gr�fico
					"chl=Jan|Fev|Mar|Abr|Mai|Jun&" + // r�tulo para cada coluna
					"chdl=Vendas&" + // legenda do gr�fico
					"chxr=1,0,50&" + // define o valor de in�cio e fim do eixo
					"chds=0,50&" + // define o valor de escala dos dados
					"chg=0,5,0,0&" + // desenha linha horizontal na grade
					"chco=3D7930&" + // cor da linha do gr�fico
					"chtt=Vendas+x+1000&" + // cabe�alho do gr�fico
					"chm=B,FF000080,0,0,0";*/
		
		// Abaixo seguem outros exemplo de gr�fico:
		strURL =
		"https://chart.googleapis.com/chart?cht=lc&chxt=x,y&chs=300x300&chd=t:10,45,5,10,13,26&chl=Janeiro|Fevereiro|Marco|Abril|Maio|Junho&chdl=Vendas%20&chxr=1,0,50&chds=0,25&chg=0,5,0,0&chco=3D7930&chtt=Vendas+x+1000&chm=v,FF0000,0,::.10,4";
		//strURL =
		//"http://chart.apis.google.com/chart?cht=bhg&chs=550x400&chd=t:100,50,115,80|10,20,15,30&chxt=x,y&chxl=1:|Janeiro|Fevereiro|Marco|Abril&chxr=0,0,120&chds=0,120&chco=4D89F9&chbh=35,0,15&chg=8.33,0,5,0&chco=0A8C8A,EBB671&chdl=Vendas|Compras";
		//strURL =
		//"https://chart.googleapis.com/chart?cht=lc&chxt=x,y&chs=700x350&chd=t:10,45,5,10|30,35,30,15|10,10.5,30,35&chl=Janeiro|Fevereiro|Marco|Abril&chdl=Vendas|Compras|Outros&chxr=1,0,50&chds=0,50&chg=0,5,0,0&chco=DA3B15,3072F3,000000&chtt=grafico+de+vendas";
		/*strURL =
		"https://chart.googleapis.com/chart?cht=p3&chs=200x90&chd=t:40,45,5,10&chl=Jan|Fev|Mar|Abr";*/
		
		wvGrafico = (WebView) findViewById(R.id.wvGrafico);
		wvGrafico.loadUrl(strURL);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
