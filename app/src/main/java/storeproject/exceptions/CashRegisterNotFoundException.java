package storeproject.exceptions;

public class CashRegisterNotFoundException extends StoreOperationException {
    public CashRegisterNotFoundException(int registerId) {
        super("Kasa s ID " + registerId + " ne beshe otkrita.");
    }

    public CashRegisterNotFoundException(String message) {
        super(message);
    }
}