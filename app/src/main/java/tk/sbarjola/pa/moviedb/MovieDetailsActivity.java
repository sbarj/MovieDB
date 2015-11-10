package tk.sbarjola.pa.moviedb;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class MovieDetailsActivity extends AppCompatActivity {

    public Result pelicula;
    final private String posterUrl = "http://image.tmdb.org/t/p/";
    final private String posterSize = "w185";
    DecimalFormat oneDecimalOnly = new DecimalFormat("#.#");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Creamos nuestro intent y recogemos los datos que enviamos desde fragment
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        if (bd != null) {   // Su hemos recibido algo lo asigna a pelocula
            pelicula = (Result) intent.getSerializableExtra("pelicula");
        }

         // Asociamos cada variable a su elemento del layout
        TextView titulo = (TextView) findViewById(R.id.tituloDetails);
        TextView puntuacion = (TextView) findViewById(R.id.puntuacionDetails);
        TextView diaSalida = (TextView) findViewById(R.id.diaSalidaDetails);
        ImageView imagenPoster = (ImageView) findViewById(R.id.peliculaPosterDetails);
        TextView description = (TextView) findViewById(R.id.descripcionDetails);
        TextView idioma = (TextView) findViewById(R.id.idiomaPeliculaDetails);
        TextView adultos = (TextView) findViewById(R.id.adultDetails);
        TextView tituloOriginal = (TextView) findViewById(R.id.tituloOriginalDetails);

        // Incorporamos los objetos al layout
        titulo.setText(" " + pelicula.getTitle());
        tituloOriginal.setText("  (" + pelicula.getOriginalTitle() + ")");
        puntuacion.setText("  Puntuación: " + oneDecimalOnly.format(pelicula.getPopularity()) + "%");
        idioma.setText("  Idioma : " + pelicula.getOriginalLanguage());
        diaSalida.setText("  Estreno : " + pelicula.getReleaseDate());
        description.setText("Descripción: \n \n" + pelicula.getOverview());
        Picasso.with(this).load(posterUrl + posterSize + pelicula.getPosterPath()).fit().into(imagenPoster);

        // Para justar si es paraadultos o no.

        if(pelicula.getAdult() == true){
            adultos.setText("  Todos los publicos: No.");
        }
        else if (pelicula.getAdult() == false){
            adultos.setText("  Todos los publicos: Sí.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_details, menu);

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
