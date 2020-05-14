package trade;

import javax.swing.*;

public class BasketHandler {
    private Basket basket = new Basket();
    private DefaultListModel listModel = new DefaultListModel();

    public DefaultListModel getListModel() {
        return listModel;
    }

    public int getBasketSize() {
        return basket.getTotalSize();
    }

    public float getBasketPrice() {
        return basket.getTotalPrice();
    }

    public void addProductToBasket(Product product) {
        Product existingProduct = basket.getProduct(product);
        if (existingProduct != null) {
            listModel.removeElement(existingProduct.getLineSummary());
        }
        basket.add(product);
        product = basket.getProduct(product);
        listModel.addElement(product.getLineSummary());
    }
}