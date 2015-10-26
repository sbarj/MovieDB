package tk.sbarjola.pa.moviedb;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ArrayAdapter<String> myAdapter; //Adaptador per al listView
    private ArrayList<String> items;    ///ArrayList amb els items **provisional
    private ListView listaPeliculas   ; //ListView on mostrarem els items
    private TextView misPeliculas;       //TestView donde mostraremos los dias

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);        //Aixo fa que mostri el menu. Com n'hi han fragments no grafics cal especificar-ho
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) { //Afegim una opcio "Refresh" al menu del fragment
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_fragment, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentoLista = inflater.inflate(R.layout.fragment_main, container, false);    //Definimos el fragment

        items = new ArrayList<>();     //array list que contindrà les pel·licules

        misPeliculas = (TextView) fragmentoLista.findViewById(R.id.misPeliculas);  //Asignem el ID
        listaPeliculas = (ListView) fragmentoLista.findViewById(R.id.listaPeliculas);    //Asignme el id

        myAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,items);  // Definim adaptador al layaout predefinit i al nostre array items
        listaPeliculas.setAdapter(myAdapter);    //Acoplem el adaptador

        //afegim diverses entrades al ListView
        myAdapter.add("Kill Bill");
        myAdapter.add("Battle Royale");
        myAdapter.add("Robo Gueisha");
        myAdapter.add("Rape Zombie");
        myAdapter.add("Austin Powers");
        myAdapter.add("A Serbian Film");
        myAdapter.add("Sharknado");

        return fragmentoLista;
    }
}
