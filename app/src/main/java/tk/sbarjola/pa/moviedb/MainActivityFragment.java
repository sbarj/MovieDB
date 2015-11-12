package tk.sbarjola.pa.moviedb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

public class MainActivityFragment extends Fragment {

    // MovieDB API Key "a7ec645f7c4f6bffaab8a964820325f7"
    // GET Movie Popular http://api.themoviedb.org/3/movie/popular
    // Full URL http://api.themoviedb.org/3/movie/popular?api_key=a7ec645f7c4f6bffaab8a964820325f7

    public FloatingActionButton fab;
    public boolean listaVisible = false;
    private MovieDbServicePopular servicePopular;   // Interfaz para las peliculas populares
    private MovieDbServiceTopRated serviceTopRated; // Interfaz para las peliculas mejor valoradas
    MovieListAdapter myListAdapter;  //Adaptador per al listView
    MovieGridAdapter myGridAdapter;  //Adaptador per al gridView
    private ArrayList<Result> items; ///ArrayList amb els items **provisional
    private ListView listaPeliculas; //ListView on mostrarem els items
    private GridView gridPeliculas;  //GridView on mostrarem els items
    private TextView misPeliculas;
    private String BaseURL = "http://api.themoviedb.org/3/movie/";  //Principio de la URL que usará retrofit
    private String apiKey = "a7ec645f7c4f6bffaab8a964820325f7"; // Key de MovieDB

    // Declarem el retrofit variable global per a que així puguem ferlo servir desde tots els metodes sense tornar-ho a definir

    private Retrofit retrofit = new Retrofit.Builder()  //Retrofit
            .baseUrl(BaseURL)   //Primera parte de la url
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    public void onStart() { //Que cada cop que s'obre l'activity s'actualitzi la llista
        super.onStart();
        refresh();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);      //Aixo fa que mostri el menu. Com n'hi han fragments no grafics cal especificar-ho
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){ //Afegim una opcio "Refresh" al menu del fragment
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            refresh();  //Fem que al presionar el Refresh cridi al metode refresh
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void refresh(){  // El mètode refresh gestiona les preferencies. Segons les preferencies, cridarà al mètode popular o al mètode toprated

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());   // necesario para referenciar y leer la configuración del programa

        // Aquí gestiona qué peliculas va a mostrar

        if(settings.getString("ListaPeliculas", "0").equals("0")){
            popular();
        }
        else if (settings.getString("ListaPeliculas", "1").equals("1")){
            topRated();
        }

        // Y en esta sección CÓMO las va a mostrar

        if(settings.getString("VistaScreen", "0").equals("0")){
            gridPeliculas.setVisibility(View.INVISIBLE);
            listaPeliculas.setVisibility(View.VISIBLE);
            listaVisible = true;
        }
        else if (settings.getString("VistaScreen", "1").equals("1")){
            listaPeliculas.setVisibility(View.GONE);
            gridPeliculas.setVisibility(View.VISIBLE);
            listaVisible = false;
        }
    }

    public void popular(){  // Actualitza la llista amb el llistat de "populars"

        servicePopular = retrofit.create(MovieDbServicePopular.class);

        Call<ListResult> llamada = (Call<ListResult>) servicePopular.pelispopulares(apiKey);
            llamada.enqueue(new Callback<ListResult>() {
                @Override
                public void onResponse(Response<ListResult> response, Retrofit retrofit) {
                    ListResult resultado = response.body();

                    myGridAdapter.clear();
                    myGridAdapter.addAll(resultado.getResults());

                    myListAdapter.clear();
                    myListAdapter.addAll(resultado.getResults());
                }

                @Override
                public void onFailure(Throwable t) {}
            });
    }

    public void topRated(){ // Actualitza la llista amb el llistat de "top rated"

        serviceTopRated = retrofit.create(MovieDbServiceTopRated.class);    //

        Call<ListResult> llamada = (Call<ListResult>) serviceTopRated.pelisvaloradas(apiKey);
        llamada.enqueue(new Callback<ListResult>(){
            @Override
            public void onResponse(Response<ListResult> response, Retrofit retrofit) {
                ListResult resultado = response.body();

                myGridAdapter.clear();
                myGridAdapter.addAll(resultado.getResults());

                myListAdapter.clear();
                myListAdapter.addAll(resultado.getResults());
            }

            @Override
            public void onFailure(Throwable t) {}
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View fragmentoLista = inflater.inflate(R.layout.fragment_main, container, false);    //Definimos el fragment
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());   // necesario para referenciar y leer la configuración del programa

        //ActionBar
        fab = (FloatingActionButton) fragmentoLista.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listaVisible == false) {
                    gridPeliculas.setVisibility(View.GONE);
                    listaPeliculas.setVisibility(View.VISIBLE);
                    listaVisible = true;
                } else {
                    listaPeliculas.setVisibility(View.GONE);
                    gridPeliculas.setVisibility(View.VISIBLE);
                    listaVisible = false;
                }
            }
        });

        items = new ArrayList<>();     //array list que contindrà les pel·licules
        misPeliculas = (TextView) fragmentoLista.findViewById(R.id.misPeliculas);  //Asignem el ID
        listaPeliculas = (ListView) fragmentoLista.findViewById(R.id.listaPeliculas);    //Asignme el id
        gridPeliculas = (GridView) fragmentoLista.findViewById(R.id.gridPeliculas);

        myGridAdapter = new MovieGridAdapter(getContext(), 0, items);
        gridPeliculas.setAdapter(myGridAdapter);

        myListAdapter = new MovieListAdapter(getContext(), 0, items);  // Definim adaptador al layaout predefinit i al nostre array items
        listaPeliculas.setAdapter(myListAdapter);    //Acoplem el adaptador

        listaPeliculas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Result selectedFilm = (Result) parent.getItemAtPosition(position);
                Intent detallesPeliculas = new Intent(getContext(), DetailsActivty.class);
                detallesPeliculas.putExtra("pelicula", selectedFilm);
                startActivity(detallesPeliculas);
            }
        });

        gridPeliculas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Result selectedFilm = (Result) parent.getItemAtPosition(position);
                Intent detallesPeliculas = new Intent(getContext(), DetailsActivty.class);
                detallesPeliculas.putExtra("pelicula", selectedFilm);
                startActivity(detallesPeliculas);
            }
        });

        //Afegim diverses entrades al ListView que apareixeran per defecte
        return fragmentoLista;
    }

    public interface MovieDbServicePopular{ //Interficie per a la llista de popular
        @GET("popular")
        Call<ListResult> pelispopulares(
                @Query("api_key") String api_key);
    }

    public interface MovieDbServiceTopRated{ //Interficie per a la llista de topRated
        @GET("top_rated")
        Call<ListResult> pelisvaloradas(
                @Query("api_key") String api_key);
    }
}
