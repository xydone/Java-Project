package storeproject;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Item implements Serializable {
    private int id;
    private String name;
    private double unitRawPrice;
    private LocalDate expirationDate;
    private int quantity;

    public Item(int id, String name, double unitRawPrice, LocalDate expirationDate, int quantity) {
        this.id = id;
        this.name = name;
        this.unitRawPrice = unitRawPrice;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getUnitRawPrice() {
        return unitRawPrice;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public abstract double calculateSellingPrice(Store store);
}