package com.example.basiccode;

public class VisitPayList {

    private String car_num;
    private String entry;
    private String departure;
    private String status;
    private int amount;


    public String getCar_num() {
        return car_num;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public void setCar_num(String car_num) {
        this.car_num = car_num;
    }


    public VisitPayList(String car_num, String entry,String departure,String status,int amount) {
        this.car_num = car_num;
        this.entry=entry;
        this.departure=departure;
        this.status=status;
        this.amount = amount;
    }
    public VisitPayList(String car_num,String status, String entry) {
        this.car_num = car_num;
        this.entry=entry;
        this.status=status;
    }
}
