package com.example.projectrefill;

public class retailer_model_placeorder_pressed {
    String name,price,quan,weight;

    public retailer_model_placeorder_pressed() {
    }

    public retailer_model_placeorder_pressed(String name, String price, String quan, String weight) {
        this.name = name;
        this.price = price;
        this.quan = quan;
        this.weight = weight;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
