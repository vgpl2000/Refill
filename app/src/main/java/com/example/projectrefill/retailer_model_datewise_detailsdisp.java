package com.example.projectrefill;

public class retailer_model_datewise_detailsdisp {
    String name,price,quan,weight,total;

    public retailer_model_datewise_detailsdisp() {
    }

    public retailer_model_datewise_detailsdisp(String name, String price, String quan, String weight,String total) {
        this.name = name;
        this.price = price;
        this.quan = quan;
        this.weight = weight;
        this.total=total;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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
