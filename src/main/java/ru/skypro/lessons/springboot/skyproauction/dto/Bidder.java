package ru.skypro.lessons.springboot.skyproauction.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Bidder {
    @NotBlank
    @Size(min = 2,max = 20)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
