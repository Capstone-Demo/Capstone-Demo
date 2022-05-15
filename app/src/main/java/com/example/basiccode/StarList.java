package com.example.basiccode;

public class StarList {

    private String college_name; //주차장명
    private int date_accept;  //예약가능자리수

    public String getCollege_name() {
        return college_name;
    }

    public void setCollege_name(String college_name) {
        this.college_name = college_name;
    }

    public int getDate_accept() {
        return date_accept;
    }

    public void setDate_accept(int date_accept) {
        this.date_accept = date_accept;
    }

    StarList(String college_name, int date_accept){
        this.college_name = college_name;
        this.date_accept = date_accept;
    }
}
