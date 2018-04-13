package ar.com.nbcargo.nbcargo_novedades;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.util.Calendar;

public class acciones extends AppCompatActivity {

    TextView a_numinc;
    TextView a_fecha;
    TextView a_novedad;
    TextView a_severidad;
    TextView a_chofer;
    TextView a_unidad;
    TextView a_accion;
    EditText fecha_seguro;
    EditText fecha_cierre;
    DatePickerDialog picker;
    Button acciones_btn_realizado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acciones);

        a_numinc = findViewById(R.id.acciones_incidente);
        a_fecha = findViewById(R.id.acciones_fecha);
        a_novedad = findViewById(R.id.acciones_novedad);
        a_severidad = findViewById(R.id.acciones_severidad);
        a_chofer = findViewById(R.id.acciones_chofer);
        a_unidad = findViewById(R.id.acciones_unidad);
        a_accion = findViewById(R.id.acciones_accion);
        acciones_btn_realizado = findViewById(R.id.acciones_btn_acp);

        fecha_seguro = findViewById(R.id.seg_fecha_denuncia);
        fecha_cierre = findViewById(R.id.seg_fecha_cierre);

        fecha_cierre.setInputType(InputType.TYPE_NULL);
        fecha_cierre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(acciones.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                fecha_cierre.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        fecha_seguro.setInputType(InputType.TYPE_NULL);
        fecha_seguro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(acciones.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                fecha_seguro.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });


        String valorDeId = getIntent().getStringExtra("id");

        cargaDatosAcciones(valorDeId);


        acciones_btn_realizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acciones.super.onBackPressed();
            }
        });

    }

    private void cargaDatosAcciones(String consulta) {

        String url = "http://192.168.5.199/novedades_nb_cards.php?filtro=id&id=" + consulta;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("TAG", "Entra a unidad");
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("success") == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("accion");
                        //for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        String id = jsonObject1.getString("id");
                        String fecha = jsonObject1.getString("fecha");
                        String novedad = jsonObject1.getString("novedad");
                        String severidad = jsonObject1.getString("severidad");
                        String chofer = jsonObject1.getString("chofer");
                        String unidad = jsonObject1.getString("unidad");
                        String accion = jsonObject1.getString("accion");

                        //itemsUnidad.add(unidad);
                        a_numinc.setText("Incidente N°: " + id);
                        a_fecha.setText("Fecha: " + fecha);
                        a_novedad.setText(novedad);
                        a_severidad.setText(severidad);
                        a_chofer.setText("Chofer: " + chofer);
                        a_unidad.setText("Unidad: " + unidad);
                        a_accion.setText(accion);
                        //}
                    }
                } catch (JSONException e) {
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
