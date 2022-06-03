package com.example.projectrefill;

public class dataholder_add_item {

    String price,quan,weight;

    public dataholder_add_item(String price, String quan, String weight) {
        this.price = price;
        this.quan = quan;
        this.weight = weight;
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
