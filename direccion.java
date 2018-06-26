package ar.com.nbcargo.nbcargo_novedades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class direccion extends AppCompatActivity {

    TextView dir_num_nov;
    TextView dir_chofer;
    TextView dir_unidad;
    TextView dir_novedad;
    TextView dir_obs_nov;
    TextView dir_odometro;
    TextView dir_accion1_txt;
    TextView dir_fecha_accion1;
    TextView dir_obs_accion1;
    TextView dir_realizo_accion1;
    View dir_accion2;
    TextView dir_accion2_txt;
    TextView dir_fecha_accion2;
    TextView dir_obs_accion2;
    TextView dir_realizo_accion2;
    View dir_rep;
    EditText dir_monto_rep;
    EditText dir_obs_rep;
    View dir_seg;
    TextView dir_fecha_denun;
    EditText dir_monto_denun;
    TextView dir_fecha_cierre;
    EditText dir_monto_rec;
    EditText dir_obs_seg;
    TextView dir_realizo_seg;
    View dir_combustible;
    EditText dir_monto_comb;
    EditText dir_obs_comb;
    CheckBox dir_aplica_desc;
    EditText dir_monto_final;
    EditText dir_obs_final;
    Button dir_button_fin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Tag1", "Dirección?");
        setContentView(R.layout.activity_direccion);
        Log.d("Tag1", "Entro a Direccion");

        dir_novedad = findViewById(R.id.dir_novedad);
        dir_obs_nov = findViewById(R.id.dir_obs_nov);
        dir_odometro = findViewById(R.id.dir_odometro);
        dir_num_nov = findViewById(R.id.dir_num_nov);
        dir_chofer = findViewById(R.id.dir_chofer);
        dir_unidad = findViewById(R.id.dir_unidad);
        dir_accion1_txt = findViewById(R.id.dir_accion1_txt);
        dir_fecha_accion1 = findViewById(R.id.dir_fecha_accion1);
        dir_obs_accion1 = findViewById(R.id.dir_obs_accion1);
        dir_realizo_accion1 = findViewById(R.id.dir_realizo_accion1);
        dir_accion2 = findViewById(R.id.dir_accion2);
        dir_accion2_txt = findViewById(R.id.dir_accion2_txt);
        dir_fecha_accion2 = findViewById(R.id.dir_fecha_accion2);
        dir_obs_accion2 = findViewById(R.id.dir_obs_accion2);
        dir_realizo_accion2 = findViewById(R.id.dir_realizo_accion2);
        dir_rep = findViewById(R.id.dir_rep);
        dir_monto_rep = findViewById(R.id.dir_monto_rep);
        dir_obs_rep = findViewById(R.id.dir_obs_rep);
        dir_seg = findViewById(R.id.dir_seg);
        dir_fecha_denun = findViewById(R.id.dir_fecha_denun);
        dir_monto_denun = findViewById(R.id.dir_monto_denun);
        dir_fecha_cierre = findViewById(R.id.dir_fecha_cierre);
        dir_monto_rec = findViewById(R.id.dir_monto_rec);
        dir_obs_seg = findViewById(R.id.dir_obs_seg);
        dir_realizo_seg = findViewById(R.id.dir_realizo_seg);
        dir_combustible = findViewById(R.id.dir_comb);
        dir_monto_comb = findViewById(R.id.dir_monto_comb);
        dir_obs_comb = findViewById(R.id.dir_obs_comb);
        dir_aplica_desc = findViewById(R.id.dir_aplica_desc);
        dir_monto_final = findViewById(R.id.dir_monto_final);
        dir_obs_final = findViewById(R.id.dir_obs_final);
        dir_button_fin = findViewById(R.id.dir_button_fin);


        Bundle datos = getIntent().getExtras();
        int fondo_tarjeta = datos.getInt("color");


    }

    private void cargaDatosAcciones(String consulta) {

        final String url = "http://192.168.5.199/novedades_nb_cards.php?consulta=id&filtro=acciones&id=" + consulta;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonObject = new JSONObject(response);

                    Log.d("TAG2", "response: " + response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Name");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    String id = jsonObject1.getString("id");
                    String fecha = jsonObject1.getString("fecha");
                    String novedad = jsonObject1.getString("novedad");
                    String severidad = jsonObject1.getString("severidad");
                    String chofer = jsonObject1.getString("chofer");
                    String unidad = jsonObject1.getString("unidad");
                    String accion = jsonObject1.getString("accion");
                    String accion2 = jsonObject1.getString("accion2");
                    String denun = jsonObject1.getString("fecha_denuncia");
                    String gasto = jsonObject1.getString("monto_denuncia");
                    String recu = jsonObject1.getString("monto_recuperado");
                    String cierre = jsonObject1.getString("cierre_seguro");
                    String montoDesc = jsonObject1.getString("monto_descuento");
                    //Boolean aplicaDesc =jsonObject1.getBoolean("aplica_descuento");
                    //Boolean aplicaSeg = jsonObject1.getBoolean("aplica_seguro");
                    String obs_seg = jsonObject1.getString("obs_seg");

                    a_fecha.setText("Fecha: " + fecha);
                    a_novedad.setText(novedad);
                    a_severidad.setText(severidad);
                    a_chofer.setText("Chofer: " + chofer);
                    a_unidad.setText("Unidad: " + unidad);

                    if (!denun.equals("null")) {
                        fecha_seguro.setText(denun);
                    }
                    if (!gasto.equals("null")) {
                        monto_seguro.setText(gasto);
                    }
                    if (!recu.equals("null")) {
                        rec_seguro.setText(recu);
                    }
                    if (!cierre.equals("null")) {
                        fecha_cierre.setText(cierre);
                    }
                    //na_Seguro.setChecked(aplicaSeg);
                    //descuento.setChecked(aplicaDesc);
                    if (!montoDesc.equals("null")) {
                        monto.setText(montoDesc);
                    }

                    if (estado.equals("Abierta")) {
                        Log.d("TAG2", "Entra en texto accion con estado = " + estado.toString());
                        a_accion.setText(accion);
                    } else if (estado.equals("Seguro")) {
                        a_accion.setText("Completar fechas y montos de seguro");
                        acciones_seguro.setVisibility(View.VISIBLE);
                        na_Seguro.setVisibility(View.VISIBLE);
                    } else if (estado.equals("Descuento")) {
                        a_accion.setText("Completar monto de Reparación/exceso de combustible");
                        descuento.setVisibility(View.VISIBLE);
                        monto.setVisibility(View.VISIBLE);
                    } else if (estado.equals("Accion2")) {
                        a_accion.setText(accion2);
                    }
                    getSupportActionBar().setTitle("Novedad N° " + id);


                } catch (JSONException e) {
                    Log.d("TAG2", "Error en Try/Catch");
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("TAG", "Zona de error unidad");
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }

}
