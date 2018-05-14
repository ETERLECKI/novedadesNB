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
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

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
    String estado;
    Integer tipo_usuario;
    MenuItem est_abiertas;
    TextView texto_error;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG", "entra en main");
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sRl = findViewById(R.id.sRl);
        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        //toolbar.setLogo(R.drawable.ic_launcher);
        toolbar.getBackground().setAlpha(70);
        subTituloA = "Todas ";
        preferencias = getSharedPreferences("MisPreferencias", getApplicationContext().MODE_PRIVATE);
        upreferencias = preferencias.edit();
        tipo_usuario = preferencias.getInt("tipo", 2);
        est_abiertas = findViewById(R.id.mnu_estado);
        texto_error = findViewById(R.id.main_textoerror);
        estado = "Abiertas";

        sRl = findViewById(R.id.sRl);
        sRl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sRl.setRefreshing(false);
                subTituloA = "Todas ";
                requestJsonObject("Todas", "");
            }
        });

        requestJsonObject("Todas", "");

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
        requestJsonObject("Todas", "");
        subTituloA = "Todas ";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        Log.d("TAG", "Tipo de usuario: " + tipo_usuario);
        if (tipo_usuario == 1) {
            menu.findItem(R.id.mnu_estado).setVisible(true);
        }
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
            subTituloA = "Taller";
            if (estado.equals("Abiertas")) {
                requestJsonObject("", "Taller");
            } else {
                requestJsonObject("Filtro_1", "Taller");
            }
            return true;
        }

        if (id == R.id.mnu_patrimonial) {
            subTituloA = "Patrimonial";
            if (estado.equals("Abiertas")) {
                requestJsonObject("", "Patrimonial");
            } else {
                requestJsonObject("Filtro_1", "Patrimonial");
            }
            return true;
        }

        if (id == R.id.mnu_trafico) {
            subTituloA = "Tráfico";
            if (estado.equals("Abiertas")) {
                requestJsonObject("", "Trafico");
            } else {
                requestJsonObject("Filtro_1", "Trafico");
            }
            return true;
        }

        if (id == R.id.mnu_conformes) {
            subTituloA = "Conformes";
            if (estado.equals("Abiertas")) {
                requestJsonObject("", "Conformes");
            } else {
                requestJsonObject("Filtro_1", "Conformes");
            }
            return true;
        }

        if (id == R.id.mnu_documentacion) {
            subTituloA = "Documentación";
            if (estado.equals("Abiertas")) {
                requestJsonObject("", "Documentacion");
            } else {
                requestJsonObject("Filtro_1", "Documentacion");
            }

            return true;
        }

        if (id == R.id.mnu_todas) {
            subTituloA = "Todas";
            if (estado.equals("Abiertas")) {
                requestJsonObject("Todas", "");
            } else {
                requestJsonObject("Todas1", "");
            }
            return true;
        }

        if (id == R.id.mnu_cerrar_sesion) {
            upreferencias.putString("sesion", "cerrada");
            upreferencias.commit();
            finish();
            return true;
        }

        if (id == R.id.mnu_est_abiertas) {
            estado = "Abiertas";
            getSupportActionBar().setTitle("Novedades");
            requestJsonObject("Todas", "");
            return true;
        }

        if (id == R.id.mnu_est_realizadas) {
            estado = "Realizadas";
            getSupportActionBar().setTitle(estado);
            requestJsonObject("Todas1", "");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void requestJsonObject(String consulta, String filtro) {

        RequestQueue queue = Volley.newRequestQueue(this);


        String url = "http://192.168.5.199/novedades_nb_cards.php?consulta=" + consulta + "&filtro=" + filtro;
        Log.d("TAG1", "Página: " + url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                List<ItemObject> posts = new ArrayList<ItemObject>();
                posts.clear();

                if (response.equals("")) {
                    Toast.makeText(MainActivity.this, "Actualmente no hay novedades a mostrar de este tipo", Toast.LENGTH_LONG).show();
                    getSupportActionBar().setSubtitle(subTituloA + " (0)");
                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    posts = Arrays.asList(mGson.fromJson(response, ItemObject[].class));
                    adapter = new RecyclerViewAdapter(MainActivity.this, posts);
                    recyclerView.setAdapter(adapter);
                    getSupportActionBar().setSubtitle("");
                    getSupportActionBar().setSubtitle(subTituloA + " (" + String.valueOf(adapter.getItemCount()) + ")");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG1", "Error " + error.getMessage());
            }
        });
        queue.add(stringRequest);
    }

}
