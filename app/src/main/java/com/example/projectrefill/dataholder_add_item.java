package com.example.projectrefill;

public class dataholder_add_item {


    Integer price,quan,weight;


    public dataholder_add_item(Integer price, Integer quan, Integer weight) {
        this.price = price;
        this.quan = quan;
        this.weight = weight;

    }



    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuan() {
        return quan;
    }

    public void setQuan(Integer quan) {
        this.quan = quan;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
