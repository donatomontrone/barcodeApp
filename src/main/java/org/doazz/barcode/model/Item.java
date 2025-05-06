package org.doazz.barcode.model;

import jakarta.persistence.*;

@Entity(name = "item")
@Table(name = "item")
public class Item {

    @Id
    @Column(name = "EAN", unique = true, nullable = false)
    private String EAN;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private double price;

    public Item() {}

    public Item(String EAN, String name, double price) {
        this.EAN = EAN;
        this.name = name;
        this.price = price;
    }

    public String getEAN() {
        return EAN;
    }

    public void setEAN(String EAN) {
        this.EAN = EAN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
