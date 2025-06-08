package storeproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storeproject.exceptions.ReceiptFileWriteException;
import storeproject.service.ReceiptService;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NonFoodItemTest {
    private static final double MARKUP = 0.1;
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
        store = new Store(0.2, MARKUP, 7, DISCOUNT, mockReceiptService);
    }

    @Test
    void testNonFood() {
        LocalDate expiry = LocalDate.now().plusYears(1);
        NonFoodItem nonFoodItem = new NonFoodItem(1, "Sapun", 5.0, expiry, QUANTITY);

        assertEquals(1, nonFoodItem.getId());
        assertEquals("Sapun", nonFoodItem.getName());
        assertEquals(5.0, nonFoodItem.getUnitRawPrice());
        assertEquals(expiry, nonFoodItem.getExpirationDate());
        assertEquals(QUANTITY, nonFoodItem.getQuantity());
    }

    @Test
    void testNonFoodPrice() {
        LocalDate expiry = LocalDate.now().plusYears(1);
        NonFoodItem nonFoodItem = new NonFoodItem(1, "Pasta za zubi", 5.0, expiry, QUANTITY);

        double expected = 5.0 * (1 + MARKUP);
        assertEquals(expected, nonFoodItem.calculateSellingPrice(store), 0);
    }

    @Test
    void testNonFoodPriceDiscounted() {
        LocalDate expiry = LocalDate.now().plusDays(5);
        NonFoodItem nonFoodItem = new NonFoodItem(1, "Pasta za zubi", 5.0, expiry, QUANTITY);

        double initial = 5.0 * (1 + MARKUP);
        double expected = initial * (1 - DISCOUNT);
        assertEquals(expected, nonFoodItem.calculateSellingPrice(store), 0.001);
    }
}