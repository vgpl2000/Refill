package com.example.projectrefill;

import android.net.Uri;

public class dataholder_add_item {


    Integer price,quan,weight;
   String url;


    public dataholder_add_item(Integer price, Integer quan, Integer weight,String url) {
        this.price = price;
        this.quan = quan;
        this.weight = weight;
        this.url=url;

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
