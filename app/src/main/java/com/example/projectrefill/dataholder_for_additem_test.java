package com.example.projectrefill;

public class dataholder_for_additem_test {
   String price,qty,weight;
    String name,url;

    public dataholder_for_additem_test(String price, String qty, String weight, String name, String url) {
        this.price = price;
        this.qty = qty;
        this.weight = weight;
        this.name = name;
        this.url = url;
    }

    public dataholder_for_additem_test() {
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
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
