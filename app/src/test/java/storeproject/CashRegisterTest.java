package storeproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CashRegisterTest {

    @Test
    void testCashRegisterInit() {
        CashRegister register = new CashRegister(1);
        assertEquals(1, register.getId());
        assertNull(register.getCashier());
    }

    @Test
    void testCashRegisterAssignment() {
        CashRegister register = new CashRegister(1);
        Cashier cashier = new Cashier(1, "Ivan Petrov", 1750.50);

        register.assignCashier(cashier);
        assertEquals(cashier, register.getCashier());

        register.removeCashier();
        assertNull(register.getCashier());
    }
}