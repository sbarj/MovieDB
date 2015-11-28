package tk.sbarjola.pa.moviedb;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import tk.sbarjola.pa.moviedb.provider.movie.MovieColumns;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivtyFragment extends Fragment {

    public Result pelicula;
    final private String posterUrl = "http://image.tmdb.org/t/p/";
    final private String posterSize = "w342";

    private long itemId = -1;

    TextView titulo;
    TextView puntuacion;
    TextView diaSalida;
    ImageView imagenPoster;
    TextView description;
    TextView idioma;
    TextView tituloOriginal;

    public DetailsActivtyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentoDetails = inflater.inflate(R.layout.fragment_details_activty, container, false);    //Definimos el fragment

        // Creamos nuestro intent y recogemos los datos que enviamos desde fragment

        // Asociamos cada variable a su elemento del layout
        titulo = (TextView) fragmentoDetails.findViewById(R.id.list_titulo);
        puntuacion = (TextView) fragmentoDetails.findViewById(R.id.list_puntuacion);
        diaSalida = (TextView) fragmentoDetails.findViewById(R.id.list_diaSalida);
        imagenPoster = (ImageView) fragmentoDetails.findViewById(R.id.peliculaPosterDetails);
        description = (TextView) fragmentoDetails.findViewById(R.id.descripcionDetails);
        idioma = (TextView) fragmentoDetails.findViewById(R.id.idiomaPeliculaDetails);
        tituloOriginal = (TextView) fragmentoDetails.findViewById(R.id.tituloOriginalDetails);

        // Load movie data
        itemId = getActivity().getIntent().getLongExtra("cursor_id", -1);
        if(itemId != -1){
            showDetails();
        }

        /*  POR IMPLEMENTAR (REHACER BDD)

        idioma.setText("Idioma : " + pelicula.getOriginalLanguage());
        tituloOriginal.setText("(" + pelicula.getOriginalTitle() + ")");

        // Para mostrar si es para adultos o no.

        if(pelicula.getAdult() == true){
            adultos.setText("Todos los publicos: No.");
        }
        else if (pelicula.getAdult() == false){
            adultos.setText("Todos los publicos: Sí.");
        }   */

        return fragmentoDetails;
    }

    public void showDetails(){

        Cursor cursor = getContext().getContentResolver().query(
                MovieColumns.CONTENT_URI,
                null,
                MovieColumns._ID + " = ?",
                new String[]{String.valueOf(itemId)},
                null
        );

        if (cursor != null){    // Incorporamos los objetos al layout si existe algo en el cursor
            cursor.moveToNext();
            
            titulo.setText(cursor.getString(cursor.getColumnIndex(MovieColumns.TITLE)));
            tituloOriginal.setText(cursor.getString(cursor.getColumnIndex(MovieColumns.ORIGINALTITLE)));
            puntuacion.setText("Puntuación: " + cursor.getString(cursor.getColumnIndex(MovieColumns.POPULARITY)).substring(0, 5) + "%");
            diaSalida.setText("Estreno: " + cursor.getString(cursor.getColumnIndex(MovieColumns.RELEASEDATE)).toString());
            description.setText("Descripción: \n \n" + cursor.getString(cursor.getColumnIndex(MovieColumns.DESCRIPTION)).toString());
            idioma.setText("Idioma: " + cursor.getString(cursor.getColumnIndex(MovieColumns.LANGUAGE)));
            Picasso.with(getContext()).load(posterUrl + posterSize + cursor.getString(cursor.getColumnIndex(MovieColumns.POSTERPATH)).toString()).fit().into(imagenPoster);
        }
    }
}
