package tk.sbarjola.pa.moviedb;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivtyFragment extends Fragment {

    public DetailsActivtyFragment() {
    }

    public Result pelicula;
    final private String posterUrl = "http://image.tmdb.org/t/p/";
    final private String posterSize = "w185";
    DecimalFormat oneDecimalOnly = new DecimalFormat("#.#");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentoDetails = inflater.inflate(R.layout.fragment_details_activty, container, false);    //Definimos el fragment

        // Creamos nuestro intent y recogemos los datos que enviamos desde fragment
        Intent intent = getActivity().getIntent();
        Bundle bd = intent.getExtras();

        if (bd != null) {   // Su hemos recibido algo lo asigna a pelocula
            pelicula = (Result) intent.getSerializableExtra("pelicula");
        }

        // Asociamos cada variable a su elemento del layout
        TextView titulo = (TextView) fragmentoDetails.findViewById(R.id.list_titulo);
        TextView puntuacion = (TextView) fragmentoDetails.findViewById(R.id.list_puntuacion);
        TextView diaSalida = (TextView) fragmentoDetails.findViewById(R.id.list_diaSalida);
        ImageView imagenPoster = (ImageView) fragmentoDetails.findViewById(R.id.peliculaPosterDetails);
        TextView description = (TextView) fragmentoDetails.findViewById(R.id.descripcionDetails);
        TextView idioma = (TextView) fragmentoDetails.findViewById(R.id.idiomaPeliculaDetails);
        TextView adultos = (TextView) fragmentoDetails.findViewById(R.id.adultDetails);
        TextView tituloOriginal = (TextView) fragmentoDetails.findViewById(R.id.tituloOriginalDetails);

        // toolbarDetails.setTitle(pelicula.getTitle());

        // Incorporamos los objetos al layout
        titulo.setText(pelicula.getTitle());
        tituloOriginal.setText("(" + pelicula.getOriginalTitle() + ")");
        puntuacion.setText("Puntuación: " + oneDecimalOnly.format(pelicula.getPopularity()) + "%");
        idioma.setText("Idioma : " + pelicula.getOriginalLanguage());
        diaSalida.setText("Estreno : " + pelicula.getReleaseDate());
        description.setText("Descripción: \n \n" + pelicula.getOverview());
        Picasso.with(getContext()).load(posterUrl + posterSize + pelicula.getPosterPath()).fit().into(imagenPoster);

        // Para justar si es paraadultos o no.

        if(pelicula.getAdult() == true){
            adultos.setText("Todos los publicos: No.");
        }
        else if (pelicula.getAdult() == false){
            adultos.setText("Todos los publicos: Sí.");
        }

        return fragmentoDetails;
    }
}
