# Supermarket Billing System 🛒

A Java-based billing system for supermarkets, designed to generate invoices, manage orders, and streamline the checkout process. This project uses Maven for dependency management and includes file handling for order processing and PDF invoice generation.

## Features ✨

- **Invoice Generation**: Automatically generate and save PDF invoices for customer transactions.
- **Order Management**: Add, view, and manage orders through a simple interface.
- **JSON Integration**: Store and retrieve order details in a `JSON` file for easy data management.
- **Maven Build**: Simplify project setup with Maven for dependency and build management.

## Folder Structure 🗂️

```plaintext
.
├── .git/               # Version control folder
├── .idea/              # IntelliJ project configuration files
├── src/                # Source code directory
│   ├── main/           # Main application source files
│   └── test/           # Unit tests
├── target/             # Compiled files and generated outputs
├── LICENSE             # License file
├── orders.json         # Stores order details in JSON format
├── pom.xml             # Maven build file for dependencies and plugins
├── README.md           # Project documentation
├── POS.iml             # IntelliJ module file
├── invoice_demo_*.pdf  # Example PDF invoices
```

## Technologies Used 🛠️

- **Java**: The core programming language.
- **Maven**: For dependency and build management.
- **JSON**: To store order data.
- **PDF Generation**: Invoices are generated as PDF files.

## Getting Started 🚀

### Prerequisites

    1. Install the latest version of [Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html).
    2. Install [Maven](https://maven.apache.org/download.cgi).
    3. Use an IDE like [IntelliJ IDEA](https://www.jetbrains.com/idea/) or [Eclipse](https://www.eclipse.org/).

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/supermarket-billing-system.git
   cd supermarket-billing-system
   ```
2. Open the project in your IDE.
3. Build the project using Maven:
   ```bash
   mvn clean install
   ```
4. Run the main application:
   ```bash
   mvn exec:java -Dexec.mainClass="com.example.Main"
   ```

## Usage

-  Launch the application.
-  Perform the following tasks:
    - **Add Orders**: Input customer order details.
    - **Generate Invoices**: Save customer orders as PDF invoices.
    - **View Orders**: Retrieve saved order details from `orders.json`.

## Example PDF Invoice 📄

- Below is an example of an automatically generated invoice:


## How It Works 🤔

- **Order Input**: Users input customer orders.
- **JSON Storage**: Orders are saved into `orders.json` for persistence.
- **PDF Invoice Generation**: The application creates an invoice in PDF format for each order.
- **Order Retrieval**: Saved orders can be loaded and displayed.

## Dependencies

- Maven dependencies are managed in the `pom.xml` file. Some key libraries include:
  - `PDFBox` for generating PDF invoices.
  - `Gson` for handling JSON data.

## License 📄

This project is licensed under the [MIT License](LICENSE).

## Contributing 🤝

Contributions are welcome! Feel free to fork the repository, submit issues, or open pull requests.


Let me know if you'd like to make further adjustments or add more content! 😊

