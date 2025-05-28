package storeproject;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class NonFoodItem extends Item {
    public NonFoodItem(int id, String name, double unitDeliveryPrice, LocalDate expirationDate, int quantity) {
        super(id, name, unitDeliveryPrice, expirationDate, quantity);
    }

    // TODO: moje da bude po-abstraktno
    @Override
    public double calculateSellingPrice(Store store) {
        double markupPercentage = store.getNonFoodMarkupPercentage();
        double sellingPrice = getUnitRawPrice() * (1 + markupPercentage);

        long daysUntilExpiration = ChronoUnit.DAYS.between(LocalDate.now(), getExpirationDate());
        if (daysUntilExpiration < store.getDaysUntilExpirationForDiscount()) {
            sellingPrice = sellingPrice * (1 - store.getExpirationDiscountPercentage());
        }

        return sellingPrice;
    }
}