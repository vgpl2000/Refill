package com.example.projectrefill;

public class client_model_ultimatedispofretinfo {
    String name,price,quan,totalamount,weight;

    public client_model_ultimatedispofretinfo() {
    }

    public client_model_ultimatedispofretinfo(String name, String price, String quan, String totalamount, String weight) {
        this.name = name;
        this.price = price;
        this.quan = quan;
        this.totalamount = totalamount;
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

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
