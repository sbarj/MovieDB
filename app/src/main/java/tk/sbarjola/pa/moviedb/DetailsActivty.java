package tk.sbarjola.pa.moviedb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class DetailsActivty extends AppCompatActivity {

    TextView title;
    TextView diaSalida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_activty);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        diaSalida = (TextView) findViewById(R.id.list_diaSalida);
        title = (TextView) findViewById(R.id.list_titulo);

        String año = diaSalida.getText().toString().substring(9, 13); // Cortamos el año
        toolbar.setTitle(title.getText() + " - " + año);    // Y mostramos en la toolbar el nombre de la pelicula y el año

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
