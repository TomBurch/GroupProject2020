package handlers;

import trade.Basket;
import trade.Product;

import javax.swing.*;
import java.util.ListIterator;

public class BasketHandler {
    private Basket basket = new Basket();
    private DefaultListModel listModel = new DefaultListModel();

    public void addBasket(Basket newBasket) {
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

    public DefaultListModel getListModel() { return listModel; }

    public int getBasketSize() { return basket.getTotalSize(); }

    public float getBasketPrice() { return basket.getTotalPrice(); }

    public Basket getBasket() { return basket; }

    public void refreshListModel() {
        DefaultListModel newListModel = new DefaultListModel();
        basket.forEach(product -> {
           newListModel.addElement(product.getLineSummary());
        });
        listModel = newListModel;
    }
}