package storeproject;

import java.util.HashMap;
import java.util.Map;

public class Bag {
    private Map<Integer, Integer> items;
    private double amountPaid;

    public Bag() {
        this.items = new HashMap<>();
    }

    public void addItem(int productId, int quantity) {
        items.put(productId, items.getOrDefault(productId, 0) + quantity);
    }

    public void setAmountPaid(double amount) {
        this.amountPaid = amount;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public Map<Integer, Integer> getItems() {
        return items;
    }
}