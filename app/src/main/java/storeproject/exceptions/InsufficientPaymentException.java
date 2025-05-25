package storeproject.exceptions;

public class InsufficientPaymentException extends Exception {
    private double requiredAmount;
    private double providedAmount;

    public InsufficientPaymentException(double requiredAmount, double providedAmount) {
        this.requiredAmount = requiredAmount;
        this.providedAmount = providedAmount;
    }

    @Override
    public String getMessage() {
        return "Nedostatuchna suma. Cena: " + requiredAmount + ", Predostaveno: " + providedAmount;
    }
}
