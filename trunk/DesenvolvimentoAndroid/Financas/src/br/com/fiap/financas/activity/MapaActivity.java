package br.com.fiap.financas.activity;

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
import br.com.fiap.R;

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
		setMapLocalizaFiap();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setMapLocalizaFiap();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	private void setMapLocalizaFiap() {
		if (map == null) {
			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			if (map != null) {
				map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
				map.setIndoorEnabled(false);
				Location loc = ((LocationManager) getSystemService(LOCATION_SERVICE))
						.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
				
				loc.setLatitude(-23.57442600000013);
				loc.setLongitude(-46.62321400000036);
				
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(
						new LatLng(loc.getLatitude(), loc.getLongitude()), 18.0f));
				
				MarkerOptions moFiap = new MarkerOptions();
				moFiap.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
				moFiap.position(new LatLng( loc.getLatitude(), loc.getLongitude()));
				
				map.addMarker(moFiap.title("Fiap"));

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
		new AlertDialog.Builder(this).setTitle("Fiap Google Maps").setMessage(text)
				.setNeutralButton("Cancel", null).show();
		return true ;
	}

}
