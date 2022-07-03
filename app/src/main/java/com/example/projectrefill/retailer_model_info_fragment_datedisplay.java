package com.example.projectrefill;

public class retailer_model_info_fragment_datedisplay {
    String date,Pmode;

    public retailer_model_info_fragment_datedisplay() {
    }

    public retailer_model_info_fragment_datedisplay(String date,String Pmode) {
        this.Pmode=Pmode;
        this.date = date;

    }

    public String getPmode() {
        return Pmode;
    }

    public void setPmode(String pmode) {
        Pmode = pmode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}


