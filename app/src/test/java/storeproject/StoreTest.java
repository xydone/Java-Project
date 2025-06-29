/*
 * This source file was generated by the Gradle 'init' task
 */
package storeproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storeproject.exceptions.CashRegisterNotFoundException;
import storeproject.exceptions.CashierNotFoundException;
import storeproject.exceptions.ReceiptFileWriteException;
import storeproject.service.ReceiptService;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StoreTest {

    private Store store;

    @BeforeEach
    void setUp() {
        ReceiptService mockReceiptService = new ReceiptService() {
            @Override
            public void saveReceipt(Receipt receipt) throws ReceiptFileWriteException, IOException {
                // for now, i have not dealt with mocking receipt saving and serialization,
                // might change in the future but probably outside of scope
            }
        };
        store = new Store(0.2, 0.1, 7, 0.1, mockReceiptService);
    }

    @Test
    void testStoreAdd() {
        Item item = new FoodItem(1, "White Monster (zero sugar)", 10.0, LocalDate.now().plusDays(30), 10);
        store.addItem(item);
        assertEquals(item, store.getItemNullable(1));
    }

    @Test
    void testStoreRemove() {
        Item item = new FoodItem(1, "White Monster (zero sugar)", 10.0, LocalDate.now().plusDays(30), 10);
        store.addItem(item);
        store.removeItem(1);
        assertNull(store.getItemNullable(1));
    }

    @Test
    void testStoreAddCashier() {
        Cashier cashier = new Cashier(1, "Ivan Petrov", 1750.58);
        store.addCashier(cashier);
        assertEquals(cashier, store.getCashier(1));
    }

    @Test
    void testStoreAddRegister() {
        CashRegister register = new CashRegister(1);
        store.addCashRegister(register);
        assertEquals(register, store.getCashRegisterNullable(1));
    }

    @Test
    void testStoreAssignCashier() throws CashierNotFoundException, CashRegisterNotFoundException {
        Cashier cashier = new Cashier(1, "Test Cashier", 1750.58);
        CashRegister register = new CashRegister(1);
        store.addCashier(cashier);
        store.addCashRegister(register);

        store.assignCashierToRegister(1, 1);

        assertEquals(cashier, store.getCashRegisterNullable(1).getCashier());
    }

    @Test
    void testStoreRemoveCashier() throws CashierNotFoundException, CashRegisterNotFoundException {
        Cashier cashier = new Cashier(1, "Test Cashier", 1750.58);
        CashRegister register = new CashRegister(1);
        store.addCashier(cashier);
        store.addCashRegister(register);
        store.assignCashierToRegister(1, 1);

        store.removeCashierFromRegister(1);
        assertNull(store.getCashRegisterNullable(1).getCashier());
    }

    @Test
    void testStoreSellItem() throws Exception {
        Item item1 = new FoodItem(1, "Banan", 0.5, LocalDate.now().plusDays(10), 100);
        Item item2 = new NonFoodItem(2, "Pasta za zubi", 2.5, LocalDate.now().plusYears(2), 10);
        store.addItem(item1);
        store.addItem(item2);

        Cashier cashier = new Cashier(1, "Ivan Petrov", 1750.58);
        store.addCashier(cashier);

        CashRegister register = new CashRegister(1);
        store.addCashRegister(register);
        store.assignCashierToRegister(1, 1);

        java.util.Map<Integer, Integer> itemsToSell = new java.util.HashMap<>();
        itemsToSell.put(1, 5);
        itemsToSell.put(2, 2);

        double customerPayment = 20.0;
        Receipt receipt = store.sellItems(1, itemsToSell, customerPayment);

        assertNotNull(receipt);
        assertEquals(cashier, receipt.getCashier());
        assertEquals(2, receipt.getItems().size());
        assertEquals(95, store.getItemNullable(1).getQuantity());
        assertEquals(8, store.getItemNullable(2).getQuantity());
        assertEquals(1, store.getTotalReceiptsIssued());
    }
}
