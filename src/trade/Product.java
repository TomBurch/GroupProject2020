package trade;

import java.util.Formatter;

/**
 * Class representing an item from the Products table
 */
public class Product {
    private String productID;
    private String isbn;
    private String title;
    private float price;
    private String author;
    private String publisher;
    private String yearPublished;
    private String description;
    private int quantity;

    public Product(String productID, String isbn, String title, float price, String author, String publisher, String yearPublished, String description, int quantity) {
        this.productID = productID;
        this.isbn = isbn;
        this.title = title;
        this.price = price;
        this.author = author;
        this.publisher = publisher;
        this.yearPublished = yearPublished;
        this.description = description;
        this.quantity = quantity;
    }

    public String getProductID() {
        return this.productID;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public float getPrice() { return this.price; }

    public String getISBN() { return this.isbn; }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Get a multiline description of the Product - used in HomePanel output
     * @return  String
     */
    public String getDetails() {
        StringBuilder sb = new StringBuilder(256);
        Formatter fr = new Formatter(sb);

        fr.format("Title: %s\n", this.title);
        fr.format("Author: %s\n", this.author);
        fr.format("Price: £%5.2f\n", this.price);
        fr.format("Publisher: %s\n", this.publisher);
        fr.format("Year Published: %s\n", this.yearPublished);
        fr.format("Description: %s\n", this.description);
        fr.close();

        return sb.toString();
    }

    /**
     * Get a single-line summary of the Product - used in JLists
     * @return  String
     */
    public String getLineSummary() {
        return String.format("%sx  %s    £%5.2f    %s    %s", this.quantity, this.isbn, (this.price * this.quantity), this.title, this.author);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof Product)) {
            return false;
        }

        Product pr = (Product) object;
        return this.productID.equals(pr.productID);
    }
}