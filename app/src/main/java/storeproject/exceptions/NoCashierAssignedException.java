package storeproject.exceptions;

public class NoCashierAssignedException extends StoreOperationException {
    public NoCashierAssignedException(int registerId) {
        super("Nqma kasier na kasa " + registerId + ".");
    }

    public NoCashierAssignedException(String message) {
        super(message);
    }
}