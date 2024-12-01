package org.example;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.awt.image.BufferedImage;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;

public class SupermarketBillingSystemGUI extends JFrame {
    private ShoppingCart cart;
    private JTable cartTable;
    private DefaultTableModel cartTableModel;
    private JTextField discountField;
    private JPanel productPanel;
    private JLabel totalAmountLabel; // Instance variable for the total amount label
    private JTextField customerNameField;

    public SupermarketBillingSystemGUI() {

        cart = new ShoppingCart();
        setTitle("POS - Supermarket Billing System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Load predefined products with images
        List<Product> products = loadProducts();

        // Left panel to display product grid with dynamic resizing
        productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(0, 4, 5, 5)); // 4 columns for product grid
        JScrollPane productScrollPane = new JScrollPane(productPanel);
        populateProductGrid(products);

        // Right panel for cart display and calculations
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout(10, 10)); // Set spacing between components
        rightPanel.setPreferredSize(new Dimension(600, 600));

        // Cart display using JTable
        String[] columnNames = {"Product Name", "Price", "Quantity", "Total"};
        cartTableModel = new DefaultTableModel(columnNames, 0);
        cartTable = new JTable(cartTableModel);
        cartTable.setFillsViewportHeight(true);
        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        rightPanel.add(cartScrollPane, BorderLayout.CENTER);

        // Action panel with discount and buttons
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        actionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Customer Name input section
        JPanel customerPanel = new JPanel(new BorderLayout());
        customerNameField = new JTextField();
        customerPanel.add(new JLabel("Customer Name:"), BorderLayout.WEST);
        customerPanel.add(customerNameField, BorderLayout.CENTER);

        // Discount input section
        JPanel discountPanel = new JPanel(new BorderLayout());
        discountField = new JTextField();
        discountPanel.add(new JLabel("Discount (%):"), BorderLayout.WEST);
        discountPanel.add(discountField, BorderLayout.CENTER);
        JButton discountButton = new JButton("Apply Discount");
        discountButton.addActionListener(e -> applyDiscount());
        discountPanel.add(discountButton, BorderLayout.EAST);

        // Clear Cart Button
        JButton clearButton = new JButton("Clear Cart");
        clearButton.addActionListener(e -> clearCart());

        // Generate Invoice Button
        JButton invoiceButton = new JButton("Generate Invoice");
        invoiceButton.addActionListener(e -> generateInvoice());
        // Add Show Report Button
        JButton reportButton = new JButton("Show Report");
        reportButton.addActionListener(e -> showReport());


        // Initialize total amount label
        totalAmountLabel = new JLabel("Total: $0.00", JLabel.RIGHT);
        totalAmountLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Add components to the action panel
        actionPanel.add(totalAmountLabel);
        actionPanel.add(Box.createVerticalStrut(10));
        actionPanel.add(customerPanel);
         // Add total amount label
        actionPanel.add(Box.createVerticalStrut(10)); // Add spacing

        actionPanel.add(discountPanel);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(clearButton);
        buttonPanel.add(invoiceButton);
        buttonPanel.add(reportButton);

            // Add the buttonPanel to the actionPanel
        actionPanel.add(buttonPanel);
        actionPanel.add(Box.createVerticalStrut(10));


        // Add action panel to the right panel, ensuring it's at the bottom
        rightPanel.add(actionPanel, BorderLayout.SOUTH);

        // Add product scroll pane and right panel to the frame
        add(productScrollPane, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Load predefined products with images
    private List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Rice", 1.2, 1, "/rice.jpeg"));
        products.add(new Product("Wheat Flour", 1.8, 1, "/Wheat_Flour.jpg"));
        products.add(new Product("Sugar", 1.5, 1, "/Sugar.jpg"));
        products.add(new Product("Milk", 2.0, 1, "/Milk.png"));
        products.add(new Product("Lentils", 1.2, 1, "/Lentils.jpg"));
        products.add(new Product("Salt", 1.8, 1, "/Salt.jpg"));
        products.add(new Product("Tea", 1.5, 1, "/Tea.jpg"));
        products.add(new Product("Coffee", 2.0, 1, "/Coffee.jpeg"));
        products.add(new Product("Cooking Oil", 1.2, 1, "/Cooking_Oil.jpeg"));
        products.add(new Product("Spices", 1.8, 1, "/Spices.jpeg"));
        products.add(new Product("Vegetables", 1.5, 1, "/Vegetables.jpg"));
        products.add(new Product("Fruits", 2.0, 1, "/Fruits.jpg"));
        return products;
    }

    // Populate product grid
    private void populateProductGrid(List<Product> products) {
        for (Product product : products) {
            JPanel productCard = new JPanel();
            productCard.setLayout(new BoxLayout(productCard, BoxLayout.Y_AXIS));
            productCard.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            productCard.setMaximumSize(new Dimension(150, 250)); // Increased height for quantity input
            productCard.setPreferredSize(new Dimension(150, 250));

            // Load product image
            ImageIcon productImage;
            URL imageURL = getClass().getResource(product.getImagePath());
            if (imageURL != null) {
                productImage = new ImageIcon(
                        new ImageIcon(imageURL)
                                .getImage()
                                .getScaledInstance(150, 120, Image.SCALE_SMOOTH)
                );
            } else {
                productImage = new ImageIcon(new BufferedImage(150, 120, BufferedImage.TYPE_INT_ARGB));
            }

            JLabel imageLabel = new JLabel(productImage);
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel nameLabel = new JLabel(product.getName(), JLabel.CENTER);
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel priceLabel = new JLabel("$" + product.getPrice(), JLabel.CENTER);
            priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Quantity Input
            JTextField quantityField = new JTextField("1"); // Default quantity
            quantityField.setMaximumSize(new Dimension(100, 25));
            quantityField.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Add to Cart Button
            JButton addToCartButton = new JButton("Add to Cart");
            addToCartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            addToCartButton.addActionListener(e -> {
                int quantity;
                try {
                    quantity = Integer.parseInt(quantityField.getText());
                    if (quantity <= 0) throw new NumberFormatException();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a positive number.");
                    return;
                }
                addItemToCart(product, quantity);
            });

            productCard.add(imageLabel);
            productCard.add(nameLabel);
            productCard.add(priceLabel);
            productCard.add(quantityField);
            productCard.add(addToCartButton);
            productPanel.add(productCard);
        }
    }

    private void addItemToCart(Product product, int quantity) {
        boolean itemExists = false;
        for (Product cartItem : cart.getItems()) {
            if (cartItem.getName().equals(product.getName())) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity); // Update quantity
                itemExists = true;
                break;
            }
        }
        if (!itemExists) {
            cart.addItem(new Product(product.getName(), product.getPrice(), quantity, product.getImagePath())); // Add new item
        }
        updateCartTable();
    }

    private void applyDiscount() {
        double discount;
        try {
            discount = Double.parseDouble(discountField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid discount amount");
            return;
        }
        cart.setDiscount(discount);
        discountField.setText("");
        updateCartTable();
        JOptionPane.showMessageDialog(this, "Discount applied!");
    }

    private void clearCart() {
        cart.clear();
        updateCartTable();
        JOptionPane.showMessageDialog(this, "Cart cleared!");
    }

    private void generateInvoice() {
        if (cart.getItems().size() > 0) {
            String customerName = customerNameField.getText().trim(); // Get customer name from the text field
            if (customerName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a customer name before generating the invoice.");
                return; // Exit if customer name is empty
            }

            try {
                // Step 1: Sanitize the customer name for file naming
                String sanitizedCustomerName = customerName.replaceAll("[^a-zA-Z0-9]", "_"); // Replace non-alphanumeric characters with underscores
                String fileName = "invoice_" + sanitizedCustomerName + "_" + System.currentTimeMillis() + ".pdf"; // Use customer name and timestamp for unique names

                PdfWriter writer = new PdfWriter(fileName);
                PdfDocument pdfDoc = new PdfDocument(writer);
                Document document = new Document(pdfDoc);

                // Add header information
                document.add(new Paragraph("Supermarket Billing Invoice")
                        .setFontSize(18)
                        .setBold());
                document.add(new Paragraph("=========================================="));
                document.add(new Paragraph("Customer: " + customerName));
                document.add(new Paragraph("Date: " + java.time.LocalDate.now()));
                document.add(new Paragraph("==========================================\n"));

                // Create a table for the cart items
                Table table = new Table(4); // 4 columns: Product Name, Price, Quantity, Total
                table.addCell("Product Name");
                table.addCell("Price");
                table.addCell("Quantity");
                table.addCell("Total");

                double totalAmount = 0;
                List<Product> productsForOrder = new ArrayList<>();
                for (Product item : cart.getItems()) {
                    double itemTotal = item.getPrice() * item.getQuantity();
                    table.addCell(item.getName());
                    table.addCell("$" + item.getPrice());
                    table.addCell(String.valueOf(item.getQuantity()));
                    table.addCell("$" + itemTotal);
                    totalAmount += itemTotal;

                    // Add product to the order list
                    productsForOrder.add(new Product(item.getName(), item.getPrice(), item.getQuantity(), null));
                }

                // Add the table to the document
                document.add(table);
                document.add(new Paragraph("\n=========================================="));

                // Calculate and display total amounts
                double discount = cart.getDiscount();
                double discountedTotal = totalAmount * (1 - discount / 100);
                document.add(new Paragraph("Total Amount: $" + totalAmount));
                document.add(new Paragraph("Discount: " + discount + "%"));
                document.add(new Paragraph("Total After Discount: $" + discountedTotal));
                document.add(new Paragraph("=========================================="));

                // Close the document
                document.close();

                // Save order to JSON (Assuming saveOrderToJson is a method you have)
                saveOrderToJson(customerName, productsForOrder);

                // Clear the cart after invoice generation
                cart.clear();
                updateCartTable(); // Make sure you have a method like cart.clearItems() to clear the cart.

                // Show success message
                JOptionPane.showMessageDialog(this, "Invoice generated successfully!\nSaved as: " + fileName);

            } catch (IOException ex) {
                // Show error message if there's an issue generating the invoice
                JOptionPane.showMessageDialog(this, "Error generating invoice: " + ex.getMessage());
            }
        } else {
            // Show error message if the cart is empty
            JOptionPane.showMessageDialog(this, "Your cart is empty. Cannot generate invoice.");
        }
    }

    private void updateCartTable() {
        double totalAmount = 0;
        cartTableModel.setRowCount(0); // Clear existing rows
        for (Product item : cart.getItems()) {
            double itemTotal = item.getPrice() * item.getQuantity();
            cartTableModel.addRow(new Object[]{item.getName(), item.getPrice(), item.getQuantity(), itemTotal});
            totalAmount += itemTotal;
        }

        // Update total amount label
        totalAmountLabel.setText("Total: $" + totalAmount);
    }

    private void saveOrderToJson(String customerName, List<Product> products) {
        File file = new File("orders.json");
        List<Order> orders = new ArrayList<>();

        // Read existing orders from JSON file
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                Gson gson = new Gson();
                java.lang.reflect.Type listType = new TypeToken<List<Order>>() {}.getType();
                orders = gson.fromJson(reader, listType);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error reading existing orders: " + e.getMessage());
            }
        }

        // Add the new order
        orders.add(new Order(customerName, java.time.LocalDate.now().toString(), products));

        // Write the updated orders list to the JSON file
        try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
            Gson gson = new Gson();
            gson.toJson(orders, writer);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving order: " + e.getMessage());
        }
    }
    private void showReport() {
        try {
            // Load JSON file
            File file = new File("orders.json");
            if (!file.exists()) {
                JOptionPane.showMessageDialog(this, "Report file not found!");
                return;
            }

            // Parse JSON using Gson
            Gson gson = new Gson();
            FileReader reader = new FileReader(file);
            java.lang.reflect.Type listType = new TypeToken<List<Order>>() {}.getType();
            List<Order> orders = gson.fromJson(reader, listType);
            reader.close();

            // Show the full report initially with dynamic product data
            showFullReport(orders);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error reading report: " + ex.getMessage());
        }
    }

    private void showFullReport(List<Order> orders) {
        try {
            // Create a table model for the report data
            String[] columnNames = {"Customer Name", "Date", "Product Name", "Price", "Quantity", "Total"};
            DefaultTableModel reportTableModel = new DefaultTableModel(columnNames, 0);

            // Populate the table model with full report data dynamically
            double grandTotal = 0;
            for (Order order : orders) {
                for (Product product : order.getProducts()) {
                    double total = product.getPrice() * product.getQuantity();
                    reportTableModel.addRow(new Object[]{
                            order.getCustomerName(),
                            order.getDate(),
                            product.getName(),
                            product.getPrice(),
                            product.getQuantity(),
                            total
                    });
                    grandTotal += total;
                }
            }

            // Display the full report in a new dialog
            JTable reportTable = new JTable(reportTableModel);
            JScrollPane reportScrollPane = new JScrollPane(reportTable);

            // Create a panel to hold the table and the total amount label
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(reportScrollPane, BorderLayout.CENTER);

            // Add a label to show the grand total amount
            JLabel totalLabel = new JLabel("Total Amount: $" + grandTotal, JLabel.RIGHT);
            totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
            totalLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
            panel.add(totalLabel, BorderLayout.SOUTH);

            // Filter button to open filter options
            JButton filterButton = new JButton("Apply Filter");
            filterButton.addActionListener(e -> {
                // Show filter dialog when filter button is clicked
                showFilterDialog(orders, reportTableModel, totalLabel);
            });

            JPanel filterPanel = new JPanel();
            filterPanel.add(filterButton);
            panel.add(filterPanel, BorderLayout.NORTH);

            // Create and display the dialog for full report
            JDialog reportDialog = new JDialog(this, "Full Report", true);
            reportDialog.setSize(800, 500);
            reportDialog.add(panel);
            reportDialog.setLocationRelativeTo(this);
            reportDialog.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error displaying full report: " + ex.getMessage());
        }
    }

    private void showFilterDialog(List<Order> orders, DefaultTableModel reportTableModel, JLabel totalLabel) {
        // Create the filter dialog
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));

        // Filter by Customer Name
        JTextField customerNameFilterField = new JTextField();
        filterPanel.add(new JLabel("Customer Name:"));
        filterPanel.add(customerNameFilterField);

        // Filter by Product Name
        JTextField productNameFilterField = new JTextField();
        filterPanel.add(new JLabel("Product Name:"));
        filterPanel.add(productNameFilterField);

        // Filter by Price Range
        JPanel priceRangePanel = new JPanel();
        JTextField minPriceField = new JTextField(5);
        JTextField maxPriceField = new JTextField(5);
        priceRangePanel.add(new JLabel("Min Price:"));
        priceRangePanel.add(minPriceField);
        priceRangePanel.add(new JLabel("Max Price:"));
        priceRangePanel.add(maxPriceField);
        filterPanel.add(priceRangePanel);

        // Filter by Date Range
        JPanel dateRangePanel = new JPanel();
        JTextField startDateField = new JTextField(10); // e.g., YYYY-MM-DD
        JTextField endDateField = new JTextField(10);   // e.g., YYYY-MM-DD
        dateRangePanel.add(new JLabel("Start Date:"));
        dateRangePanel.add(startDateField);
        dateRangePanel.add(new JLabel("End Date:"));
        dateRangePanel.add(endDateField);
        filterPanel.add(dateRangePanel);

        // Filter Button to apply the filter
        JButton applyFilterButton = new JButton("Apply Filter");
        applyFilterButton.addActionListener(e -> {
            String customerNameFilter = customerNameFilterField.getText().trim();
            String productNameFilter = productNameFilterField.getText().trim();
            double minPrice = minPriceField.getText().isEmpty() ? 0 : Double.parseDouble(minPriceField.getText());
            double maxPrice = maxPriceField.getText().isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxPriceField.getText());

            // Parse the date range filters
            String startDateStr = startDateField.getText().trim();
            String endDateStr = endDateField.getText().trim();
            LocalDate startDate = startDateStr.isEmpty() ? null : LocalDate.parse(startDateStr);
            LocalDate endDate = endDateStr.isEmpty() ? null : LocalDate.parse(endDateStr);

            // Apply the filter and update the report
            showFilteredReport(orders, customerNameFilter, productNameFilter, minPrice, maxPrice, startDate, endDate, reportTableModel, totalLabel);

            // Close the filter dialog
            JDialog filterDialog = (JDialog) SwingUtilities.getWindowAncestor(applyFilterButton);
            filterDialog.dispose(); // Close the filter dialog
        });
        filterPanel.add(applyFilterButton);

        // Show the filter dialog in a modal window
        JDialog filterDialog = new JDialog(this, "Filter Report", true);
        filterDialog.setSize(300, 400);
        filterDialog.add(filterPanel);
        filterDialog.setLocationRelativeTo(this);
        filterDialog.setVisible(true);
    }

    private void showFilteredReport(List<Order> orders, String customerNameFilter, String productNameFilter, double minPrice, double maxPrice, LocalDate startDate, LocalDate endDate, DefaultTableModel reportTableModel, JLabel totalLabel) {
        try {
            // Clear the existing rows
            reportTableModel.setRowCount(0);
            double grandTotal = 0;

            for (Order order : orders) {
                // Apply customer name filter
                if (!customerNameFilter.isEmpty() && !order.getCustomerName().toLowerCase().contains(customerNameFilter.toLowerCase())) {
                    continue;
                }

                // Apply date range filter
                LocalDate orderDate = LocalDate.parse(order.getDate()); // Assuming order.getDate() is in the format "YYYY-MM-DD"
                if ((startDate != null && orderDate.isBefore(startDate)) || (endDate != null && orderDate.isAfter(endDate))) {
                    continue;
                }

                for (Product product : order.getProducts()) {
                    // Apply product name filter
                    if (!productNameFilter.isEmpty() && !product.getName().toLowerCase().contains(productNameFilter.toLowerCase())) {
                        continue;
                    }

                    // Apply price filter
                    if (product.getPrice() < minPrice || product.getPrice() > maxPrice) {
                        continue;
                    }

                    double total = product.getPrice() * product.getQuantity();
                    reportTableModel.addRow(new Object[]{
                            order.getCustomerName(),
                            order.getDate(),
                            product.getName(),
                            product.getPrice(),
                            product.getQuantity(),
                            total
                    });
                    grandTotal += total;
                }
            }

            // Update the grand total label
            totalLabel.setText("Total Amount: $" + grandTotal);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error displaying filtered report: " + ex.getMessage());
        }
    }
    // Updates the cart table to reflect the current items in the cart


    public static void main(String[] args) {
        SwingUtilities.invokeLater(SupermarketBillingSystemGUI::new);

    }
}
