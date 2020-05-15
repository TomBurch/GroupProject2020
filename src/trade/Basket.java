package trade;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * ArrayList of Products
 */
public class Basket extends ArrayList<Product> {
    /**
     * Search the Basket for a Product with the given ISBN
     * @param isbn  String
     * @return  Product or null
     */
    public Product getProductFromISBN(String isbn) {
        return super.stream()
                .filter(product -> product.getISBN().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get the total size of the Basket (sum of Product.quantity)
     * @return  int
     */
    public int getTotalSize() {
        return super.stream().mapToInt(Product::getQuantity).sum();
    }

    /**
     * Get the total price of the Basket (sum of Product.quantity * Product.price)
     * @return  float
     */
    public float getTotalPrice() {
        float price = 0;
        ListIterator<Product> iterator = super.listIterator();
        Product product;

        while (iterator.hasNext()) {
            product = iterator.next();
            price += (product.getQuantity() * product.getPrice());
        }

        return price;
    }

    /**
     * Add the given quantity of the Product to the Basket
     * @param product   Product
     * @param quantity  int
     */
    public void add(Product product, int quantity) {
        int i = super.indexOf(product);

        if (i != -1) {  // Product already in Basket, just change its quantity
            Product existingProduct = super.get(i);
            existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
        } else {  // Product not in Basket, add it and sort
            super.add(product);
        }
    }

    @Override
    public boolean add(Product product) {
        boolean result;
        int i = super.indexOf(product);

        if (i != -1) {
            Product existingProduct = super.get(i);
            existingProduct.setQuantity(existingProduct.getQuantity() + 1);
            result = true;
        } else {
            result = super.add(product);
        }

        return result;
    }
}