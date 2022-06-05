package com.example.projectrefill;

public class client_model_todisplayitemsavailable {
   String price,quan,weight,name,url;


    public client_model_todisplayitemsavailable(String price, String quan, String weight, String name, String url) {
        this.price = price;
        this.quan = quan;
        this.weight = weight;
        this.name = name;
        this.url = url;
    }

    public client_model_todisplayitemsavailable() {
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
