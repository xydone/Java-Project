package storeproject;

import java.io.Serializable;

public class CashRegister implements Serializable {
    private int id;
    private Cashier cashier;

    public CashRegister(int id) {
        this.id = id;
        this.cashier = null;
    }

    public int getId() {
        return id;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public void assignCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    public void removeCashier() {
        this.cashier = null;
    }
}