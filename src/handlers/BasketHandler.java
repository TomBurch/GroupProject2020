package handlers;

import org.jetbrains.annotations.NotNull;
import trade.Basket;
import trade.Product;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for handling management of a Basket
 */
public class BasketHandler {
    private Basket basket = new Basket();
    /**
     * List of Product line summaries, used in JLists
     */
    private DefaultListModel listModel = new DefaultListModel();
    /**
     * Pattern used to extract ISBN from Product line summaries
     */
    private final Pattern isbnPattern = Pattern.compile("\\S+\\s+([0-9-]+)");

    /**
     * Copy all Products from given Basket into this Basket
     */
    public void addBasket(@NotNull Basket newBasket) {
        for (Product product : newBasket) {
            basket.add(product, product.getQuantity());
        }
        refreshListModel();
    }

    public void addProductToBasket(Product product) {
        basket.add(product, product.getQuantity());
        refreshListModel();
    }

    public void clearBasket() {
        basket = new Basket();
        listModel = new DefaultListModel();
    }

    public void deleteProductFromBasket(Product product) {
        basket.remove(product);
        refreshListModel();
    }

    /**
     * Remake the listModel, used when Basket has been updated
     */
    public void refreshListModel() {
        DefaultListModel newListModel = new DefaultListModel();
        basket.forEach(product ->
                newListModel.addElement(product.getLineSummary())
        );
        listModel = newListModel;
    }

    public Basket getBasket() {
        return basket;
    }

    /**
     * Get total size of the basket (sum of Product.quantity)
     */
    public int getBasketSize() {
        return basket.getTotalSize();
    }

    /**
     * Get total price of the basket (sum of Product.quantity * Product.price)
     */
    public float getBasketPrice() {
        return basket.getTotalPrice();
    }

    public DefaultListModel getListModel() {
        return listModel;
    }

    /**
     * @return Product or null
     */
    public Product getProductFromLineSummary(String lineSummary) {
        Matcher matcher = isbnPattern.matcher(lineSummary);
        if (matcher.find()) {
            String isbn = matcher.group(1);
            return basket.getProductFromISBN(isbn);
        }
        return null;
    }
}