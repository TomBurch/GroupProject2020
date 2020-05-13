package trade;

public class Product {
    private String productID;
    private int quantity;

    public Product(String productID, int quantity) {
        this.productID = productID;
        this.quantity = quantity;
    }

    public String getProductID() {
        return this.productID;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
