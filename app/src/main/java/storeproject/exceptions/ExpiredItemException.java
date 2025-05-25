package storeproject.exceptions;

import java.time.LocalDate;

public class ExpiredItemException extends Exception {
  private int itemId;
  private LocalDate expirationDate;

  public ExpiredItemException(int itemId, LocalDate expirationDate) {
    this.itemId = itemId;
    this.expirationDate = expirationDate;
  }

  public int getItemId() {
    return itemId;
  }

  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  @Override
  public String getMessage() {
    return "Produktut " + itemId + " e sus iztekul srok na godnost. Srok na godnost: " + expirationDate;
  }
}