package com.example.projectrefill;

public class client_model_btncheckorders {
    String date,nm,quan;

    public client_model_btncheckorders() {

    }

    public client_model_btncheckorders(String date, String nm, String quan) {
        this.date = date;
        this.nm = nm;
        this.quan = quan;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }
}
