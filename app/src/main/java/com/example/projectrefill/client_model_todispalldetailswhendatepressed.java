package com.example.projectrefill;

public class client_model_todispalldetailswhendatepressed {
    String Pmode,date,nameofretailer;

    public client_model_todispalldetailswhendatepressed(String pmode, String date, String nameofretailer) {
        Pmode = pmode;
        this.date = date;
        this.nameofretailer = nameofretailer;
    }

    public client_model_todispalldetailswhendatepressed() {
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

    public String getNameofretailer() {
        return nameofretailer;
    }

    public void setNameofretailer(String nameofretailer) {
        this.nameofretailer = nameofretailer;
    }
}
