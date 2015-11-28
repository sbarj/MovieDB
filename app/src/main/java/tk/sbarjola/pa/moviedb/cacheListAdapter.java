package tk.sbarjola.pa.moviedb;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import tk.sbarjola.pa.moviedb.provider.movie.MovieColumns;

/**
 * Created by 46465442z on 19/11/15.
 */
public class cacheListAdapter extends SimpleCursorAdapter{

    final private String posterUrl = "http://image.tmdb.org/t/p/";
    final private String posterSize = "w342";
    DecimalFormat oneDecimalOnly = new DecimalFormat("#.#");
    Context context;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public cacheListAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {

        super(context, layout, c, from, to, flags);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Creamos el objeto en la posición correspondiente
        Cursor peliculas = getCursor();
        peliculas.moveToPosition(position);

        // Comprobamos si la view ya se ha usado antes, si no, la inflamos (es una buena practica y ahorramos recursos)
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_view_layout, parent, false);
        }

        // Asociamos cada variable a su elemento del layout
        TextView titulo = (TextView) convertView.findViewById(R.id.list_titulo);
        TextView puntuacion = (TextView) convertView.findViewById(R.id.list_puntuacion);
        TextView diaSalida = (TextView) convertView.findViewById(R.id.list_diaSalida);
        ImageView imagenPoster = (ImageView) convertView.findViewById(R.id.list_imagenPoster);
        TextView description = (TextView) convertView.findViewById(R.id.list_descripcion);

        // Incorporamos los objetos al layout

        // puntuacion.setText(oneDecimalOnly.format(pelicula.getPopularity()) + "%");
        titulo.setText(peliculas.getString(peliculas.getColumnIndex(MovieColumns.TITLE)));
        puntuacion.setText(peliculas.getString(peliculas.getColumnIndex(MovieColumns.POPULARITY)) + "%");
        diaSalida.setText(peliculas.getString(peliculas.getColumnIndex(MovieColumns.RELEASEDATE)));
        description.setText(peliculas.getString(peliculas.getColumnIndex(MovieColumns.DESCRIPTION)));
        Picasso.with(context).load(posterUrl + posterSize + peliculas.getString(peliculas.getColumnIndex(MovieColumns.POSTERPATH))).fit().into(imagenPoster);

        return convertView; //Devolvemos la view ya rellena
    }
}