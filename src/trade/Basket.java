package trade;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Basket extends ArrayList<Product> {
    private final ProductIDComparator comparator = new ProductIDComparator();

    public Basket() {
    }

    public Product getProduct(Product product) {
        int i = super.indexOf(product);

        if (i != -1) {
            return super.get(i);
        } else {
            return null;
        }
    }

    public int getTotalSize() {
        return super.stream().mapToInt(Product::getQuantity).sum();
    }

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

    public void add(Product product, int quantity) {
        int i = super.indexOf(product);

        if (i != -1) {
            Product existingProduct = super.get(i);
            existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
        } else {
            super.add(product);
            super.sort(comparator);
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
            super.sort(comparator);
        }

        return result;
    }

    static class ProductIDComparator implements Comparator<Product> {
        public int compare(@NotNull Product p1, @NotNull Product p2) {
            String p1ID = p1.getProductID();
            String p2ID = p2.getProductID();
            return p1ID.compareTo(p2ID);
        }
    }
}
