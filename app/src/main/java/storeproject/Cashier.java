package storeproject;

import java.io.Serializable;

public class Cashier implements Serializable {
    private int id;
    private String name;
    private double monthlySalary;

    public Cashier(int id, String name, double monthlySalary) {
        this.id = id;
        this.name = name;
        this.monthlySalary = monthlySalary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }
}