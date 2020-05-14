package handlers;

import org.jetbrains.annotations.NotNull;
import trade.Basket;
import trade.Product;

import javax.swing.*;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasketHandler {
    private Basket basket = new Basket();
    private DefaultListModel listModel = new DefaultListModel();

    private Pattern isbnPattern = Pattern.compile("\\S+\\s+([0-9-]+)");

    public void addBasket(@NotNull Basket newBasket) {
        ListIterator<Product> iterator = newBasket.listIterator();

        while (iterator.hasNext()) {
            Product product = iterator.next();
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

    public void refreshListModel() {
        DefaultListModel newListModel = new DefaultListModel();
        basket.forEach(product -> {
            newListModel.addElement(product.getLineSummary());
        });
        listModel = newListModel;
    }

    public int getBasketSize() { return basket.getTotalSize(); }

    public float getBasketPrice() { return basket.getTotalPrice(); }

    public Basket getBasket() { return basket; }

    public DefaultListModel getListModel() { return listModel; }

    public Product getProductFromLineSummary(String lineSummary) {
        Matcher matcher = isbnPattern.matcher(lineSummary);
        if (matcher.find()) {
            String isbn = matcher.group(1);
            return basket.getProductFromISBN(isbn);
        }
        return null;
    }

}