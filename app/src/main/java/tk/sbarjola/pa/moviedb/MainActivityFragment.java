package tk.sbarjola.pa.moviedb;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    //MovieDB API Key "a7ec645f7c4f6bffaab8a964820325f7"
    //GET Movie Popular http://api.themoviedb.org/3/movie/popular
    // Full URL http://api.themoviedb.org/3/movie/popular?api_key=a7ec645f7c4f6bffaab8a964820325f7

    private MovieDbService service;
    ArrayAdapter<String> myAdapter; //Adaptador per al listView
    private ArrayList<String> items;    ///ArrayList amb els items **provisional
    private ListView listaPeliculas; //ListView on mostrarem els items
    private TextView misPeliculas;
    private String BaseURL = "http://api.themoviedb.org/3/movie/";
    private String apiKey = "a7ec645f7c4f6bffaab8a964820325f7";

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);      //Aixo fa que mostri el menu. Com n'hi han fragments no grafics cal especificar-ho
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

    public void refresh(){

        Retrofit retrofit = new Retrofit.Builder()  //Retrofit
                .baseUrl(BaseURL)   //Primera parte de la url
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(MovieDbService.class);    //

        Call<ListResult> llamada = (Call<ListResult>) service.pelispopulares(apiKey);
            llamada.enqueue(new Callback<ListResult>() {
                @Override
                public void onResponse(Response<ListResult> response, Retrofit retrofit) {
                    ArrayList<String> arrayPeliculas = new ArrayList<String>(); // Fem un array on em
                    ListResult resultado = response.body();
                    for(Result list : resultado.getResults()){
                        int id = list.getId();  // Demanem el ID de la pelicula
                        String titulo = list.getTitle();    // Demanem el titol de la pelicula
                        arrayPeliculas.add(String.valueOf(id) + " | " + titulo);    //Afegim al array el la id i el titol
                    }
                    myAdapter.clear();
                    myAdapter.addAll(arrayPeliculas);
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });

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

        //afegim diverses entrades al ListView que apareixeran per defecte
        myAdapter.add("Kill Bill");
        myAdapter.add("Battle Royale");
        myAdapter.add("Robo Gueisha");
        myAdapter.add("Rape Zombie");
        myAdapter.add("Austin Powers");
        myAdapter.add("A Serbian Film");
        myAdapter.add("Sharknado");

        return fragmentoLista;
    }

    public interface MovieDbService{
        @GET("popular")
        Call<ListResult> pelispopulares(
                @Query("api_key") String api_key);
    }
}
