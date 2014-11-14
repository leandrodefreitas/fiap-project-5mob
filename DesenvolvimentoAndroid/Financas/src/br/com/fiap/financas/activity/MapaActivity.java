package br.com.fiap.financas.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import br.com.fiap.R;
import br.com.fiap.financas.common.vo.GastoVO;
import br.com.fiap.financas.services.scn.GastoSCN;
import br.com.fiap.financas.util.Util;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaActivity extends Activity {
	
	private GoogleMap map;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa_locais);
		setMapLocaliza();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setMapLocaliza();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	private void setMapLocaliza() {
		if (map == null) {
			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			if (map != null) {
				map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				map.setIndoorEnabled(false);
				
				Location localvc = ((LocationManager) getSystemService(LOCATION_SERVICE))
						.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
				
				if (localvc == null) {
					map.moveCamera(CameraUpdateFactory.newLatLngZoom(
							new LatLng(-23.57442600000013, -46.62321400000036), 12.0f));
				} else {
					map.moveCamera(CameraUpdateFactory.newLatLngZoom(
							new LatLng(localvc.getLatitude(), localvc.getLongitude()), 12.0f));
					
					MarkerOptions moVC = new MarkerOptions();
					moVC.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
					moVC.position(new LatLng( localvc.getLatitude(), localvc.getLongitude()));
					moVC.title("Você está aqui!");
					map.addMarker(moVC);
				}
				
				GastoSCN gastosA = new GastoSCN(getApplicationContext());
				List<GastoVO> gastos = gastosA.obterTodosGastos();
				
				MarkerOptions moGasto = new MarkerOptions();
				moGasto.icon(BitmapDescriptorFactory.fromResource(R.drawable.localmapa));
				
				for (int i = 0; i < gastos.size(); i++) {
					if(gastos.get(i).getLatitude() != null && gastos.get(i).getLongitude() != null) {
						moGasto.position(new LatLng(gastos.get(i).getLatitude(), gastos.get(i).getLongitude()));
						String conteudo = gastos.get(i).getDescricao() + "\n - " + Util.imprimeDataFormatoBR(gastos.get(i).getDataFormatted()) + 
								" - " + Util.formataMoedaBRL(gastos.get(i).getValor());
						moGasto.title(conteudo);
						map.addMarker(moGasto);
					}
				}
				
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String text = GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(this);
		new AlertDialog.Builder(this).setTitle("Google Maps").setMessage(text)
				.setNeutralButton("Cancel", null).show();
		return true ;
	}
	
	public void voltarDashboard(View v) {
		finish();
	}

}
