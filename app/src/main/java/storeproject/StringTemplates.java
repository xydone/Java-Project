package storeproject;

import java.time.LocalDateTime;
import java.util.List;

public class StringTemplates {
    public String receiptID(int serialNumber) {
        return "Nomer na kasova belejka: " + serialNumber;
    }

    public String cashierName(String name) {
        return "Kasier: " + name;
    }

    public String issuedAt(LocalDateTime dateTime) {
        return "Izdadena na: " + dateTime;
    }

    public String items(List<ReceiptItem> items) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            ReceiptItem item = items.get(i);
            sb.append(item.getItem().getName())
                    .append(" x ")
                    .append(item.getQuantity())
                    .append(" @ $")
                    .append(String.format("%.2f", item.getPrice()))
                    .append(" = $")
                    .append(String.format("%.2f", item.getPrice() * item.getQuantity()));
            if (i < items.size() - 1) {
                sb.append("\n");
            }
        }
        ;
        return sb.toString();
    }

    public String newline() {
        return "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-";
    }

    public String genericAmount(String priceLabel, double price) {
        return priceLabel + ": $" + String.format("%.2f", price);
    }

    public String totalAmount(double price) {
        return genericAmount("Obshto", price);
    }

    public String paidAmount(double price) {
        return genericAmount("Plateno", price);
    }

    public String remainderAmount(double price) {
        return genericAmount("Resto", price);
    }
}