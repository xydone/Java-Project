package storeproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CashierTest {

    @Test
    void testCashier() {
        Cashier cashier = new Cashier(1, "Ivan Petrov", 2000.0);

        assertEquals(1, cashier.getId());
        assertEquals("Ivan Petrov", cashier.getName());
        assertEquals(2000.0, cashier.getMonthlySalary());
    }
}