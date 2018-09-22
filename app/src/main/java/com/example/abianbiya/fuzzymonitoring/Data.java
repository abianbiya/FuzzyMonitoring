package com.example.abianbiya.fuzzymonitoring;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("suhu")
    @Expose
    private String suhu;
    @SerializedName("ph")
    @Expose
    private String ph;
    @SerializedName("hasil")
    @Expose
    private String hasil;
    @SerializedName("kesimpulan")
    @Expose
    private String kesimpulan;
    @SerializedName("ip")
    @Expose
    private String ip;
    @SerializedName("user_agent")
    @Expose
    private String userAgent;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("tgl")
    @Expose
    private String tgl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSuhu() {
        return suhu;
    }

    public void setSuhu(String suhu) {
        this.suhu = suhu;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getHasil() {
        return hasil;
    }

    public void setHasil(String hasil) {
        this.hasil = hasil;
    }

    public String getKesimpulan() {
        return kesimpulan;
    }

    public void setKesimpulan(String kesimpulan) {
        this.kesimpulan = kesimpulan;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

}