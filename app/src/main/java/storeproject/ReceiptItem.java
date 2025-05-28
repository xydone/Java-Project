package storeproject;

import java.io.Serializable;

public class ReceiptItem implements Serializable {
    private Item item;
    private int quantity;
    private double price;

    public ReceiptItem(Item item, int quantity, double price) {
        this.item = item;
        this.quantity = quantity;
        this.price = price;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}