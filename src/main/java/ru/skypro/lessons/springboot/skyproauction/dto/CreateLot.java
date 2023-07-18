package ru.skypro.lessons.springboot.skyproauction.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateLot {
    @NotBlank
    @Size(min = 3,max = 64)
    private String title;
    @NotBlank
    @Size(min = 3,max = 4096)
    private String describtion;
    @NotNull
    @Min(1)
    private Integer startPrice;
    @NotNull
    @Min(1)
    private Integer bidPrice;
    private int id;

    public String getTitle() {
        return title;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public int getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(int bidPrice) {
        this.bidPrice = bidPrice;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
