package org.example;

import java.util.List;

public class Order {
    private String customerName;
    private String date;
    private List<Product> products;
    public Order(String customerName, String date, List<Product> products) {
        this.customerName = customerName;
        this.date = date;
        this.products = products;
    }
    // Getters and Setters
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}