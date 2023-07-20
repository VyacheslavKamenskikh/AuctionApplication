package ru.skypro.lessons.springboot.skyproauction.dto;

import java.time.OffsetDateTime;

public class Bid {
    private String bidderName;
    private OffsetDateTime bidDate;

    public String getBidderName() {
        return bidderName;
    }

    public Bid(){

    }

    public void setBidderName(String bidderName) {
        this.bidderName = bidderName;
    }

    public OffsetDateTime getBidDate() {
        return bidDate;
    }

    public void setBidDate(OffsetDateTime bidDate) {
        this.bidDate = bidDate;
    }

    public Bid(String bidderName, OffsetDateTime bidDate) {
        this.bidderName = bidderName;
        this.bidDate = bidDate;
    }
}
