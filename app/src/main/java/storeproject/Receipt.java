package storeproject;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Receipt implements Serializable {
    private int serialNumber;
    private Cashier cashier;
    private LocalDateTime dateTime;
    private List<ReceiptItem> items;
    private double totalValue;
    private double moneyProvided;

    public Receipt(int serialNumber, Cashier cashier, LocalDateTime dateTime, double moneyProvided) {
        this.serialNumber = serialNumber;
        this.cashier = cashier;
        this.dateTime = dateTime;
        this.items = new ArrayList<>();
        this.totalValue = 0.0;
        this.moneyProvided = moneyProvided;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public List<ReceiptItem> getItems() {
        return items;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public double getMoneyProvided() {
        return moneyProvided;
    }

    public void addItem(ReceiptItem item) {
        this.items.add(item);
        this.totalValue += item.getPrice() * item.getQuantity();
    }

    public void printReceipt() {
        StringTemplates templates = new StringTemplates();
        System.out.println(templates.receiptID(serialNumber));
        System.out.println(templates.cashierName(cashier.getName()));
        System.out.println(templates.issuedAt(dateTime));
        System.out.println(templates.newline());
        System.out.println(templates.items(items));
        System.out.println(templates.newline());
        System.out.println(templates.totalAmount(totalValue));
        System.out.println(templates.paidAmount(moneyProvided));
        System.out.println(templates.remainderAmount(moneyProvided - totalValue));
    }

}