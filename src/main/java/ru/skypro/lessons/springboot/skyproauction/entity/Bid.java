package ru.skypro.lessons.springboot.skyproauction.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "bids")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 20,nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "date_time", nullable = false,updatable = false)
    private OffsetDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "lot_id")
    private Lot lot;

    public Bid(){};

    public Bid(String name) {
        this.name = name;
    }

    public Bid(String name, Lot lot) {
        this.name = name;
        this.lot = lot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(OffsetDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }
}
