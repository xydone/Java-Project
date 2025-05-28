package storeproject;

import java.time.LocalDateTime;
import java.util.List;

public class StringTemplates {
    String receiptID(int serialNumber) {
        return "Nomer na kasova belejka: " + serialNumber;
    }

    String cashierName(String name) {
        return "Kasier: " + name;
    }

    String issuedAt(LocalDateTime dateTime) {
        return "Izdadena na: " + dateTime;
    }

    String items(List<ReceiptItem> items) {
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

    String newline() {
        return "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-";
    }

    String genericAmount(String priceLabel, double price) {
        return priceLabel + ": $" + String.format("%.2f", price);
    }

    String totalAmount(double price) {
        return genericAmount("Obshto", price);
    }

    String paidAmount(double price) {
        return genericAmount("Plateno", price);
    }

    String remainderAmount(double price) {
        return genericAmount("Resto", price);
    }
}