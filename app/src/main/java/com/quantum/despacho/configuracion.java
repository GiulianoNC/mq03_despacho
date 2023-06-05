package com.quantum.despacho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

public class configuracion extends AppCompatActivity {

    private TextView direccion,depo;

    public static String direc = null;
    public static String depositoGlobal = null;

    public static boolean  checkGlobalLector = false;

    private CheckBox  ckbxLector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        direccion= findViewById(R.id.direccion);
        depo=findViewById(R.id.depo);

        SharedPreferences preferences = getSharedPreferences("dato", Context.MODE_PRIVATE);
        direccion.setText(preferences.getString("direcciones",""));
        depo.setText(preferences.getString("deposito",""));


        direc = direccion.getText().toString();
        depositoGlobal=depo.getText().toString();

        ckbxLector = findViewById(R.id.checkBoxLector);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //statusBar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.rgb(102,45,145));  //Define color

    }


    public void guardar (View view){
        SharedPreferences preferecias =  getSharedPreferences("dato",Context.MODE_PRIVATE);
        SharedPreferences.Editor Obj_editor = preferecias.edit();

        Obj_editor.putString("direcciones", direccion.getText().toString());
        Obj_editor.putString("deposito", depo.getText().toString());

        Obj_editor.commit();


        Intent siguiente = new Intent(configuracion.this, LoginActivity.class);

        siguiente.putExtra("direcciones", direccion.getText().toString());

        startActivity(siguiente);

        if (ckbxLector.isChecked()==false){
            checkGlobalLector = false;
        } else{
            checkGlobalLector = true;
        }

    }

}