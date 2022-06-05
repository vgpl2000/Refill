package com.example.projectrefill;

public class client_model_todisplayitemsavailable {
   String price,quan,weight,name,url;


    public client_model_todisplayitemsavailable(String name,String price, String quan, String url, String weight) {
        this.name = name;
        this.price = price;
        this.quan = quan;
        this.url = url;
        this.weight = weight;
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
