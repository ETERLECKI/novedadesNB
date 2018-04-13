package ar.com.nbcargo.nbcargo_novedades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "ContentMainActivity";
    SharedPreferences preferencias;
    SharedPreferences.Editor upreferencias;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerViewAdapter adapter;
    private SwipeRefreshLayout sRl;
    private String subTituloA;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sRl = findViewById(R.id.sRl);
        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        toolbar.setLogo(R.drawable.ic_launcher);
        subTituloA = "Todas ";
        preferencias = getSharedPreferences("MisPreferencias", getApplicationContext().MODE_PRIVATE);
        upreferencias = preferencias.edit();

        sRl = findViewById(R.id.sRl);
        //sRl.setColorSchemeResources(R.color.colorPrimary);
        //sRl.setProgressBackgroundColorSchemeResource(R.color.colorPrimary);
        sRl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sRl.setRefreshing(false);
                subTituloA = "Todas ";
                requestJsonObject("Todas");
            }
        });

        requestJsonObject("Todas");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, agregarNovedad.class));
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        requestJsonObject("Todas");
        subTituloA = "Todas ";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mnu_taller) {
            //setTitle("Novedades para Taller NB");
            //getSupportActionBar().setSubtitle("Taller ");
            //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#227585")));
            subTituloA = "Taller ";
            requestJsonObject("Taller");
            return true;
        }

        if (id == R.id.mnu_patrimonial) {
            //setTitle("Novedades para Patrimonial");
            //getSupportActionBar().setSubtitle("Patrimonial ");
            //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF8A80")));
            subTituloA = "Patrimonial ";
            requestJsonObject("Patrimonial");
            return true;
        }

        if (id == R.id.mnu_trafico) {
            //setTitle("Novedades para Tráfico");
            //getSupportActionBar().setSubtitle("Tráfico ");
            //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#B388FF")));
            subTituloA = "Tráfico ";
            requestJsonObject("Trafico");
            return true;
        }

        if (id == R.id.mnu_conformes) {
            //setTitle("Novedades para Conformes");
            //getSupportActionBar().setSubtitle("Conformes ");
            //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E91E63")));
            subTituloA = "Conformes ";
            requestJsonObject("Conformes");
            return true;
        }

        if (id == R.id.mnu_documentacion) {
            //setTitle("Novedades para Documentación");
            //getSupportActionBar().setSubtitle("Documentación ");
            //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2D566B")));
            subTituloA = "Documentación ";
            requestJsonObject("Documentacion");
            return true;
        }

        if (id == R.id.mnu_todas) {
            //setTitle("Todas las novedades");
            //getSupportActionBar().setSubtitle("Todas ");
            subTituloA = "Todas ";
            requestJsonObject("Todas");
            return true;
        }

        if (id == R.id.mnu_cerrar_sesion) {
            upreferencias.putString("sesion", "cerrada");
            upreferencias.commit();
            startActivity(new Intent(this, login.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void requestJsonObject(String consulta) {

        RequestQueue queue = Volley.newRequestQueue(this);


        String url = "http://192.168.5.199/novedades_nb_cards.php?filtro=" + consulta;
        Log.d("pagina", "Página: " + url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response " + response);
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();

                List<ItemObject> posts = new ArrayList<ItemObject>();
                posts.clear();
                posts = Arrays.asList(mGson.fromJson(response, ItemObject[].class));
                Log.d(TAG, "Lista de items:" + posts);

                adapter = new RecyclerViewAdapter(MainActivity.this, posts);
                recyclerView.setAdapter(adapter);
                getSupportActionBar().setSubtitle("");
                getSupportActionBar().setSubtitle(subTituloA + "(" + String.valueOf(adapter.getItemCount()) + ")");


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error " + error.getMessage());
            }
        });
        queue.add(stringRequest);
    }

}
