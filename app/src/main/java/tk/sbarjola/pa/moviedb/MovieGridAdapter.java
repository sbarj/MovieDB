package tk.sbarjola.pa.moviedb;

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

/**
 * Created by sergi on 12/11/15.
 */
public class MovieGridAdapter extends ArrayAdapter<Result> implements Serializable {

    final private String posterUrl = "http://image.tmdb.org/t/p/";
    final private String posterSize = "w185";
    DecimalFormat oneDecimalOnly = new DecimalFormat("#.#");

    public MovieGridAdapter(Context context, int resource, List<Result> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Creamos el objeto en la posición correspondiente
        Result pelicula = getItem(position);

        // Comprobamos si la view ya se ha usado antes, si no, la inflamos (es una buena practica y ahorramos recursos)
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.grid_view_layout, parent, false);
        }

        // Asociamos cada variable a su elemento del layout
        // TextView titulo = (TextView) convertView.findViewById(R.id.grid_titulo);
        ImageView imagenPoster = (ImageView) convertView.findViewById(R.id.grid_imagenPoster);
        TextView titulo = (TextView) convertView.findViewById(R.id.grid_Titulo);

        // Incorporamos los objetos al layout

        /*  //Fragmento para dejar el listView más ordenado y no descuadrarlo
        if(titulo.getLineCount() > 1){
            description.setMaxLines(7);
            description.setEllipsize(TextUtils.TruncateAt.END);
        }*/

        titulo.setText(pelicula.getTitle() + "\n (" + pelicula.getReleaseDate().substring(0, 4) + ")");
        Picasso.with(getContext()).load(posterUrl + posterSize + pelicula.getPosterPath()).fit().into(imagenPoster);

        return convertView; //Devolvemos la view ya rellena
    }
}
