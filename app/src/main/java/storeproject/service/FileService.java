package storeproject.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import storeproject.Receipt;
import storeproject.StringTemplates;
import storeproject.exceptions.ReceiptFileWriteException;

public class FileService implements ReceiptService {

    private final String receiptsDirectory;

    public FileService(String receiptsDirectory) {
        this.receiptsDirectory = receiptsDirectory;
        File dir = new File(receiptsDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
    public void saveReceipt(Receipt receipt) throws ReceiptFileWriteException, IOException {
        String filenameBase = receiptsDirectory + "/receipt_" + receipt.getSerialNumber();

        try (PrintWriter out = new PrintWriter(new FileWriter(filenameBase + ".txt"))) {
            out.println(toFileString(receipt));
        } catch (java.io.IOException e) {
            throw new ReceiptFileWriteException(filenameBase + ".txt", e);
        }
        System.out.println("Kasova belejka zapazena v file " + filenameBase + ".txt");

        try {
            serializeToFile(filenameBase + ".ser", receipt);
        } catch (IOException e) {
            System.err.println("Greshka pri serializiraneto na " + receipt.getSerialNumber() + ": " + e.getMessage());
            throw e;
        }
    }

    public void serializeToFile(String filename, Receipt receipt) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(receipt);
        }
    }

    public static Receipt deserializeFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Receipt) ois.readObject();
        }
    }

    public String toFileString(Receipt receipt) {
        StringBuilder sb = new StringBuilder();

        StringTemplates templates = new StringTemplates();
        sb.append(templates.receiptID(receipt.getSerialNumber())).append("\n");
        sb.append(templates.cashierName(receipt.getCashier().getName())).append("\n");
        sb.append(templates.issuedAt(receipt.getDateTime())).append("\n");
        sb.append(templates.newline()).append("\n");
        sb.append(templates.items(receipt.getItems())).append("\n");
        sb.append(templates.newline()).append("\n");
        sb.append(templates.totalAmount(receipt.getTotalValue())).append("\n");
        sb.append(templates.paidAmount(receipt.getMoneyProvided())).append("\n");
        sb.append(templates.remainderAmount(receipt.getMoneyProvided() - receipt.getTotalValue())).append("\n");
        return sb.toString();
    }
}