package org.example;

public class Product {
    private String name;
    private double price;
    private int quantity;
    private String imagePath; // New field for storing the image path

    // Updated constructor to accept an image path
    public Product(String name, double price, int quantity, String imagePath) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imagePath = imagePath;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImagePath() { // New getter for image path
        return imagePath;
    }

    // Setter for quantity if needed
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
