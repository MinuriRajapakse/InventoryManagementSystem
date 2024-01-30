package com.example.coursework;

import java.time.LocalDate;

public class ViewDealers {
    private LocalDate registerDate;
    private String dealerName;
    private String contactNo;
    private String location;

    public ViewDealers(LocalDate registerDate, String dealerName, String contactNo, String location) {
        this.registerDate = registerDate;
        this.dealerName = dealerName;
        this.contactNo = contactNo;
        this.location = location;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}