package trade;

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
        int size = super.stream().mapToInt(Product::getQuantity).sum();
        return size;
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

    class ProductIDComparator implements Comparator<Product> {
        public int compare(Product p1, Product p2) {
            String p1ID = p1.getProductID();
            String p2ID = p2.getProductID();
            return p1ID.compareTo(p2ID);
        }
    }
}
