package tk.sbarjola.pa.moviedb;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
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

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;
import tk.sbarjola.pa.moviedb.provider.movie.MovieColumns;
import tk.sbarjola.pa.moviedb.provider.movie.MovieContentValues;

public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    // MovieDB API Key "a7ec645f7c4f6bffaab8a964820325f7"
    // GET Movie Popular http://api.themoviedb.org/3/movie/popular
    // Full URL http://api.themoviedb.org/3/movie/popular?api_key=a7ec645f7c4f6bffaab8a964820325f7

    public FloatingActionButton fab;                // FloatingActionBar para cambiar entre listView y gridView
    public boolean listaVisible = false;            // Variable que controla si se muestra el listView o el gridView
    private MovieDbService service;                 // Interfaz para los servicios de Retrofit
    private cacheListAdapter myListAdapter;         // Adaptador per al listView i ho emmagatzema a la base de dates
    private cacheGridAdapter myGridAdapter;         // Adaptador per al listView i ho emmagatzema a la base de dates
    private ListView listaPeliculas;                // ListView on mostrarem els items
    private GridView gridPeliculas;                 // GridView on mostrarem els items
    private TextView misPeliculas;                  // TextView Auxiliar

    // Datos de la API

    private String BaseURL = "http://api.themoviedb.org/3/movie/";  //Principio de la URL que usará retrofit
    private String apiKey = "a7ec645f7c4f6bffaab8a964820325f7";     // Key de MovieDB

    // Declarem el retrofit variable global per a que així puguem ferlo servir desde tots els metodes sense tornar-ho a definir

    private Retrofit retrofit = new Retrofit.Builder()  //Retrofit
            .baseUrl(BaseURL)   //Primera parte de la url
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    public void onStart() { //Que cada cop que s'obre l'activity s'actualitzi la llista
        super.onStart();

        // Es gestiona les preferencies. Segons les preferencies, cridarà al mètode popular o al mètode toprated
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());   // necesario para referenciar y leer la configuración del programa

        if (settings.getString("ListaPeliculas", "0").equals("0")) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("MovieDB - Popular");
        }
        else if (settings.getString("ListaPeliculas", "1").equals("1")) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("MovieDB - Top Rated");
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);    //Aixo fa que mostri el menu. Com n'hi han fragments no grafics cal especificar-ho
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) { //Afegim una opcio "Refresh" al menu del fragment
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    public void refresh() {  // El metode refresh nomes descarrega pelicules, nomes al pulsar-lo gastem dades

        // Limpiamos la base de datos y descargamos los datos de las dos categorías, de Popular y de TopRated
        UpdateMoviesTask downloadMoviesTask = new UpdateMoviesTask();
        downloadMoviesTask.execute();
    }

    public void downloadMovies() {  // Actualitza la llista descargando todas las peliculas

        service = retrofit.create(MovieDbService.class);    // Llamamos a nuestro servicio de retrofit

        Call<ListResult> llamada = (Call<ListResult>) service.pelispopulares(apiKey);   // Uno para pelisPopulares
        procesaResultado(llamada, "populares");

        llamada = (Call<ListResult>) service.pelisvaloradas(apiKey);    // Otro para las mejor valoradas
        procesaResultado(llamada, "topRated");

    }

    private void procesaResultado(Call<ListResult> llamada, String categoriaPelicula) {

        try {

            Response<ListResult> response = llamada.execute();
            ListResult resultado = response.body();

            for (Result movie : resultado.getResults()) {    // En este for guardamos en la base de datos
                MovieContentValues values = new MovieContentValues();
                values.putTitle(movie.getTitle());
                values.putDescription(movie.getOverview());
                values.putReleasedate(movie.getReleaseDate());
                values.putPopularity(movie.getPopularity().toString());
                values.putPosterpath(movie.getPosterPath());
                values.putOriginaltitle(movie.getOriginalTitle());
                values.putLanguage(movie.getOriginalLanguage());
                values.putCategory(categoriaPelicula);
                getContext().getContentResolver().insert(
                        MovieColumns.CONTENT_URI, values.values());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentoLista = inflater.inflate(R.layout.fragment_main, container, false);    //Definimos el fragment
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());   // necesario para referenciar y leer la configuración del programa

        //ActionBar que muestra y oculta el listView y el gridView para cambiar como se muestran las peliculas
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

        // Declaramos los TextView y el GridView para hacer los adapters
        misPeliculas = (TextView) fragmentoLista.findViewById(R.id.misPeliculas);
        listaPeliculas = (ListView) fragmentoLista.findViewById(R.id.listaPeliculas);
        gridPeliculas = (GridView) fragmentoLista.findViewById(R.id.gridPeliculas);

        // Creamos y definimos el GridAdapter
        myGridAdapter = new cacheGridAdapter(
                getContext(),
                R.layout.grid_view_layout,
                null,
                new String[]{MovieColumns.TITLE, MovieColumns.POSTERPATH},
                new int[]{R.id.grid_Titulo, R.id.grid_imagenPoster},
                0);

        gridPeliculas.setAdapter(myGridAdapter);    //Acoplem el adaptador

        // Creamos y definimos el ListAdapter
        myListAdapter = new cacheListAdapter(
                getContext(),
                R.layout.list_view_layout,
                null,
                new String[]{MovieColumns.TITLE, MovieColumns.DESCRIPTION, MovieColumns.POPULARITY, MovieColumns.RELEASEDATE, MovieColumns.POSTERPATH},
                new int[]{R.id.list_titulo, R.id.list_descripcion, R.id.list_puntuacion, R.id.list_diaSalida, R.id.list_imagenPoster},
                0);

        listaPeliculas.setAdapter(myListAdapter);   //Acoplem el adaptador

        // Gestionamos que vista va a mostrar la APP

        if (settings.getString("VistaScreen", "0").equals("0")) {
            gridPeliculas.setVisibility(View.INVISIBLE);
            listaPeliculas.setVisibility(View.VISIBLE);
            listaVisible = true;
        } else if (settings.getString("VistaScreen", "1").equals("1")) {
            listaPeliculas.setVisibility(View.GONE);
            gridPeliculas.setVisibility(View.VISIBLE);
            listaVisible = false;
        }

        // Listeners que controlan las pulsaciones de la listView y gridView

        listaPeliculas.setOnItemClickListener(new AdapterView.OnItemClickListener() {   //Listener para el list
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), DetailsActivty.class);
                i.putExtra("cursor_id", id);
                startActivity(i);
            }
        });

        gridPeliculas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  //Listener para el grid
                Intent i = new Intent(getContext(), DetailsActivty.class);
                i.putExtra("cursor_id", id);
                startActivity(i);
            }
        });

        getLoaderManager().initLoader(0, null, this);   // Creamos un loaderManager

        //Afegim diverses entrades al ListView que apareixeran per defecte
        return fragmentoLista;
    }

    // Método para limpiar y vaciar la base de datos
    private void clearMovies() {
        getContext().getContentResolver().delete(
                MovieColumns.CONTENT_URI,
                null,
                null);
    }

    // Según en qué situación haremos una cosa u otra

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {

        // Es gestiona les preferencies. Segons les preferencies, cridarà al mètode popular o al mètode toprated
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());

        // Aquí gestiona que categoria de peliculas va a mostrar

        if (settings.getString("ListaPeliculas", "0").equals("0")) {
            return new CursorLoader(getContext(),
                    MovieColumns.CONTENT_URI,
                    null,
                    MovieColumns.CATEGORY + "= ? ", new String[]{"populares"},
                    null);

        }
        else {
            return new CursorLoader(getContext(),
                    MovieColumns.CONTENT_URI,
                    null,
                    MovieColumns.CATEGORY + "= ? ", new String[]{"topRated"},
                    null);

        }

        /*
                return new android.support.v4.content.CursorLoader(getContext(),
                MovieColumns.CONTENT_URI,
                null,
                null,
                null,
                "_id");
         */
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        myListAdapter.swapCursor(data);
        myGridAdapter.swapCursor(data);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        myListAdapter.swapCursor(null);
        myGridAdapter.swapCursor(null);
    }

public interface MovieDbService { //Interficie per a la llista de popular
    @GET("popular")
    Call<ListResult> pelispopulares(
            @Query("api_key") String api_key);

    @GET("top_rated")
    Call<ListResult> pelisvaloradas(
            @Query("api_key") String api_key);
}

    class UpdateMoviesTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            clearMovies();
            downloadMovies();
            return null;
        }
    }
}