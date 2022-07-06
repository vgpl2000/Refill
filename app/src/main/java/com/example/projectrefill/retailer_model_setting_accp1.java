package com.example.projectrefill;

public class retailer_model_setting_accp1 {
    String date,pmode;

    public retailer_model_setting_accp1() {
    }

    public retailer_model_setting_accp1(String date,String pmode) {
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
