package ar.com.nbcargo.nbcargo_novedades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity {

    public SharedPreferences preferencias;
    public SharedPreferences.Editor upreferencias;
    String sesion;
    CheckBox estadoSesion;
    Button btnIngreso;
    EditText usuario;
    EditText password;


    @Override
    protected void onRestart() {
        super.onRestart();
        preferencias = getSharedPreferences("MisPreferencias", getApplicationContext().MODE_PRIVATE);
        sesion = preferencias.getString("sesion", "cerrada");
        upreferencias = preferencias.edit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferencias = getSharedPreferences("MisPreferencias", getApplicationContext().MODE_PRIVATE);
        sesion = preferencias.getString("sesion", "cerrada");
        upreferencias = preferencias.edit();


        estadoSesion = findViewById(R.id.log_chk_sesion);
        btnIngreso = findViewById(R.id.log_btn_in);
        usuario = findViewById(R.id.log_usr);
        password = findViewById(R.id.log_pass);

        btnIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String URLagreganov = "http://192.168.5.199/login.php?usuario=" + usuario.getText().toString() + "&pass=" + password.getText().toString();
                URLagreganov = URLagreganov.replace(" ", "+");
                Log.d("TAG", URLagreganov);
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URLagreganov, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("success") == 1) {
                                Toast.makeText(getApplicationContext(), "Acceso correcto", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(login.this, MainActivity.class));
                                if (estadoSesion.isChecked()) {
                                    upreferencias.putString("sesion", "abierta");
                                    upreferencias.putString("usuario", usuario.getText().toString());
                                    upreferencias.putString("tipo", "1");
                                    upreferencias.commit();
                                    Log.d("TAG", sesion);
                                }
                            } else {
                                Log.d("TAG", "Ingreso incorrecto");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
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

            }
        });


        if (sesion.equals("abierta")) {
            startActivity(new Intent(this, MainActivity.class));
        }


    }
}
