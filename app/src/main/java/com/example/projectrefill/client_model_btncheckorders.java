package com.example.projectrefill;

public class client_model_btncheckorders {
    String date,nm;
    Integer quan;

    public client_model_btncheckorders() {

    }

    public client_model_btncheckorders(String date, String nm, Integer quan) {
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

    public Integer getQuan() {
        return quan;
    }

    public void setQuan(Integer quan) {
        this.quan = quan;
    }
}
