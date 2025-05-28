package storeproject.exceptions;

public class InsufficientStockException extends Exception {
    private int itemId;
    private int requestedQuantity;
    private int availableQuantity;

    public InsufficientStockException(int itemId, int requestedQuantity, int availableQuantity) {
        this.itemId = itemId;
        this.requestedQuantity = requestedQuantity;
        this.availableQuantity = availableQuantity;
    }

    public int getItemId() {
        return itemId;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    @Override
    public String getMessage() {
        return "Nqma dostatuchno kolichestvo ot produkt " + itemId + ".  Ochakva se: " + requestedQuantity
                + ", Nalichno: " + availableQuantity;
    }
}
