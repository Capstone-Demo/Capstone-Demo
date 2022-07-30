package com.example.basiccode;

public class ReportList {

    private int report_id; //신고내역 id값
    private String report_date; //신고날짜
    private String report_college; //신고위치
    private String car_number; //신고차량번호
    private String cause; //신고이유(위반내역)

    public int getReport_id() {
        return report_id;
    }

    public void setReport_id(int report_id) {
        this.report_id = report_id;
    }

    public String getReport_date() {
        return report_date;
    }

    public void setReport_date(String report_date) {
        this.report_date = report_date;
    }

    public String getReport_college() {
        return report_college;
    }

    public void setReport_college(String report_college) {
        this.report_college = report_college;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    ReportList(int report_id, String report_date, String report_college, String car_number, String cause){
        this.report_id = report_id;
        this.report_date = report_date;
        this.report_college = report_college;
        this.car_number = car_number;
        this.cause = cause;
    }
}
