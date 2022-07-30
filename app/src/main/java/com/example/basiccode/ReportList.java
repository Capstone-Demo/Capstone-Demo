package com.example.basiccode;

public class ReportList {

    private String report_date; //신고날짜
    private String report_college; //신고위치
    private String car_number; //신고차량번호
    private String cause; //신고이유(위반내역)


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

    ReportList(String report_date, String report_college, String car_number, String cause){
        this.report_date = report_date;
        this.report_college = report_college;
        this.car_number = car_number;
        this.cause = cause;
    }
}
