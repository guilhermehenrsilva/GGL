package br.edu.ifsuldeminas.mch.GGL.model;

import java.io.Serializable;

public class GasolinePrice {
    private String country;
    private String price;
    private String currency;

    // Getters e Setters
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
