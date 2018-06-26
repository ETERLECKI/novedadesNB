package ar.com.nbcargo.nbcargo_novedades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;


public class agregarNovedad extends AppCompatActivity implements AdapterView.OnItemClickListener, OnItemSelectedListener {

    Spinner spinner;
    AutoCompleteTextView autoChoferApe;
    AutoCompleteTextView autoUnidad;
    TextView consumo;
    TextView monto;
    String URLarea = "http://192.168.5.199/spinnersanovedad.php?filtro=area";
    String URLchofer = "http://192.168.5.199/qchoferes.php";
    String URLunidad = "http://192.168.5.199/qunidades.php";
    ArrayList<String> itemsOrigen;
    ArrayList<String> itemsNovedad;
    ArrayList<String> itemsChofer;
    ArrayList<String> itemsUnidad;
    EditText editObs;
    Button aceptar;
    EditText odometro;
    private Spinner cmbNovedades;
    private Spinner cmbSeveridad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_agregar_novedad);


        itemsOrigen = new ArrayList<>();
        itemsNovedad = new ArrayList<>();
        itemsChofer = new ArrayList<>();
        itemsUnidad = new ArrayList<>();

        aceptar = findViewById(R.id.btn_acp);
        spinner = findViewById(R.id.spinner_origen);
        cmbNovedades = findViewById(R.id.spinner_novedad);
        editObs = findViewById(R.id.edit_obs);
        autoChoferApe = findViewById(R.id.auto_chofer);
        autoUnidad = findViewById(R.id.auto_unidad);
        consumo = findViewById(R.id.edit_consumo);
        monto = findViewById(R.id.edit_monto);
        odometro = findViewById(R.id.edit_odometro);
        autoChoferApe.setThreshold(1);
        autoUnidad.setThreshold(1);
        getSupportActionBar().hide();

        //Oculto a_consumo y monto
        consumo.setVisibility(View.GONE);
        monto.setVisibility(View.GONE);
        odometro.setVisibility(View.GONE);
        autoUnidad.setVisibility(View.GONE);

        autoChoferApe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                loadchofer(URLchofer, autoChoferApe.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        loadSpinnerData(URLarea, "area");
        loadchofer(URLchofer, "");
        Loadunidad(URLunidad);

        autoChoferApe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String filtroChofer = autoChoferApe.getText().toString();
                loadchofer(URLchofer, filtroChofer);
            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String URLagreganov = "http://192.168.5.199/transporte1.php?origen=" + spinner.getSelectedItem().toString() + "&chofer=" + autoChoferApe.getText().toString() + "&novedad=" + cmbNovedades.getSelectedItem().toString() + "&severidad=" + cmbSeveridad.getSelectedItem().toString() + "&observaciones=" + editObs.getText().toString() + "&estado=Abierta";
                //URLagreganov.getBytes("ISO-8859-1","UTF-8");
                if (spinner.getSelectedItem().toString().equals("Taller") || spinner.getSelectedItem().toString().equals("Patrimonial") || spinner.getSelectedItem().toString().equals("Conformes")) {
                    URLagreganov = URLagreganov + "&unidad=" + autoUnidad.getText().toString();
                }

                if (cmbNovedades.getSelectedItem().toString().equals("Exceso consumo combustible")) {
                    URLagreganov = URLagreganov + "&consumo=" + consumo.getText().toString();
                    URLagreganov = URLagreganov + "&monto_descuento=" + monto.getText().toString();
                    URLagreganov = URLagreganov + "&descuento=1";
                    URLagreganov = URLagreganov + "&odometro=" + odometro.getText().toString();
                } else if (cmbNovedades.getSelectedItem().toString().equals("Roturas")) {
                    URLagreganov = URLagreganov + "&monto_descuento=" + monto.getText().toString();
                    URLagreganov = URLagreganov + "&descuento=2";
                    URLagreganov = URLagreganov + "&odometro=" + odometro.getText().toString();
                } else if (cmbNovedades.getSelectedItem().toString().equals("Accidentes")) {
                    URLagreganov = URLagreganov + "&odometro=" + odometro.getText().toString();
                }
                URLagreganov = URLagreganov.replace(" ", "+");
                URLagreganov = URLagreganov.replace("á", "a");
                URLagreganov = URLagreganov.replace("é", "e");
                URLagreganov = URLagreganov.replace("í", "i");
                URLagreganov = URLagreganov.replace("ó", "o");
                URLagreganov = URLagreganov.replace("ú", "u");
                Log.d("Tag2", "URL: " + URLagreganov);
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URLagreganov, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("success") == 1) {
                                Toast.makeText(getApplicationContext(), "Se generó novedad correctamente", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "No se pudo generar novedad", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d("TAG", "Zona de error agregar");
                    }
                });
                int socketTimeout = 30000;
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);
                requestQueue.add(stringRequest);

                Toast.makeText(getApplicationContext(), "Se generó novedad correctamente " + autoChoferApe.getText().toString(), Toast.LENGTH_LONG).show();
                agregarNovedad.super.onBackPressed();
            }
        });

        cmbNovedades.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String novedad = cmbNovedades.getItemAtPosition(cmbNovedades.getSelectedItemPosition()).toString();

                if (spinner.getSelectedItem().toString().equals("Taller") || spinner.getSelectedItem().toString().equals("Patrimonial")) {
                    autoUnidad.setVisibility(View.VISIBLE);
                } else {
                    autoUnidad.setVisibility(View.GONE);
                }
                if (novedad.equals("Exceso consumo combustible")) {
                    monto.setVisibility(View.VISIBLE);
                    consumo.setVisibility(View.VISIBLE);
                    odometro.setVisibility(View.VISIBLE);
                    monto.setHint("Monto de la excedencia");
                } else if (novedad.equals("Roturas")) {
                    monto.setVisibility(View.VISIBLE);
                    odometro.setVisibility(View.VISIBLE);
                    consumo.setVisibility(View.GONE);
                    monto.setHint("Monto de la reparación");
                } else if (novedad.equals("Accidentes")) {
                    odometro.setVisibility(View.VISIBLE);
                } else {
                    monto.setVisibility(View.GONE);
                    consumo.setVisibility(View.GONE);
                    odometro.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String country = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                URLarea = "http://192.168.5.199/spinnersanovedad.php?filtro=" + country;
                loadSpinnerData(URLarea, "novedad");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cmbSeveridad = findViewById(R.id.spinner_severidad);


        final String[] datos_severidad =
                new String[]{"Leve", "Grave"};


        //Combo Severidad
        ArrayAdapter<String> adaptador_severidad =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, datos_severidad);


        adaptador_severidad.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        cmbSeveridad.setAdapter(adaptador_severidad);

    }

    private void AgregarDAtos() {


    }

    private void Loadunidad(String urLunidad) {


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urLunidad, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("TAG", "Entra a unidad");
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("success") == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("Unidad");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String unidad = jsonObject1.getString("Patente");
                            itemsUnidad.add(unidad);
                        }
                    }
                    autoUnidad.setAdapter(new ArrayAdapter<String>(agregarNovedad.this, android.R.layout.simple_list_item_1, itemsUnidad));
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

    private void loadchofer(String URLchofer, final String filtro) {


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLchofer + "?chofer=" + filtro, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("success") == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("Chofer");
                        itemsChofer.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String chofer1 = jsonObject1.getString("nombre");
                            itemsChofer.add(chofer1);
                        }
                    }

                    autoChoferApe.setAdapter(new ArrayAdapter<String>(agregarNovedad.this, android.R.layout.simple_list_item_1, itemsChofer));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("TAG", "Zona de error chofer");
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private void loadSpinnerData(String url, final String filtrado) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (filtrado == "area") {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getInt("success") == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("Name");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String country = jsonObject1.getString("origen");
                                itemsOrigen.add(country);
                            }
                        }

                        spinner.setAdapter(new ArrayAdapter<String>(agregarNovedad.this, android.R.layout.simple_spinner_dropdown_item, itemsOrigen));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    itemsNovedad.clear();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getInt("success") == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("Name");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String country = jsonObject1.getString("novedad");
                                itemsNovedad.add(country);
                            }
                        }
                        cmbNovedades.setAdapter(new ArrayAdapter<String>(agregarNovedad.this, android.R.layout.simple_spinner_dropdown_item, itemsNovedad));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("Tag2", "Zona de error");
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

}



