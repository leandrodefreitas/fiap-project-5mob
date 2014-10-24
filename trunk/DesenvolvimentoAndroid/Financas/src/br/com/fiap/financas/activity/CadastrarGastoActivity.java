package br.com.fiap.financas.activity;

import br.com.fiap.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CadastrarGastoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_gasto);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
