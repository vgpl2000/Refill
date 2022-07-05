package com.example.projectrefill;

public class client_model_todisplaytransactionswhenbuttonpressed {
    String date,Pmode;


    public client_model_todisplaytransactionswhenbuttonpressed(String date, String Pmode) {
        this.date = date;
        this.Pmode = Pmode;

    }

    public client_model_todisplaytransactionswhenbuttonpressed() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPmode() {
        return Pmode;
    }

    public void setPmode(String pmode) {
        Pmode = pmode;
    }
}
