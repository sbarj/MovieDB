package tk.sbarjola.pa.moviedb;

/**
 * Created by 46465442z on 09/11/15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

public class MovieListAdapter extends ArrayAdapter<Result> implements Serializable{

    final private String posterUrl = "http://image.tmdb.org/t/p/";
    final private String posterSize = "w185";
    DecimalFormat oneDecimalOnly = new DecimalFormat("#.#");

    public MovieListAdapter(Context context, int resource, List<Result> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

    // Creamos el objeto en la posición correspondiente
        Result pelicula = getItem(position);

    // Comprobamos si la view ya se ha usado antes, si no, la inflamos (es una buena practica y ahorramos recursos)
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_view_layout, parent, false);
        }

    // Asociamos cada variable a su elemento del layout
        TextView titulo = (TextView) convertView.findViewById(R.id.list_titulo);
        TextView puntuacion = (TextView) convertView.findViewById(R.id.list_puntuacion);
        TextView diaSalida = (TextView) convertView.findViewById(R.id.list_diaSalida);
        ImageView imagenPoster = (ImageView) convertView.findViewById(R.id.list_imagenPoster);
        TextView description = (TextView) convertView.findViewById(R.id.list_descripcion);

    // Incorporamos los objetos al layout
        titulo.setText(pelicula.getTitle());
        puntuacion.setText(oneDecimalOnly.format(pelicula.getPopularity()) + "%");
        diaSalida.setText(pelicula.getReleaseDate());

        /*  //Fragmento para dejar el listView más ordenado y no descuadrarlo
        if(titulo.getLineCount() > 1){
            description.setMaxLines(7);
            description.setEllipsize(TextUtils.TruncateAt.END);
        }*/

        description.setText(pelicula.getOverview());
        Picasso.with(getContext()).load(posterUrl + posterSize + pelicula.getPosterPath()).fit().into(imagenPoster);


        return convertView; //Devolvemos la view ya rellena
    }
}