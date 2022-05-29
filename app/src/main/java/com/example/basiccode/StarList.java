package com.example.basiccode;

public class StarList {

    private String college_name; //주차장명
    private String address;  //주소
    private String detail_address; //상세주소
    private String enabled; //사용가능여부
    private int date_accept;  //예약가능자리수
    private int total_quantity; //전체자리수

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetail_address() {
        return detail_address;
    }

    public void setDetail_address(String detail_address) {
        this.detail_address = detail_address;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public int getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(int total_quantity) {
        this.total_quantity = total_quantity;
    }

    StarList(String college_name, String address, String detail_address, String enabled, int date_accept, int total_quantity){
        this.college_name = college_name;
        this.address = address;
        this.detail_address = detail_address;
        this.enabled = enabled;
        this.date_accept = date_accept;
        this.total_quantity = total_quantity;
    }
}
