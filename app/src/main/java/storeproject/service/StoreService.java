package storeproject.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import storeproject.exceptions.CashierNotFoundException;
import storeproject.CashRegister;
import storeproject.Cashier;
import storeproject.FoodItem;
import storeproject.NonFoodItem;
import storeproject.Receipt;
import storeproject.Store;
import storeproject.exceptions.CashRegisterNotFoundException;
import storeproject.exceptions.ExpiredItemException;
import storeproject.exceptions.InsufficientPaymentException;
import storeproject.exceptions.InsufficientStockException;
import storeproject.exceptions.NoCashierAssignedException;
import storeproject.exceptions.ReceiptFileWriteException;

public class StoreService {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Store store = null;
        ReceiptService receiptService = new FileService("receipts");

        while (true) {
            System.out.println("1. Konfiguraciq na magazin");
            System.out.println("2. Dobavqne na produkti");
            System.out.println("3. Dobavqne na kasieri");
            System.out.println("4. Dobavqne na kasi");
            System.out.println("5. Svurzvane na kasier s kasa");
            System.out.println("6. Prodajbi");
            System.out.println("7. Informaciq za prodajbi");
            System.out.println("8. exit");

            int choice = -1;
            try {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                } else {
                    System.out.println("Izbora trqbva da bude chislo.");
                    scanner.next();
                    continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("Izbora trqbva da bude chislo.");
                scanner.nextLine();
                continue;
            } finally {
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
            }

            if (choice == 1) {
                System.out.println("\n--- Konfiguraciq na magazin ---");
                try {
                    System.out.print("Vuvedete nadcenka na hranitelni produkti (e.x. 0.2): ");
                    double foodMarkup = scanner.nextDouble();
                    System.out.print("Vuvedete nadcenka na ne-hranitelni produkti (e.x. 0.3): ");
                    double nonFoodMarkup = scanner.nextDouble();
                    System.out.print(
                            "Vuvedete kolko dni trqbva da ostavat ot godnosta na edin produkt, za da bude namalen: ");
                    int daysForDiscount = scanner.nextInt();
                    System.out.print(
                            "Vuvedete procenta na namalenie, kogato produkt nablijava srok na godnost (e.x. 0.1): ");
                    double discountPercentage = scanner.nextDouble();
                    scanner.nextLine();
                    store = new Store(foodMarkup, nonFoodMarkup, daysForDiscount, discountPercentage, receiptService);
                    System.out.println("Konfiguraciqta na magazina e uspeshna!");
                } catch (InputMismatchException e) {
                    // ostaveno za debugging, ne bi trqbvalo da se sreshta, tui kato chestite
                    // greshki sa pokriti ot ostanalite catch blockove
                    System.err.println(e.getMessage());
                    scanner.nextLine();
                }
            } else if (choice >= 2 && choice <= 7 && store == null) {
                System.out.println("Ne e zadadena konfiguraciq na magazina. Izberete opciq 1.");
                continue;
            } else if (choice == 2) {
                System.out.println("\n--- Dobavqne na produkt ---");
                while (true) {
                    try {
                        System.out.print(
                                "Vuvedete tipa na produkta (food/nonfood, ili ako iskate da prikluchite, 'next'): ");
                        String type = scanner.nextLine().toLowerCase();
                        if (type.equals("next"))
                            break;

                        System.out.print("Vuvedete ID-to na produkta: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Vuvedete imeto na produkta: ");
                        String name = scanner.nextLine();
                        System.out.print("Vuvedete cenata na 1 broi produkt, bez nadcenka: ");
                        double rawPrice = scanner.nextDouble();
                        System.out.print("Vuvedete kolichestvoto: ");
                        int quantity = scanner.nextInt();
                        System.out.print("Srok na godnost");
                        System.out.print("Vuvedete godinata (ex. 2025): ");
                        int year = scanner.nextInt();
                        System.out.print("Vuvedete meseca (ex. 10): ");
                        int month = scanner.nextInt();
                        System.out.print("Vuvedete denq (ex. 25): ");
                        int day = scanner.nextInt();
                        scanner.nextLine();
                        LocalDate expirationDate = LocalDate.of(year, month, day);

                        if (type.equals("food")) {
                            store.addItem(new FoodItem(id, name, rawPrice, expirationDate, quantity));
                        } else if (type.equals("nonfood")) {
                            store.addItem(new NonFoodItem(id, name, rawPrice, expirationDate, quantity));
                        } else {
                            System.out.println("Tiput na produkta trqbva da bude 'food' ili 'nonfood'.");
                            continue;
                        }
                        System.out.println(name + " beshe uspeshno dobaven.");
                    } catch (InputMismatchException e) {
                        System.out.println("Greshka pri vuvejdane na danni. Nqkoq ot dannite ne e ot pravilniqt type");
                        scanner.nextLine();
                    } catch (Exception e) {
                        // ostaveno za debugging, ne bi trqbvalo da se sreshta, tui kato chestite
                        // greshki sa pokriti ot ostanalite catch blockove
                        System.err.println(e.getMessage());
                    }
                }
            } else if (choice == 3) {
                System.out.println("\n--- Dobavqne na kasier ---");
                while (true) {
                    try {
                        System.out.print("Vuvedete ID-to na kasiera (ili ako iskate da prikluchite, 'next'): ");
                        String idInput = scanner.nextLine();
                        if (idInput.equalsIgnoreCase("next"))
                            break;
                        int id = Integer.parseInt(idInput);

                        System.out.print("Ime: ");
                        String name = scanner.nextLine();
                        System.out.print("Mesechna zaplata: ");
                        double salary = scanner.nextDouble();
                        scanner.nextLine();

                        store.addCashier(new Cashier(id, name, salary));
                        System.out.println("Kasierut " + name + " e dobaven.");
                    } catch (InputMismatchException e) {
                        System.out.println("Zaplatata trqbva da e chislo.");
                        scanner.nextLine();
                    } catch (NumberFormatException e) {
                        System.out.println("ID-to trqbva da e chislo ili 'next'");
                    } catch (Exception e) {
                        // ostaveno za debugging, ne bi trqbvalo da se sreshta, tui kato chestite
                        // greshki sa pokriti ot ostanalite catch blockove
                        System.err.println(e.getMessage());
                    }
                }
            } else if (choice == 4) {
                System.out.println("\n--- Dobavqne na kasi ---");
                while (true) {
                    try {
                        System.out.print("Dobavete ID-to na kasata (ili ako iskate da prikluchite, 'next'): ");
                        String idInput = scanner.nextLine();
                        if (idInput.equalsIgnoreCase("next"))
                            break;
                        int id = Integer.parseInt(idInput);

                        store.addCashRegister(new CashRegister(id));
                        System.out.println("Uspeshno dobavqna na kasa " + id + ".");
                    } catch (NumberFormatException e) {
                        System.out.println("ID-to trqbva da e chislo ili 'next'");
                    } catch (Exception e) {
                        // ostaveno za debugging, ne bi trqbvalo da se sreshta, tui kato chestite
                        // greshki sa pokriti ot ostanalite catch blockove
                        System.err.println(e.getMessage());
                    }
                }
            } else if (choice == 5) {
                System.out.println("\n--- Svurzvane na kasier s kasa ---");
                try {
                    System.out.print("ID-to na kasiera: ");
                    int cashierId = scanner.nextInt();
                    System.out.print("ID-to na kasata: ");
                    int registerIdVal = scanner.nextInt();
                    scanner.nextLine();
                    store.assignCashierToRegister(cashierId, registerIdVal);
                    System.out.println("Kasiera e uspeshno svurzan!");
                } catch (InputMismatchException e) {
                    System.out.println("ID-to trqbva da e chislo.");
                    scanner.nextLine();
                } catch (CashierNotFoundException | CashRegisterNotFoundException e) {
                    System.err.println("Greshka pri vruzkata kasier-kasa: " + e.getMessage());
                } catch (Exception e) {
                    // ostaveno za debugging, ne bi trqbvalo da se sreshta, tui kato chestite
                    // greshki sa pokriti ot ostanalite catch blockove
                    System.err.println(e.getMessage());
                }
            } else if (choice == 6) {
                System.out.println("\n--- Prodajba na produkt ---");
                Map<Integer, Integer> itemsToSell = new HashMap<>();
                try {
                    System.out.print("Vuvedete ID-to na kasata: ");
                    int regId = scanner.nextInt();
                    scanner.nextLine();

                    while (true) {
                        System.out.print("Vuvedete ID-to na produkta (ili ako iskate da prikluchite, 'next'): ");
                        String itemIdInput = scanner.nextLine();
                        if (itemIdInput.equalsIgnoreCase("next")) {
                            break;
                        }
                        try {
                            int itemId = Integer.parseInt(itemIdInput);
                            System.out.print("Vuvedete kolichestvoto za produkt " + itemId + ": ");
                            int quantity = scanner.nextInt();
                            scanner.nextLine();
                            itemsToSell.put(itemId, quantity);
                        } catch (NumberFormatException e) {
                            System.out.println("ID-to na produkta i kolichestvoto trqbva da budat chisla.");
                        }
                    }

                    if (itemsToSell.isEmpty()) {
                        System.out.println("Kolichkata e prazna.");
                    } else {
                        System.out.print("Vuvedete sumata, koqto dava kupuvacha: ");
                        double paymentAmount = scanner.nextDouble();
                        scanner.nextLine();

                        Receipt receipt = store.sellItems(regId, itemsToSell, paymentAmount);
                        if (receipt != null) {
                            System.out.println("Uspeshna prodajba!");
                        }
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Ne sushtestvuva.");
                    scanner.nextLine();
                } catch (InsufficientStockException | ExpiredItemException | InsufficientPaymentException
                        | ReceiptFileWriteException | CashRegisterNotFoundException | NoCashierAssignedException e) {
                    System.err.println("Sale failed: " + e.getMessage());
                }
            } else if (choice == 7) {
                System.out.println("\n--- Informaciq za prodajbi ---");
                System.out.println("Pechalba bez razhodi: " + String.format("%.2f", store.getTotalRevenue()));
                System.out.println("Broika kasovi belejki: " + store.getTotalReceiptsIssued());
                System.out.println("Zaplati: " + String.format("%.2f", store.calculateCashierSalariesCost()));
                System.out.println("Dostavki: " + String.format("%.2f", store.calculateDeliveryCosts()));
                System.out.println("Obshto: " + String.format("%.2f", store.calculateProfit()));
            } else if (choice == 8) {
                break;
            } else {
                System.out.println("Ne sushtestvuva.");
            }
        }
        scanner.close();
    }

}