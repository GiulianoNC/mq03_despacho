package com.quantum.despacho;

import static com.quantum.despacho.LoginActivity.contraseñaGlobal;
import static com.quantum.despacho.LoginActivity.usuarioGlobal;
import static com.quantum.despacho.ResultadosActivity.ClienteGlobal;
import static com.quantum.despacho.ResultadosActivity.NroClienteGlobal;
import static com.quantum.despacho.ResultadosActivity.ordenGlobal;
import static com.quantum.despacho.ResultadosActivity.tipoGlobal;
import static com.quantum.despacho.configuracion.checkGlobalLector;
import static com.quantum.despacho.configuracion.direc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.quantum.despacho.Conectividad.Conexion;
import com.quantum.despacho.Parseo.Parametros;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LectorActivity extends AppCompatActivity {

    private TextView NroCliente1,cliente1,tipo1,nroPedido1,codigolote,item,ubicacion,informeConexion,qrInfo;

    //prueba
    private TextView cantidad;
    Button qr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lector);


        NroCliente1 = (TextView) findViewById(R.id.textView26);
        cliente1 = (TextView) findViewById(R.id.cliente);
        tipo1 = (TextView) findViewById(R.id.tipo);
        nroPedido1 = (TextView) findViewById(R.id.nroPedido);
        informeConexion = (TextView) findViewById(R.id.informeConexion1);
        ubicacion= (EditText) findViewById(R.id.ubicacion1);
        codigolote= (EditText) findViewById(R.id.codigoLote);
        cantidad= (EditText) findViewById(R.id.cantidad);


        //qr
        qr = findViewById(R.id.qrImage);
        qrInfo = findViewById(R.id.qrInfo);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //statusBar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.rgb(102,45,145));  //Define color


        //obtengo datos del primer ACTIVITY
        NroCliente1.setText(NroClienteGlobal);

        tipo1.setText(tipoGlobal);

        cliente1.setText(ClienteGlobal);

        nroPedido1.setText(ordenGlobal);

        if(checkGlobalLector == true){
            qr.setVisibility(View.VISIBLE);
        }else{
            qr.setVisibility(View.INVISIBLE);
        }

    }

    public void scan(View v){
        IntentIntegrator intentIntegrator = new IntentIntegrator(LectorActivity.this);
        //tipo de lector
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        //que va a decir el lector
        intentIntegrator.setPrompt("Lector - Código");
        //que camara usa, en este caso la 0 es la de atras
        intentIntegrator.setCameraId(0);
        //dispositivos, alertas de sonido
        intentIntegrator.setBeepEnabled(true);
        //para leer correctamente
        intentIntegrator.setBarcodeImageEnabled(true);
        //inicia el elemento de scaneo
        intentIntegrator.initiateScan();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //recibir el resultado de los parametros de arriba
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null  ){
            if(result.getContents() == null ){
                Toast.makeText(this, "Lectura cancelada", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();

                if(codigolote.equals("")){
                    codigolote.setText("");
                }else{
                    codigolote.setText(result.getContents());
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void Siguiente (View view){
        Intent e = new Intent(this, ResultadosActivity.class);
        startActivity(e);
    }

    public void Grabar2(View view){


        if (codigolote.length() == 0 ) {
            Toast.makeText(this, "Debes ingresar un codigo|lote ", Toast.LENGTH_LONG).show();
        }else{
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(direc)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Conexion conexion = retrofit.create(Conexion.class);

        String codigolote1 = codigolote.getText().toString();
        if (codigolote1.length() != 0){
            String string = codigolote1;
            String[] parts = string.split("-");
            String codigo = parts[0];
            String lote = parts[1];

            String ubicacion1 = ubicacion.getText().toString();
            String cantidad1 = cantidad.getText().toString();


            Parametros param = new Parametros(usuarioGlobal, contraseñaGlobal, tipoGlobal ,ordenGlobal, lote, codigo,ubicacion1,cantidad1);

        Call<Parametros> call = conexion.getParametros(param);
        call.enqueue(new Callback<Parametros>() {
            @Override
            public void onResponse(Call<Parametros> call, Response<Parametros> response) {
                int statusCode = response.code();
                if (statusCode == 200){
                    Parametros data =  response.body();
                    int contador = data.getContador();

                    if (contador == 0){

                        AlertDialog.Builder builder = new AlertDialog.Builder(LectorActivity.this);
                        builder.setTitle("¡Verificar producto seleccionado!");
                        builder.setMessage("Producto no despachado");
                        builder.setPositiveButton("cerrar", null);

                        AlertDialog dialog = builder.create();
                        dialog.show();
                        Toast.makeText(LectorActivity.this, "contador " + contador , Toast.LENGTH_LONG).show();




                    }
                    if (contador == 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(LectorActivity.this);
                        builder.setTitle("¡Producto confirmado!");
                        builder.setPositiveButton("Aceptar", null);

                        AlertDialog dialog = builder.create();
                        dialog.show();
                        Toast.makeText(LectorActivity.this, "contador " + contador , Toast.LENGTH_LONG).show();

                        ubicacion.setText("");
                        cantidad.setText("");
                        codigolote.setText("");



                    }
                }else{
                    Toast.makeText(LectorActivity.this, "error " , Toast.LENGTH_LONG).show();

                }




            }

            @Override
            public void onFailure(Call<Parametros> call, Throwable t) {
                Toast.makeText(LectorActivity.this, "Error de conexion " , Toast.LENGTH_LONG).show();

            }
        });

    }

    }
    }
}