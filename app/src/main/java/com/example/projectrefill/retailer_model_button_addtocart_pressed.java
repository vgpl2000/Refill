package com.example.projectrefill;

public class retailer_model_button_addtocart_pressed {
    String name,quan,weight,date,price;

    public retailer_model_button_addtocart_pressed() {
    }

    public retailer_model_button_addtocart_pressed(String name, String quan, String weight, String date,String price) {
        this.name = name;
        this.quan = quan;
        this.weight = weight;
        this.date = date;
        this.price=price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
