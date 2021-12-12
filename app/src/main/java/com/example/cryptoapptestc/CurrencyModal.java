package com.example.cryptoapptestc;


import java.io.Serializable;

public class CurrencyModal implements Serializable {
    // variable for currency name,
    // currency symbol and price.
    private String name;
    private String symbol;
    private double price;

    //empty constructor for firestore
    public CurrencyModal() {

    }

    public CurrencyModal(String name, String symbol, double price) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
    }

    //getter and setter methods for all variables
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
