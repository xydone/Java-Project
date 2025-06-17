package storeproject.service;

import java.io.IOException;

import storeproject.Receipt;
import storeproject.exceptions.ReceiptFileWriteException;

public interface ReceiptService {
    void saveReceipt(Receipt receipt) throws ReceiptFileWriteException, IOException;

}