package ar.com.nbcargo.nbcargo_novedades;

import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    CheckBox descuento;
    EditText monto;
    View acciones_seguro;
    String estado;
    EditText observaciones;
    EditText monto_seguro;
    EditText rec_seguro;
    String url;
    CheckBox na_Seguro;
    View tarjeta;
    TextView tit_accion;
    TextView tit_denuncia;
    TextView tit_gasto;
    TextView tit_monto;
    TextView tit_cierre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG2", "Entra en acciones");
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_acciones);

        a_fecha = findViewById(R.id.acciones_fecha);
        a_novedad = findViewById(R.id.acciones_novedad);
        a_severidad = findViewById(R.id.acciones_severidad);
        a_chofer = findViewById(R.id.acciones_chofer);
        a_unidad = findViewById(R.id.acciones_unidad);
        a_accion = findViewById(R.id.acciones_accion);
        tit_accion = findViewById(R.id.acciones_tit_accion);
        tit_denuncia = findViewById(R.id.acciones_tit_fechadenun);
        tit_gasto = findViewById(R.id.acciones_tit_gasto);
        tit_monto = findViewById(R.id.acciones_tit_rec);
        tit_cierre = findViewById(R.id.acciones_tit_fechacierre);

        acciones_btn_realizado = findViewById(R.id.acciones_btn_acp);
        descuento = findViewById(R.id.acciones_chk_descuento);
        monto = findViewById(R.id.acciones_edit_monto);
        observaciones = findViewById(R.id.acciones_edit_obs);
        tarjeta = findViewById(R.id.acciones_lay_detail);

        na_Seguro = findViewById(R.id.acciones_chk_naseg);
        fecha_seguro = findViewById(R.id.seg_fecha_denuncia);
        fecha_cierre = findViewById(R.id.seg_fecha_cierre);
        monto_seguro = findViewById(R.id.acciones_edit_montoseg);
        rec_seguro = findViewById(R.id.acciones_edit_montorec);

        acciones_seguro = findViewById(R.id.acciones_seguro);

        //final String fondo_tarjeta = getIntent().getStringExtra("color");

        Bundle datos = getIntent().getExtras();
        int fondo_tarjeta = datos.getInt("color");
        tarjeta.setBackgroundColor(fondo_tarjeta);
        tit_accion.setBackgroundColor(fondo_tarjeta);
        tit_denuncia.setBackgroundColor(fondo_tarjeta);
        tit_gasto.setBackgroundColor(fondo_tarjeta);
        tit_monto.setBackgroundColor(fondo_tarjeta);
        tit_cierre.setBackgroundColor(fondo_tarjeta);
        acciones_btn_realizado.setBackgroundColor(fondo_tarjeta);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(fondo_tarjeta));

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


        final String valorDeId = datos.getString("id");
        getSupportActionBar().setTitle("Novedad N° " + valorDeId);

        cargaDatosAcciones(valorDeId);

        na_Seguro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    acciones_seguro.setVisibility(View.VISIBLE);
                } else {
                    acciones_seguro.setVisibility(View.GONE);
                }
            }
        });


        descuento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (descuento.isChecked()) {
                    monto.setEnabled(true);
                    monto.setFocusable(true);
                } else {
                    monto.setEnabled(false);
                }
            }
        });

        acciones_btn_realizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("TAG2", "Valor de estado: " + estado.toString());
                switch (estado.toString()) {

                    case "Abierta": {
                        Log.d("TAG2", "Entra en Abierta");
                        url = "http://192.168.5.199/acciones.php?id=" + valorDeId + "&observaciones=" + observaciones.getText().toString();
                        break;
                    }
                    case "Accion2": {
                        Log.d("TAG2", "Entra en Accion2");
                        url = "http://192.168.5.199/acciones.php?id=" + valorDeId + "&observaciones=" + observaciones.getText().toString();
                        break;
                    }
                    case "Seguro": {
                        Log.d("TAG2", "Entra en Seguro");
                        url = "http://192.168.5.199/acciones.php?id=" + valorDeId + "&observaciones=" + observaciones.getText().toString() +
                                "&na_seg=" + na_Seguro.isChecked() + "&fecha_seguro=" + fecha_seguro.getText().toString() + "&monto_seguro=" +
                                monto_seguro.getText().toString() + "&rec_seguro=" + rec_seguro.getText().toString() + "&fecha_cierre=" + fecha_cierre.getText().toString();
                        break;
                    }
                    case "Descuento": {
                        Log.d("TAG2", "Entra en Descuento");
                        url = "http://192.168.5.199/acciones.php?id=" + valorDeId + "&observaciones=" + observaciones.getText().toString() + "&descuento=" + descuento.isChecked() + "&monto=" + monto.getText().toString();
                        break;
                    }
                    case "Cerrada": {
                        Log.d("TAG2", "Entra en Cerrada");
                        url = "http://192.168.5.199/acciones.php?id=" + valorDeId + "&observaciones=" + observaciones.getText().toString();
                        break;
                    }
                }

                url = url.replace(" ", "+");
                Log.d("TAGurl", url);

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("TAG2", "Valor de url: " + url);
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("success") == 1) {
                                Toast.makeText(acciones.this, "Acción guardada correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(acciones.this, "No se pudo guardar acción", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d("TAG2", "Zona de error btn_realizado");
                        Toast.makeText(acciones.this, "Error al generar consulta", Toast.LENGTH_SHORT).show();
                    }
                });
                int socketTimeout = 30000;
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);
                requestQueue.add(stringRequest);

                acciones.super.onBackPressed();
            }
        });

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
                    estado = jsonObject1.getString("estado");
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
