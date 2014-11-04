package br.com.fiap.financas.activity;

import java.io.File;

import br.com.fiap.R;

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
import android.view.View;
import android.widget.ImageView;

public class FotoActivity extends Activity {
	
	private ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.foto);
		
		
	     if(savedInstanceState != null) {
	    	 	iv = (ImageView) findViewById(R.id.imageView1);
		        Bitmap bitmap = savedInstanceState.getParcelable("foto");
		        iv.setImageBitmap(bitmap);
		  }		
	}

	public void tirarFoto(View view) {
		//Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		//startActivityForResult(intent, 0);
		

		String pathImg = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + "foto.jpg";
		
		Bitmap bitmap = BitmapFactory.decodeFile(pathImg);
		ImageView iv = (ImageView) findViewById(R.id.imageView1);
		iv.setImageBitmap(bitmap);
		
		/*
		Matrix matrix = new Matrix();
		iv.setScaleType(ScaleType.MATRIX);   //required
		matrix.postRotate( 90f, iv.getDrawable().getBounds().width(), iv.getDrawable().getBounds().height());	
		iv.setImageMatrix(matrix);		
		
		
		*/
	}

	public void tirarFotoEGravar(View view) {
		
		Log.i("Camera", "Diretorio Extrerno: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString());
		Log.i("Camera", "Diretorio Extrerno Absolute path: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());
		Log.i("Camera", "Diretorio Extrerno path: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath());
		Log.i("Camera", "Diretório pictures " + Environment.DIRECTORY_PICTURES);
		Log.i("Camera", "Diretório data " + Environment.getDataDirectory().toString());		
		
		
		File picsDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File imageFile = new File(picsDir, "foto.jpg");

		Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
		i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
		startActivityForResult(i, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null) {
			Bundle bundle = data.getExtras();
			if (bundle != null) {
				Bitmap img = (Bitmap) bundle.get("data");

				iv = (ImageView) findViewById(R.id.imageView1);
				iv.setImageBitmap(img);
			}
		}
		
		if(resultCode == Activity.RESULT_OK) {
			
			//Setando a imagem no imageView
			String pathImg = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + "foto.jpg";
			
			Bitmap bitmap = BitmapFactory.decodeFile(pathImg);
			ImageView iv = (ImageView) findViewById(R.id.imageView1);
			iv.setImageBitmap(bitmap);			
		}
	}
		
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		
		iv = (ImageView) findViewById(R.id.imageView1);
	    BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
	    Bitmap bitmap = drawable.getBitmap();
	    outState.putParcelable("foto", bitmap);
		super.onSaveInstanceState(outState);
	}
}
