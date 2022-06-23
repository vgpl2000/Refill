package com.example.projectrefill;

public class retailer_model_home_recycler_itemdisplaying {
    String name,price,url,weight;

    public retailer_model_home_recycler_itemdisplaying(String name, String price, String url, String weight) {
        this.name = name;
        this.price = price;
        this.url = url;
        this.weight = weight;
    }

    public retailer_model_home_recycler_itemdisplaying() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
