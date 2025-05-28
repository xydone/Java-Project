package storeproject.exceptions;

// exception za vsichki exceptioni :D 
public class StoreOperationException extends Exception {
    public StoreOperationException(String message) {
        super(message);
    }

    public StoreOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}