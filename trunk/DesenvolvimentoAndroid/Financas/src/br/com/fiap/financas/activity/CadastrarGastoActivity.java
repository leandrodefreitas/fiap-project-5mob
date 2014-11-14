package br.com.fiap.financas.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.com.fiap.R;
import br.com.fiap.financas.adapter.CategoriaAdapter;
import br.com.fiap.financas.common.vo.CategoriaVO;
import br.com.fiap.financas.common.vo.GanhoVO;
import br.com.fiap.financas.common.vo.GastoVO;
import br.com.fiap.financas.services.scn.CategoriaSCN;
import br.com.fiap.financas.services.scn.GanhoSCN;
import br.com.fiap.financas.services.scn.GastoSCN;
import br.com.fiap.financas.util.Util;

public class CadastrarGastoActivity extends Activity {

	private ListView listViewCategorias;
	private GastoVO gasto = new GastoVO();
	private GanhoVO ganhoDescontar = new GanhoVO();
	private TextView dataGanho;
	private EditText edtDescricao;
	private EditText edtValor;
	private Button btnSalvar;
	private Button btnVoltar;
	private EditText edtParcela;
	private EditText edtNumParcelas;
	private ArrayList<CategoriaVO> categoriasSelecionadas = new ArrayList<CategoriaVO>();
	private Spinner spnGanhos;
	private List<GanhoVO> listaGanhos;
	private CheckBox checkLocal;
	private TextView latitude;
	private TextView longitude;
	private String localizacao = "";
	private Button btnTirarFoto;
	private ImageView ivFoto;
	private String fotoPath = "";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_gasto);
		
	     if(savedInstanceState != null) {
	    	 	ivFoto = (ImageView) findViewById(R.id.ivFoto);
		        Bitmap bitmap = savedInstanceState.getParcelable("fotoGasto");
		        ivFoto.setImageBitmap(bitmap);
		  }		

		Bundle param = getIntent().getExtras();
		final String data = param.getString("data");
		dataGanho = (TextView) findViewById(R.id.lblDataGasto);
		dataGanho.setText(Util.imprimeDataFormatoBR(data));
		
		edtDescricao = (EditText) findViewById(R.id.fDescricaoGasto);
		edtValor = (EditText) findViewById(R.id.fValorGasto);
		
		edtParcela = (EditText) findViewById(R.id.fParcelaGasto);
		edtNumParcelas = (EditText) findViewById(R.id.fNumParcelasGasto);

		// ListView de categorias
		CategoriaSCN controleCategoria = new CategoriaSCN(getApplicationContext());
		List<CategoriaVO> listaCategorias = controleCategoria.obterTodasCategorias();
		listViewCategorias = (ListView) findViewById(R.id.lvCategoriasGasto);
		listViewCategorias.setAdapter(new CategoriaAdapter(this, listaCategorias));
		// metodo para mostrar todos as categorias no listview
		calculeHeightListView();
		
		if (listaCategorias.isEmpty()) {
			Log.i("Script", "lista categorias vazia..");
			Toast.makeText(getApplicationContext(),
					"Nenhuma categoria cadastrada...", Toast.LENGTH_SHORT).show();
			finish();
		}		
		
		// Populando spinner de ganhos
		GanhoSCN controleGanho = new GanhoSCN(getApplicationContext());
		listaGanhos = controleGanho.obterTodosGanhos();
		
		if (listaGanhos.isEmpty()) {
			Log.i("Script", "lista Ganhos vazia..");
			Toast.makeText(getApplicationContext(),
					"Nenhum ganho cadastrado...", Toast.LENGTH_SHORT).show();
			finish();
		}				

		spnGanhos = (Spinner) findViewById(R.id.spnGanhoSelecao);
		ArrayAdapter<GanhoVO> ganhoAdapter = new ArrayAdapter<GanhoVO>(this, android.R.layout.simple_spinner_dropdown_item, listaGanhos);
		spnGanhos.setAdapter(ganhoAdapter);
		spnGanhos.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				ganhoDescontar = (GanhoVO) parent.getItemAtPosition(position);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});

		
		listViewCategorias.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				CategoriaAdapter catAdapter = ((CategoriaAdapter) parent.getAdapter());
				
				CategoriaVO catVo = (CategoriaVO) catAdapter.getItem(position);

				Log.i("Teste onitemclick list", catVo.getDescricao());
				
				CheckedTextView ctv = (CheckedTextView) ((LinearLayout) view).findViewById(R.id.categoriaItem);
				
			    if(!ctv.isChecked()) {
			        if(!categoriasSelecionadas.contains(catVo)) {
			        	categoriasSelecionadas.add(catVo);			        	
			        }
			    } else {
			         if(categoriasSelecionadas.contains(catVo))
			            categoriasSelecionadas.remove(catVo);
			    }
			    
				if(categoriasSelecionadas.contains(catVo)) {
				    ctv.setChecked(true);
				} else {
				    ctv.setChecked(false);
				}				
				
				
			}
		});
		
		
		// Setando localização atual
		checkLocal = (CheckBox) findViewById(R.id.chkLocal);
		checkLocal.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				latitude = (TextView) findViewById(R.id.txtLatitude);
				longitude = (TextView) findViewById(R.id.txtLongitude);	
				LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); 
				
				if (isChecked) {
					
					Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
					
					if( location != null ){
						Log.i("GPS","location ok");
						latitude.setText( "Latitude: "+location.getLatitude() );
						longitude.setText( "Longitude: "+location.getLongitude() );
						
						localizacao = location.getLatitude() + ", " + location.getLongitude();
						Log.i("GPS","localizacao: "+ localizacao);
						
						
						String lat = localizacao.substring(0,localizacao.indexOf(","));
						
						Log.i("GPS", "latitude: " + lat);
						
						String lng = localizacao.substring(localizacao.indexOf(",")+1,localizacao.length());
						
						Log.i("GPS", "longitude: " + lng);

						
						Log.i("GPS", "latitude em double: " + Double.parseDouble(lat));	
						Log.i("GPS", "longitude em double: " + Double.parseDouble(lng));	
					}
					
					/*locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {
						
						@Override
						public void onStatusChanged(String provider, int status, Bundle extras) {
							Toast.makeText(getApplicationContext(), "Status alterado", Toast.LENGTH_SHORT).show();	
						}
						
						@Override
						public void onProviderEnabled(String provider) {
							Toast.makeText(getApplicationContext(), "GPS Habilitado!", Toast.LENGTH_SHORT).show();
						}
						
						@Override
						public void onProviderDisabled(String provider) {
							
							AlertDialog.Builder alert = new AlertDialog.Builder(CadastrarGastoActivity.this);

							alert.setTitle("Atenção");
							alert.setMessage("O GPS está desabilitado. Deseja abrir as configurações para ativá-lo?");
							
							alert.setPositiveButton("Sim", new DialogInterface.OnClickListener(){
								@Override
								public void onClick(DialogInterface dialog,	int which) {
									Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
									startActivity(intent);
								}
							});
							
							alert.setNegativeButton("Não", new DialogInterface.OnClickListener(){
								@Override
								public void onClick(DialogInterface dialog, int which) {
									//checkLocal.setChecked(false);
									dialog.dismiss();
								}
							});
							alert.show( ) ;							
							
						}
						
						@Override
						public void onLocationChanged(Location location) {

							if( location != null ){
								Log.i("GPS","location ok");
								latitude.setText( "Latitude: "+location.getLatitude() );
								longitude.setText( "Longitude: "+location.getLongitude() );
								
								localizacao = location.getLatitude() + ", " + location.getLongitude();
								Log.i("GPS","localizacao: "+ localizacao);
								
								
								String lat = localizacao.substring(0,localizacao.indexOf(","));
								
								Log.i("GPS", "latitude: " + lat);
								
								String lng = localizacao.substring(localizacao.indexOf(",")+1,localizacao.length());
								
								Log.i("GPS", "longitude: " + lng);

								
								Log.i("GPS", "latitude em double: " + Double.parseDouble(lat));	
								Log.i("GPS", "longitude em double: " + Double.parseDouble(lng));	
								
							}
							
						}
					}, null);*/				
										
				} else {
					latitude.setText("Latitude: ");
					longitude.setText("Longitude: ");	
					localizacao = "";
				}
				
			}
		});
		
		
		btnTirarFoto = (Button) findViewById(R.id.btnTirarFoto);
		btnTirarFoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Log.i("Camera", "Diretorio Extrerno Absolute path: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());

				File picsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
				
				GastoSCN gastoScn = new GastoSCN(getApplicationContext());
				int id = gastoScn.obterProximoId();
				String nomeFoto = "gasto" + id + ".jpg";
				
				File imageFile = new File(picsDir, nomeFoto);
				
				fotoPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + nomeFoto;
						
				Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
				i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
				startActivityForResult(i, 0);					
			}
		});
		
		

		btnSalvar = (Button) findViewById(R.id.btnSalvarGasto);
		btnSalvar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				gasto = new GastoVO();

				if (edtDescricao.getText().toString().length() == 0){
					edtDescricao.setError(getString(R.string.campo_obrigatorio));
					edtDescricao.requestFocus();
				} else	
				if (edtValor.getText().toString().length() == 0){
					edtValor.setError(getString(R.string.campo_obrigatorio));
					edtValor.requestFocus();
				} 
				else {				
					
					gasto.setDescricao(edtDescricao.getText().toString());
					gasto.setValor(Double.valueOf(edtValor.getText().toString()));
					gasto.setData(data);
					
					
					for (int i = 0; i < categoriasSelecionadas.size(); i++) {
						Log.i("Array categorias: ", String.valueOf(categoriasSelecionadas.get(i).getDescricao()));
					}
					gasto.setCategorias(categoriasSelecionadas);
					
					
					gasto.setGanhoDescontar(ganhoDescontar);
					
					if (!(edtParcela.getText().toString().length() == 0)){
						gasto.setParcela(Integer.valueOf(edtParcela.getText().toString()));						
					} else {
						gasto.setParcela(0);
					}
					
					if (!(edtNumParcelas.getText().toString().length() == 0)){
						gasto.setNumParcelas(Integer.valueOf(edtNumParcelas.getText().toString()));					
					} else {
						gasto.setNumParcelas(0);
					}
					
					gasto.setLocal(localizacao);
					
					gasto.setFoto(fotoPath);
					
					if (gasto.getNumParcelas() >= 5) {
						createNotificationConfirmParcelas();
					} else {
						createNotificationConfirm();
					}
			    	finish();
				}
			}
		});

		btnVoltar = (Button) findViewById(R.id.btnSairGasto);
		btnVoltar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//PARA SETAR IMAGEM COM O PATH
		if(resultCode == Activity.RESULT_OK) {
						
			Bitmap bitmap = BitmapFactory.decodeFile(fotoPath);
			ivFoto = (ImageView) findViewById(R.id.ivFoto);
			ivFoto.setImageBitmap(bitmap);			
		}
	}
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);		
		ivFoto = (ImageView) findViewById(R.id.ivFoto);
	    BitmapDrawable drawable = (BitmapDrawable) ivFoto.getDrawable();
	    if (drawable != null) {
		    Bitmap bitmap = drawable.getBitmap();
		    outState.putParcelable("fotoGasto", bitmap);	    	
	    }
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
    private void calculeHeightListView() {  
        int totalHeight = 0;  
  
        ListAdapter adapter = listViewCategorias.getAdapter();  
        int lenght = adapter.getCount();  
  
        for (int i = 0; i < lenght; i++) {  
            View listItem = adapter.getView(i, null, listViewCategorias);  
            listItem.measure(0, 0);  
            totalHeight += listItem.getMeasuredHeight();  
        }  
  
        ViewGroup.LayoutParams params = listViewCategorias.getLayoutParams();  
        params.height = totalHeight  
                + (listViewCategorias.getDividerHeight() * (adapter.getCount() - 1));  
        listViewCategorias.setLayoutParams(params);  
        listViewCategorias.requestLayout();  
    }
    
    public void createNotificationConfirm(){
    	
        Intent intent = new Intent(this, NotificationConfirmGastoActivity.class);
        intent.putExtra("vo", gasto);
        PendingIntent pIntent = PendingIntent.getActivity(this,0,intent,Intent.FLAG_ACTIVITY_NEW_TASK);

        Notification notifica = new Notification.Builder(this)
                .setContentTitle("Novo gasto")
                .setContentText("Confirme ou cancele esse novo registro.").setSmallIcon(R.drawable.rf_icon)
                .setContentIntent(pIntent).build();
                //.addAction(R.drawable.appicon, "btn1", pIntent).build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notifica.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, notifica);
    }
    
    public void createNotificationConfirmParcelas(){
    	
        Intent intent = new Intent(this, NotificationConfirmParcelaActivity.class);
        intent.putExtra("vo", gasto);
        PendingIntent pIntent = PendingIntent.getActivity(this,0,intent,Intent.FLAG_ACTIVITY_NEW_TASK);

        Notification notifica = new Notification.Builder(this)
                .setContentTitle("Gasto parcelado")
                .setContentText("Confirme ou cancele o gasto parcelado.").setSmallIcon(R.drawable.rf_icon)
                .setContentIntent(pIntent).build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notifica.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, notifica);
    }
    
}
