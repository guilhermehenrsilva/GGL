package br.edu.ifsuldeminas.mch.GGL.model;

import java.io.Serializable;

public class FuelRegister implements Serializable {

    private Integer id;
    private Double fuelQty;
    private Double fuelPrice;

    public FuelRegister() {}

    public FuelRegister(int id, Double fuelQty, Double fuelPrice) {
        this.id = id;
        this.setFuelQty(fuelQty);
        this.setFuelPrice(fuelPrice);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getFuelQty() {
        return fuelQty;
    }

    public void setFuelQty(Double fuelQty) {
        this.fuelQty = fuelQty;
    }

    public Double getFuelPrice() {
        return fuelPrice;
    }

    public void setFuelPrice(Double fuelPrice) {
        this.fuelPrice = fuelPrice;
    }

    @Override
    public String toString() {
        return "Quantidade de combustível: " + this.getFuelQty().toString() +
                "Preço: " + this.getFuelPrice().toString();
    }
}
