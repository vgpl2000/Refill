package com.example.projectrefill;

public class retailer_model_cart_retailer {
String name,price,quan;

    public retailer_model_cart_retailer() {
    }

    public retailer_model_cart_retailer(String name, String price, String quan) {
        this.name = name;
        this.price = price;
        this.quan = quan;
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

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }
}
