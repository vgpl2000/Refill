package com.example.projectrefill;

public class client_model_todisplaytransactionswhenbuttonpressed {
    String date,name,quan;


    public client_model_todisplaytransactionswhenbuttonpressed(String date, String name, String quan) {
        this.date = date;
        this.name = name;
        this.quan = quan;
    }

    public client_model_todisplaytransactionswhenbuttonpressed() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }
}
