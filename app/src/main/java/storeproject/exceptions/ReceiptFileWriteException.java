package storeproject.exceptions;

public class ReceiptFileWriteException extends CustomIOException {
    public ReceiptFileWriteException(String filename, Throwable cause) {
        super("Greshka pri zapisvane na file: " + filename, cause);
    }

    public ReceiptFileWriteException(String message) {
        super(message);
    }
}