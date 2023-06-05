package com.quantum.despacho;

import static com.quantum.despacho.LoginActivity.contraseñaGlobal;
import static com.quantum.despacho.LoginActivity.usuarioGlobal;
import static com.quantum.despacho.configuracion.direc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.quantum.despacho.Adaptador.AdapterDatos;
import com.quantum.despacho.Conectividad.Conexion;
import com.quantum.despacho.Parseo.Cuerpo;
import com.quantum.despacho.Parseo.LoginUser;
import com.quantum.despacho.Parseo.Mq0302aDatareq;
import com.quantum.despacho.Parseo.Rowset;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultadosActivity extends AppCompatActivity {

    private TextView user,contraseña,prueba,numero;

    private TextView desposito1, orden1, tipo1,  fecha1, NroCliente1, cliente1, sucursal1;

    ArrayList<String> listDatos;

    RecyclerView recycler;

    public static String depositoGlobal = null;
    public static String ordenGlobal = null;
    public static String tipoGlobal = null;
    public static String fechaGlobal = null;
    public static String NroClienteGlobal = null;
    public static String ClienteGlobal = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        prueba = (TextView) findViewById(R.id.prueba);
        numero = (TextView) findViewById(R.id.prueba);



        recycler= (RecyclerView) findViewById(R.id.recyclerId);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        orden1 = findViewById(R.id.orden1);
        tipo1 = findViewById(R.id.tipo1);
        sucursal1 = findViewById(R.id.sucursal1);
        fecha1 = findViewById(R.id.fecha1);
        NroCliente1= findViewById(R.id.NroCliente1);
        cliente1 = findViewById(R.id.cliente1);
        desposito1 = findViewById(R.id.sucursal1);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //statusBar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.rgb(102,45,145));  //Define color




        Prueba();
    }

    public void Prueba( ){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(direc)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Conexion conexion = retrofit.create(Conexion.class);


        Cuerpo login3 = new Cuerpo(usuarioGlobal, contraseñaGlobal, depositoGlobal);
        Call<LoginUser> call2 = conexion.getUser(login3);
        call2.enqueue(new Callback<LoginUser>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                int statusCode = response.code();

                if(statusCode == 200) {

                    Mq0302aDatareq contactList =  response.body().getMq0302a();

                    listDatos =new ArrayList<>();



                    for(int e = 0; e<contactList.getRowset().size(); e++) {

                        ArrayList<Rowset> rowset1 = (ArrayList<Rowset>) contactList.getRowset();


                        String content1 ="";
                        String content ="";

                        content +=  rowset1.get(e).getCliente() + " | " ;
                        content += rowset1.get(e).getNroCliente() + " | "+ "\n";
                        content1 += rowset1.get(e).getOrden()  ;
                        content +=  rowset1.get(e).getTipo()  + " | ";
                        content += "Fecha: " + rowset1.get(e).getFecha() + "\n\n" ;


                        listDatos.add( content1 );

                        prueba.append(content);

                        AdapterDatos adapter = new AdapterDatos(listDatos);
                        recycler.setAdapter(adapter);

                        adapter.setOnclickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                numero.setText(listDatos.get(recycler.getChildAdapterPosition(view)));


                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(direc)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                Conexion conexion = retrofit.create(Conexion.class);


                                Cuerpo login3 = new Cuerpo(usuarioGlobal, contraseñaGlobal, depositoGlobal);
                                Call<LoginUser> call2 = conexion.getUser(login3);
                                call2.enqueue(new Callback<LoginUser>() {
                                    @Override
                                    public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                                        if(response.body() != null) {

                                            String numeroOrden2 = numero.getText().toString();

                                            for(int e = 0; e<contactList.getRowset().size(); e++) {

                                                ArrayList<Rowset> rowset1 = (ArrayList<Rowset>) contactList.getRowset();
                                                String cliente = rowset1.get(e).getCliente();
                                                String nroCliente = rowset1.get(e).getNroCliente();
                                                String orden = rowset1.get(e).getOrden();
                                                String tipo = rowset1.get(e).getTipo();
                                                String fecha = rowset1.get(e).getFecha();
                                                String deposito= rowset1.get(e).getDeposito();


                                                if (rowset1.get(e).getOrden().equals(numeroOrden2)){
                                                    tipo1.setText(tipo );
                                                    fecha1.setText(fecha);
                                                    cliente1.setText(cliente);
                                                    orden1.setText(orden);
                                                    NroCliente1.setText(nroCliente);
                                                    desposito1.setText(deposito);


                                                    tipoGlobal = tipo1.getText().toString();
                                                    fechaGlobal  = fecha1.getText().toString();
                                                    ClienteGlobal = cliente1.getText().toString();
                                                    ordenGlobal = orden1.getText().toString();
                                                    NroClienteGlobal = NroCliente1.getText().toString();
                                                    depositoGlobal = desposito1.getText().toString();

                                                }
                                                if(rowset1.get(e).getOrden().equals(numeroOrden2)){


                                                    Intent i = new Intent(ResultadosActivity.this, LectorActivity.class);
                                                    i.putExtra("dato", NroCliente1.getText().toString());
                                                    i.putExtra("dato2", tipo1.getText().toString());
                                                    i.putExtra("dato3", cliente1.getText());
                                                    i.putExtra("dato4", orden1.getText().toString());


                                                    startActivity(i);



                                                }
                                            }

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<LoginUser> call, Throwable t) {
                                        Toast.makeText(ResultadosActivity.this, "error", Toast.LENGTH_LONG).show();

                                    }
                                });


                                tipo1.setText("" );
                                fecha1.setText("");
                                cliente1.setText("");
                                orden1.setText("");
                                NroCliente1.setText("");
                                desposito1.setText("");


                            }
                        });
                    }

                }else {
                    Toast.makeText(ResultadosActivity.this, "ERROR " + statusCode, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<LoginUser> call, Throwable t) {
                Toast.makeText(ResultadosActivity.this, "ERROR " , Toast.LENGTH_LONG).show();


            }


        });


    }



    public void Siguiente (View view){
        Intent siguiente = new Intent(this, LoginActivity.class);
        startActivity(siguiente);
    }

    public void Salir(View v){
        new AlertDialog.Builder(this)
                .setTitle("¿Realmente desea cerrar la aplicación?")
                .setCancelable(false)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {// un listener que al pulsar, cierre la aplicacion
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //android.os.Process.killProcess(android.os.Process.myPid()); //Su funcion es algo similar a lo que se llama cuando se presiona el botón "Forzar Detención" o "Administrar aplicaciones", lo cuál mata la aplicación
                        finishAffinity();;
                    }
                }).show();
    }
}

