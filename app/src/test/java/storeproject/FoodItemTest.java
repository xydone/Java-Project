package storeproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;

import storeproject.exceptions.ReceiptFileWriteException;
import storeproject.service.ReceiptService;

class FoodItemTest {
    private static final double MARKUP = 0.2;
    private static final double DISCOUNT = 0.1;
    private static final int QUANTITY = 50;
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
        store = new Store(MARKUP, 0.1, 7, DISCOUNT, mockReceiptService);
    }

    @Test
    void testFoodItem() {
        LocalDate expiry = LocalDate.now().plusDays(10);
        FoodItem foodItem = new FoodItem(1, "Qbulka", 1.5, expiry, QUANTITY);

        assertEquals(1, foodItem.getId());
        assertEquals("Qbulka", foodItem.getName());
        assertEquals(1.5, foodItem.getUnitRawPrice());
        assertEquals(expiry, foodItem.getExpirationDate());
        assertEquals(QUANTITY, foodItem.getQuantity());
    }

    @Test
    void testFoodPrice() {
        LocalDate expiry = LocalDate.now().plusDays(10);
        FoodItem foodItem = new FoodItem(1, "Domat", 1.0, expiry, QUANTITY);

        double expected = 1.0 * (1 + 0.2);
        assertEquals(expected, foodItem.calculateSellingPrice(store), 0);
    }

    @Test
    void testFoodPriceDiscounted() {
        LocalDate expiry = LocalDate.now().plusDays(5);
        FoodItem foodItem = new FoodItem(1, "Krastavica", 1.0, expiry, QUANTITY);

        double initial = 1.0 * (1 + MARKUP);
        double expected = initial * (1 - DISCOUNT);
        assertEquals(expected, foodItem.calculateSellingPrice(store), 0);
    }
}