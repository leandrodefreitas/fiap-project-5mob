package br.com.fiap.minichef.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.minichef.adapter.CategoriaAdapter;
import br.com.fiap.minichef.common.vo.CategoriaVO;
import br.com.fiap.minichef.common.vo.ReceitaVO;
import br.com.fiap.minichef.services.scn.CategoriaSCN;
import br.com.fiap.minichef.services.scn.ReceitaSCN;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CadastrarReceitaActivity extends Activity {

	private ListView listViewCategorias;
	private ReceitaVO receita = new ReceitaVO();
	private EditText edtDescricao;
	private Button btnEnviar;
	private Button btnVoltar;
	private ArrayList<CategoriaVO> categoriasSelecionadas = new ArrayList<CategoriaVO>();
	private Button btnTirarFoto;
	private ImageView ivFoto;
	private String fotoPath = "";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_receita);
		
	    if(savedInstanceState != null) { 
	    	ivFoto = (ImageView) findViewById(R.id.ivFoto);
		    Bitmap bitmap = savedInstanceState.getParcelable("fotoReceita");
		    ivFoto.setImageBitmap(bitmap);
		}		
		
		edtDescricao = (EditText) findViewById(R.id.etDescricao);

		// ListView de categorias
		CategoriaSCN controleCategoria = new CategoriaSCN(getApplicationContext());
		List<CategoriaVO> listaCategorias = controleCategoria.obterTodasCategorias();
		listViewCategorias = (ListView) findViewById(R.id.lvCategorias);
		listViewCategorias.setAdapter(new CategoriaAdapter(this, listaCategorias));
		// metodo para mostrar todos as categorias no listview
		calculeHeightListView();
		
		if (listaCategorias.isEmpty()) {
			Log.i("Script", "lista categorias vazia..");
			Toast.makeText(getApplicationContext(),
					"Nenhuma categoria cadastrada...", Toast.LENGTH_SHORT).show();
			finish();
		}		

		
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
		
		btnTirarFoto = (Button) findViewById(R.id.btnTirarFoto);
		btnTirarFoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Log.i("Camera", "Diretorio Extrerno Absolute path: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());

				File picsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
				
				ReceitaSCN receitaScn = new ReceitaSCN(getApplicationContext());
				int id = receitaScn.obterProximoId();
				String nomeFoto = "receita" + id + ".jpg";
				
				File imageFile = new File(picsDir, nomeFoto);
				
				fotoPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + nomeFoto;
						
				Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
				i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
				startActivityForResult(i, 0);					
			}
		});
		
		

		btnEnviar = (Button) findViewById(R.id.btnEnviarReceita);
		btnEnviar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				receita = new ReceitaVO();

				if (edtDescricao.getText().toString().length() == 0){
					edtDescricao.setError(getString(R.string.campo_obrigatorio));
					edtDescricao.requestFocus();
				} 
				else {				
					
					receita.setDescricao(edtDescricao.getText().toString());
					
					
					for (int i = 0; i < categoriasSelecionadas.size(); i++) {
						Log.i("Array categorias: ", String.valueOf(categoriasSelecionadas.get(i).getDescricao()));
					}
					receita.setCategorias(categoriasSelecionadas);
					
					
					receita.setFoto(fotoPath);
					
					Toast.makeText(getApplicationContext(),
							"Sua receita foi enviada com sucesso. Aguarde um dia para ela ser aprovada.", Toast.LENGTH_SHORT).show();
			    	finish();
				}
			}
		});

		btnVoltar = (Button) findViewById(R.id.btnSairReceita);
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
		    outState.putParcelable("fotoReceita", bitmap);	    	
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
    
}
