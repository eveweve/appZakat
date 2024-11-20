package com.example.models;

public class Zakat {
    private String zakatType;
    private double amount;
    private String date;

    public Zakat(){}

    public Zakat(String zakatType, Double amount, String date){
        this.zakatType = zakatType;
        this.amount = amount;
        this.date = date;
    }

    public String getZakatType(){return zakatType;}
    public void setZakatType(String zakatType){this.zakatType = zakatType;}

    public double getAmount(){return amount;}
    public void setAmount(double amount){this.amount = amount;}

    public String getDate(){return date;}
    public void setDate(String date){this.date = date;}
}
