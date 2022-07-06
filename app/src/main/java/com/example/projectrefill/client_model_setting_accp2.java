package com.example.projectrefill;

public class client_model_setting_accp2 {
    String date,pmode;

    public client_model_setting_accp2() {
    }

    public client_model_setting_accp2(String date,String pmode) {
        this.date = date;
        this.pmode=pmode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPmode() {
        return pmode;
    }

    public void setPmode(String pmode) {
        this.pmode = pmode;
    }
}
