package com.quantum.despacho;

import static com.quantum.despacho.configuracion.depositoGlobal;
import static com.quantum.despacho.configuracion.direc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.quantum.despacho.Conectividad.Conexion;
import com.quantum.despacho.Parseo.Cuerpo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {


    private TextView user, contraseña, informeConexion, urls;

    public static String usuarioGlobal = null;
    public static String contraseñaGlobal = null;

    Switch switcher;
    boolean nightMODE;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //statusBar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.rgb(102,45,145));  //Define color


        user = findViewById(R.id.usuario);
        contraseña = findViewById(R.id.contras);
        //informeConexion= findViewById(R.id.informeConexion);

        //guardar

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        user.setText(preferences.getString("usuario",""));
        contraseña.setText(preferences.getString("password",""));

        urls = findViewById(R.id.dir);


        String direccion = getIntent().getStringExtra("direcciones");
        urls.setText(direccion);


        //Esto es el Day/Night Mode
        //Uso el SharedPreference para guardar el modo cuando salgo de la pagina
        switcher = findViewById(R.id.btnToggleDark);
        sharedPreferences = getSharedPreferences("MODE",Context.MODE_PRIVATE);
        nightMODE = sharedPreferences.getBoolean("night",false); //El modo luz es el default

        if (nightMODE){
            switcher.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nightMODE){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night",false);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night",true);
                }
                editor.apply();
            }
        });

    }



    public void Logearse(View view ) {

        String user2 = user.getText().toString();
        String contraseña2 = contraseña.getText().toString();


        String direccion = getIntent().getStringExtra("direcciones");
        urls.setText(direccion);



        if (urls.length() == 0)  {

            Intent siguiente = new Intent(LoginActivity.this, configuracion.class);
            startActivity(siguiente);
        }else{

            if (user2.length() == 0 && contraseña2.length() == 0) {
                Toast.makeText(this, "Debes ingresar un usuario y contraseña", Toast.LENGTH_LONG).show();
            }
            if (user2.length() != 0 && contraseña2.length() != 0) {

                Toast.makeText(this, "Procesando", Toast.LENGTH_LONG).show();


                usuarioGlobal = user.getText().toString();
                contraseñaGlobal = contraseña.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(direc)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Conexion conexion = retrofit.create(Conexion.class);

                Cuerpo logerse = new Cuerpo(usuarioGlobal,  contraseñaGlobal, depositoGlobal);


                Call<Cuerpo> call1 = conexion.getData(logerse);
                call1.enqueue(new Callback<Cuerpo>() {

                    @Override
                    public void onResponse(Call<Cuerpo> call, Response<Cuerpo> response) {
                        int statusCode = response.code();
                        if (response.isSuccessful()) {
                            Cuerpo cuerpo = response.body();
                            if (statusCode == 200) {
                                Intent siguiente = new Intent(LoginActivity.this, ResultadosActivity.class);
                                startActivity(siguiente);
                            }

                        }
                        else {
                            if (statusCode == 403) {
                                Toast.makeText(LoginActivity.this, "Usuario/Contraseña Incorrecto", Toast.LENGTH_LONG).show();
                            }
                            if (statusCode == 500) {
                                Toast.makeText(LoginActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<Cuerpo> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                        informeConexion.setText(t.getMessage());

                    }
                });

            }
        }

        SharedPreferences preferecias =  getSharedPreferences("datos",Context.MODE_PRIVATE);
        SharedPreferences.Editor Obj_editor = preferecias.edit();
        Obj_editor.putString("usuario", user.getText().toString());
        Obj_editor.putString("password", contraseña.getText().toString());

        Obj_editor.commit();

    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //acciones de menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_privacidad:
                Toast.makeText(this, "Politicas de privacidad", Toast.LENGTH_SHORT).show();

                String url = "https://quantumconsulting.com.ar/politicas-de-privacidad/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
            case R.id.action_configuracion:
                Intent siguiente = new Intent(LoginActivity.this, configuracion.class);
                startActivity(siguiente);
                break;

        }
        return super.onOptionsItemSelected(item);
    }



}


