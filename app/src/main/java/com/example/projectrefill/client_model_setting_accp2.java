package com.example.projectrefill;

public class client_model_setting_accp2 {
    String date,pmode,retname;

    public client_model_setting_accp2() {
    }

    public client_model_setting_accp2(String date,String pmode,String retname) {
        this.date = date;
        this.pmode=pmode;
        this.retname=retname;
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

    public String getRetname() {
        return retname;
    }

    public void setRetname(String retname) {
        this.retname = retname;
    }
}
