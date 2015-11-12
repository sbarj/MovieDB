package tk.sbarjola.pa.moviedb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);   // necesario para referenciar y leer la configuración del programa

        // Según como esté configurado el programa lee de las preferncias y muestra un titulo en el Toolbar u otro
        /* IMPORTANTE: SOLO funcionara mientras la gestion de categorias pase por las preferencias.*/

        if(settings.getString("ListaPeliculas", "0").equals("0")){
            toolbar.setTitle("MovieDB - Populares");
        }
        else if (settings.getString("ListaPeliculas", "1").equals("1")){
            toolbar.setTitle("MovieDB - Top Rated");
        }
        else{
            toolbar.setTitle("MovieDB");
        }

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);    //intent para ir a la activity I
            startActivity(i);   //hacemos el intent
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
