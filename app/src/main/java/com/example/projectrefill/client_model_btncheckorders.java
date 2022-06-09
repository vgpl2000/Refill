package com.example.projectrefill;

public class client_model_btncheckorders {
    String date,item,quan;

    public client_model_btncheckorders() {

    }

    public client_model_btncheckorders(String date, String item, String quan) {
        this.date = date;
        this.item = item;
        this.quan = quan;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }
}
