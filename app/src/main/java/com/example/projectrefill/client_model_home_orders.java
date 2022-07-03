package com.example.projectrefill;



public class client_model_home_orders {
    String order_state;
    String name;
    String pmode;


    public client_model_home_orders() {

    }

    public String getOrder_state() {
        return order_state;
    }

    public client_model_home_orders(String order_state, String name, String pmode) {
        this.order_state = order_state;
        this.name = name;
        this.pmode = pmode;
    }

    public String getPmode() {
        return pmode;
    }

    public void setPmode(String pmode) {
        this.pmode = pmode;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public client_model_home_orders(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
