package com.example.projectrefill;

public class dataholder_for_additem_test {
    Integer price,qty,weight;
    String name,url;

    public dataholder_for_additem_test(Integer price, Integer qty, Integer weight, String name, String url) {
        this.price = price;
        this.qty = qty;
        this.weight = weight;
        this.name = name;
        this.url = url;
    }

    public dataholder_for_additem_test() {
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
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
