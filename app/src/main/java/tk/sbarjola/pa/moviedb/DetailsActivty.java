package tk.sbarjola.pa.moviedb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class DetailsActivty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_activty);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Recibimos la pelicula de la que extraemos el titulo y la fecha de salida donde recortamos el año

        Result pelicula = (Result) getIntent().getExtras().get("pelicula");
        toolbar.setTitle(pelicula.getTitle() + " - (" + pelicula.getReleaseDate().substring(0, 4) + ")" );
        setSupportActionBar(toolbar);
    }
}
