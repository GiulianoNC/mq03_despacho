package com.quantum.despacho.Parseo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Datareq {

    @SerializedName("MQ0302A_DATAREQ")
    @Expose
    private Mq0302aDatareq mq0302aDatareq;
    @SerializedName("jde__status")
    @Expose
    private String jdeStatus;
    @SerializedName("jde__startTimestamp")
    @Expose
    private String jdeStartTimestamp;
    @SerializedName("jde__endTimestamp")
    @Expose
    private String jdeEndTimestamp;
    @SerializedName("jde__serverExecutionSeconds")
    @Expose
    private Double jdeServerExecutionSeconds;



    public Mq0302aDatareq getMq0302aDatareq() {
        return mq0302aDatareq;
    }

    public void setMq0302aDatareq(Mq0302aDatareq mq0302aDatareq) {
        this.mq0302aDatareq = mq0302aDatareq;
    }

    public String getJdeStatus() {
        return jdeStatus;
    }

    public void setJdeStatus(String jdeStatus) {
        this.jdeStatus = jdeStatus;
    }

    public String getJdeStartTimestamp() {
        return jdeStartTimestamp;
    }

    public void setJdeStartTimestamp(String jdeStartTimestamp) {
        this.jdeStartTimestamp = jdeStartTimestamp;
    }

    public String getJdeEndTimestamp() {
        return jdeEndTimestamp;
    }

    public void setJdeEndTimestamp(String jdeEndTimestamp) {
        this.jdeEndTimestamp = jdeEndTimestamp;
    }

    public Double getJdeServerExecutionSeconds() {
        return jdeServerExecutionSeconds;
    }

    public void setJdeServerExecutionSeconds(Double jdeServerExecutionSeconds) {
        this.jdeServerExecutionSeconds = jdeServerExecutionSeconds;
    }

}