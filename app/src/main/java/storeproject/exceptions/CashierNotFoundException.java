package storeproject.exceptions;

public class CashierNotFoundException extends StoreOperationException {
    public CashierNotFoundException(int cashierId) {
        super("Kasier s ID " + cashierId + " ne beshe otkrit.");
    }

    public CashierNotFoundException(String message) {
        super(message);
    }
}