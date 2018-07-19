package ar.com.nbcargo.nbcargo_novedades;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class direccion extends AppCompatActivity {

    //Declaro vistas
    View dir_card_titulo;
    TextView dir_fecha_nov;
    TextView dir_sev;
    TextView dir_chofer;
    TextView dir_unidad;
    View dir_nov;
    CardView dir_card_nov;
    TextView dir_novedad;
    TextView dir_obs_nov;
    TextView dir_odometro;
    View dir_accion1;
    CardView dir_card_accion1;
    TextView dir_accion1_txt;
    TextView dir_fecha_accion1;
    TextView dir_obs_accion1;
    TextView dir_realizo_accion1;
    View dir_accion2;
    CardView dir_card_accion2;
    TextView dir_accion2_txt;
    TextView dir_fecha_accion2;
    TextView dir_obs_accion2;
    TextView dir_realizo_accion2;
    View dir_rep;
    CardView dir_card_rep;
    TextView dir_monto_rep;
    TextView dir_obs_rep;
    View dir_seg;
    CardView dir_card_seg;
    TextView dir_fecha_denun;
    TextView dir_monto_denun;
    TextView dir_fecha_cierre;
    TextView dir_monto_rec;
    TextView dir_obs_seg;
    TextView dir_realizo_seg;
    View dir_combustible;
    CardView dir_card_comb;
    TextView dir_consumo;
    TextView dir_monto_comb;
    CheckBox dir_aplica_desc;
    EditText dir_monto_final;
    EditText dir_obs_final;
    Button dir_button_fin;
    View dir_card_fin;
    int fondo_tarjeta;
    String urlfin;
    String valorDeId;
    String aplicaDescuento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_direccion);

//Asigno nombres a las vistas
        dir_card_titulo = findViewById(R.id.dir_card_titulo);
        dir_card_nov = findViewById(R.id.dir_card_nov);
        dir_novedad = findViewById(R.id.dir_novedad);
        dir_fecha_nov = findViewById(R.id.dir_fecha_nov);
        dir_sev = findViewById(R.id.dir_sev);
        dir_obs_nov = findViewById(R.id.dir_obs_nov);
        dir_odometro = findViewById(R.id.dir_odometro);
        dir_chofer = findViewById(R.id.dir_chofer);
        dir_unidad = findViewById(R.id.dir_unidad);
        dir_accion1 = findViewById(R.id.dir_accion1);
        dir_card_accion1 = findViewById(R.id.dir_card_accion1);
        dir_accion1_txt = findViewById(R.id.dir_accion1_txt);
        dir_fecha_accion1 = findViewById(R.id.dir_fecha_accion1);
        dir_obs_accion1 = findViewById(R.id.dir_obs_accion1);
        dir_realizo_accion1 = findViewById(R.id.dir_realizo_accion1);
        dir_accion2 = findViewById(R.id.dir_accion2);
        dir_card_accion2 = findViewById(R.id.dir_card_accion2);
        dir_accion2_txt = findViewById(R.id.dir_accion2_txt);
        dir_fecha_accion2 = findViewById(R.id.dir_fecha_accion2);
        dir_obs_accion2 = findViewById(R.id.dir_obs_accion2);
        dir_realizo_accion2 = findViewById(R.id.dir_realizo_accion2);
        dir_rep = findViewById(R.id.dir_rep);
        dir_card_rep = findViewById(R.id.dir_card_rep);
        dir_monto_rep = findViewById(R.id.dir_monto_rep);
        dir_obs_rep = findViewById(R.id.dir_obs_rep);
        dir_seg = findViewById(R.id.dir_seg);
        dir_card_seg = findViewById(R.id.dir_card_seg);
        dir_fecha_denun = findViewById(R.id.dir_fecha_denun);
        dir_monto_denun = findViewById(R.id.dir_monto_denun);
        dir_fecha_cierre = findViewById(R.id.dir_fecha_cierre);
        dir_monto_rec = findViewById(R.id.dir_monto_rec);
        dir_obs_seg = findViewById(R.id.dir_obs_seg);
        dir_realizo_seg = findViewById(R.id.dir_realizo_seg);
        dir_combustible = findViewById(R.id.dir_comb);
        dir_card_comb = findViewById(R.id.dir_card_comb);
        dir_consumo = findViewById(R.id.dir_consumo);
        dir_monto_comb = findViewById(R.id.dir_monto_comb);
        dir_aplica_desc = findViewById(R.id.dir_aplica_desc);
        dir_monto_final = findViewById(R.id.dir_monto_final);
        dir_obs_final = findViewById(R.id.dir_obs_final);
        dir_button_fin = findViewById(R.id.dir_button_fin);
        dir_card_fin = findViewById(R.id.dir_card_fin);

//Obtengo datos de otra activity
        Bundle datos = getIntent().getExtras();
        fondo_tarjeta = datos.getInt("color");

        //Cambio color de las tarjetas y oculto hasta decidir cuales se usan
        dir_card_titulo.setBackgroundColor(fondo_tarjeta);
        dir_card_titulo.setAlpha(0.95f);
        dir_card_nov.setCardBackgroundColor(fondo_tarjeta);
        dir_card_nov.setAlpha(0.8f);
        dir_card_accion1.setCardBackgroundColor(fondo_tarjeta);
        dir_card_accion1.setAlpha(0.8f);
        dir_card_accion2.setCardBackgroundColor(fondo_tarjeta);
        dir_card_accion2.setAlpha(0.8f);
        dir_accion2.setVisibility(View.GONE);
        dir_card_rep.setCardBackgroundColor(fondo_tarjeta);
        dir_card_rep.setAlpha(0.8f);
        dir_rep.setVisibility(View.GONE);
        dir_card_seg.setCardBackgroundColor(fondo_tarjeta);
        dir_card_seg.setAlpha(0.8f);
        dir_seg.setVisibility(View.GONE);
        dir_card_comb.setCardBackgroundColor(fondo_tarjeta);
        dir_card_comb.setAlpha(0.8f);
        dir_combustible.setVisibility(View.GONE);
        dir_card_fin.setBackgroundColor(fondo_tarjeta);
        dir_card_fin.setAlpha(0.9f);
        valorDeId = datos.getString("id");

//Llamada a función que carga datos desde SQL
        cargaDatosAcciones(valorDeId);

    }


    //Función que carga los datos desde la página PHP
    private void cargaDatosAcciones(String consulta) {

        final String url = "http://192.168.5.199/novedades_nb_cards.php?consulta=id&filtro=acciones&id=" + consulta;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    Log.d("Tag2", "Llegué hasta acá1");
                    JSONObject jsonObject = new JSONObject(response);

                    Log.d("Tag2", "response: " + response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Name");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
//Obetnción de datos solicitados
                    String novedad = jsonObject1.getString("novedad");
                    String fechaNov = jsonObject1.getString("fecha");
                    String severidad = jsonObject1.getString("severidad");
                    String obsNovedad = jsonObject1.getString("observaciones");
                    String odometro = jsonObject1.getString("odometro");
                    String numNov = jsonObject1.getString("id");
                    String chofer = jsonObject1.getString("chofer");
                    String unidad = jsonObject1.getString("unidad");
                    String accion1 = jsonObject1.getString("accion");
                    String fechaAccion1 = jsonObject1.getString("fecha_accion");
                    String obsAccion1 = jsonObject1.getString("obs_accion");
                    String realizo1 = jsonObject1.getString("realizo");
                    String accion2 = jsonObject1.getString("accion2");
                    String obsAccion2 = jsonObject1.getString("obs_accion2");
                    String fechaAccion2 = jsonObject1.getString("fecha_accion2");
                    String realizo2 = jsonObject1.getString("realizo2");
                    String aplicaDescuento = jsonObject1.getString("aplica_descuento");
                    String consumo = jsonObject1.getString("consumo");
                    String montoDesc = jsonObject1.getString("monto_descuento");
                    String obsDesc = jsonObject1.getString("obs_descuento");
                    String aplicaSeguro = jsonObject1.getString("aplica_seguro");
                    String fechaDenun = jsonObject1.getString("fecha_denuncia");
                    String montoDenun = jsonObject1.getString("monto_denuncia");
                    String montoRecu = jsonObject1.getString("monto_recuperado");
                    String fechaCierre = jsonObject1.getString("cierre_seguro");
                    String obsSeg = jsonObject1.getString("obs_seg");
                    String realizoSeg = jsonObject1.getString("realizo_seg");

                    getSupportActionBar().setTitle("Novedad N° " + numNov);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(fondo_tarjeta));

//Seteo los textos a las vistas
                    dir_novedad.setText(novedad);
                    dir_fecha_nov.setText("Fecha: " + fechaNov);
                    dir_sev.setText("Severidad: " + severidad);

                    if (obsNovedad.equals("")) {
                        dir_obs_nov.setText("Sin observaciones");
                    } else {
                        dir_obs_nov.setText(obsNovedad);
                    }
                    if (odometro.equals("null")) {
                        dir_odometro.setVisibility(View.GONE);
                    } else {
                        dir_odometro.setText("Odometro: " + odometro);
                    }

                    dir_chofer.setText("Chofer: " + chofer);
                    if (unidad.equals("")) {
                        dir_unidad.setVisibility(View.GONE);
                    } else {
                        dir_unidad.setText("Unidad: " + unidad);
                    }
                    dir_accion1_txt.setText("Acción tomada: " + accion1);
                    dir_fecha_accion1.setText("Fecha: " + fechaAccion1);
                    if (obsAccion1.equals("")) {
                        dir_obs_accion1.setText("Sin observaciones");
                    } else {
                        dir_obs_accion1.setText(obsAccion1);
                    }
                    dir_realizo_accion1.setText("Realizó: " + realizo1);
                    dir_accion2_txt.setText("Accíon secundaria:  " + accion2);
                    dir_fecha_accion2.setText("Fecha: " + fechaAccion2);
                    if (obsAccion2.equals("")) {
                        dir_obs_accion2.setText("Sin observaciones");
                    } else {
                        dir_obs_accion2.setText(obsAccion2);
                    }
                    dir_realizo_accion2.setText("Realizó: " + realizo2);
//Hago visibles las vistas cuando tienen alguna acción asignada
                    if (aplicaDescuento.equals("2")) {
                        dir_rep.setVisibility(View.VISIBLE);
                        dir_monto_rep.setText("Costo de la reparación: $" + montoDesc + "-");
                        if (obsDesc.equals("")) {
                            dir_obs_rep.setText("Sin observaciones");
                        } else {
                            dir_obs_rep.setText(obsDesc);
                        }
                    } else if (aplicaDescuento.equals("1")) {
                        dir_combustible.setVisibility(View.VISIBLE);
                        dir_consumo.setText("Consumo registrado: " + consumo + " lts/100Km");
                        dir_monto_comb.setText("Costo de excedencia: $" + montoDesc + "-");
                    } else if (aplicaDescuento.equals("3")) {
                        dir_accion2.setVisibility(View.VISIBLE);
                    }

                    if (aplicaSeguro.equals("1")) {
                        dir_seg.setVisibility(View.VISIBLE);
                        dir_fecha_denun.setText("Fecha de denuncia: " + fechaDenun);
                        dir_monto_denun.setText("Monto denunciado: $" + montoDenun + "-");
                        dir_fecha_cierre.setText("Fecha de cierre en seguro: " + fechaCierre);
                        dir_monto_rec.setText("Monto recuperado: $" + montoRecu + "-");
                        dir_obs_seg.setText(obsSeg);
                        if (obsSeg.equals("")) {
                            dir_obs_seg.setText("Sin observaciones");
                        } else {
                            dir_obs_seg.setText(obsSeg);
                        }
                        dir_realizo_seg.setText("Realizó: " + realizoSeg);
                    }

                    if (montoDesc.equals("null")) {

                    } else {
                        dir_monto_final.setText(montoDesc);
                    }


                } catch (JSONException e) {
                    Log.d("TAG2", "Error en Try/Catch");
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("Tag2", "Zona de error unidad");
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

        dir_aplica_desc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    dir_monto_final.setEnabled(true);
                } else {
                    dir_monto_final.setEnabled(false);
                }
            }
        });

        dir_button_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dir_aplica_desc.isChecked()) {
                    aplicaDescuento = "1";
                } else {
                    aplicaDescuento = "0";
                }

                urlfin = "http://192.168.5.199/acciones.php?id=" + valorDeId + "&observaciones=" + dir_obs_final.getText().toString() + "&realizo=" + "&descuento="
                        + aplicaDescuento + "&monto=" + dir_monto_final + "&estado=Cerrada";


                urlfin = urlfin.replace(" ", "+");
                urlfin = urlfin.replace("á", "a");
                urlfin = urlfin.replace("é", "e");
                urlfin = urlfin.replace("í", "i");
                urlfin = urlfin.replace("ó", "o");
                urlfin = urlfin.replace("ú", "u");

                Log.d("Tag2", urlfin);

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, urlfin, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("Tag2", "Valor de url: " + urlfin);
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("success") == 1) {
                                Toast.makeText(direccion.this, "Acción guardada correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(direccion.this, "No se pudo guardar acción", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d("Tag2", "Zona de error btn_realizado");
                        Toast.makeText(direccion.this, "Error al generar consulta", Toast.LENGTH_SHORT).show();
                    }
                });
                int socketTimeout = 30000;
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);
                requestQueue.add(stringRequest);

                direccion.super.onBackPressed();

            }
        });

    }

}
