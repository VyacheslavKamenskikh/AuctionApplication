package ru.skypro.lessons.springboot.skyproauction.dto;

public class FullLot {
    private int id;
    private Status status;
    private String describtion;
    private int startPrice;
    private int bidPrice;
    private int currentPrice;
    private Bid lastBid;

    public int getId() {
        return id;
    }

    public FullLot(int id, Status status, String describtion, int startPrice, int bidPrice, int currentPrice, Bid lastBid) {
        this.id = id;
        this.status = status;
        this.describtion = describtion;
        this.startPrice = startPrice;
        this.bidPrice = bidPrice;
        this.currentPrice = currentPrice;
        this.lastBid = lastBid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public int getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public int getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(int bidPrice) {
        this.bidPrice = bidPrice;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Bid getLastBid() {
        return lastBid;
    }

    public void setLastBid(Bid lastBid) {
        this.lastBid = lastBid;
    }
}
