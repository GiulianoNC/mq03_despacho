package com.quantum.despacho.Conectividad;



import com.quantum.despacho.Parseo.Cuerpo;
import com.quantum.despacho.Parseo.LoginUser;
import com.quantum.despacho.Parseo.Mq0302aDatareq;
import com.quantum.despacho.Parseo.Parametros;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Conexion {
    @POST("jderest/v3/orchestrator/MQ0302A_ORCH")
    Call <LoginUser> getUser(@Body Cuerpo users );

    @POST("jderest/v3/orchestrator/MQ0302A_ORCH")
    Call<Mq0302aDatareq> getData(@Body Mq0302aDatareq users );


    @POST("jderest/v3/orchestrator/MQ0302A_ORCH")
    Call<Cuerpo> getData(@Body Cuerpo users );


    @POST("jderest/v3/orchestrator/MQ0301A_ORCH")
    Call<Parametros> getParametros(@Body Parametros parametros);

}