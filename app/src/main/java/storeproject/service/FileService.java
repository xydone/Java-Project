package storeproject.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import storeproject.Receipt;
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
            out.println(receipt.toFileString());
        } catch (java.io.IOException e) {
            throw new ReceiptFileWriteException(filenameBase + ".txt", e);
        }
        System.out.println("Kasova belejka zapazena v file " + filenameBase + ".txt");

        try {
            receipt.serializeToFile(filenameBase + ".ser");
        } catch (IOException e) {
            System.err.println("Greshka pri serializiraneto na " + receipt.getSerialNumber() + ": " + e.getMessage());
            throw e;
        }
    }
}