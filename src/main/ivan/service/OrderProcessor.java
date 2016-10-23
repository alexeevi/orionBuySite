package ivan.service;

import ivan.data.Order;
import ivan.data.Shop;
import ivan.data.Stock;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OrderProcessor {

    private Shop shop;
    private Order order;
    private Set<Stock> badStocks = new HashSet<Stock>();

    public OrderProcessor(Shop shop, Order order) {
        this.shop = shop;
        this.order = order;
    }

    public Set<Stock> getBadStocks() {
        return badStocks;
    }

    public Boolean checkQuantities() {
        badStocks.clear();
        for (Map.Entry<Stock, Integer> entry : order.getOrdered().entrySet()) {
            Stock shopStock = shop.getStocks().get(entry.getKey().getId());
            if (shopStock != null && entry.getValue() > shopStock.getQuantity()) {
                badStocks.add(entry.getKey());
            }
        }
        return badStocks.size() == 0;
    }

    public Boolean commitOrder() {
        synchronized (shop.getOrderLock()) {
            // check quantities once again and commit the order
            if (checkQuantities()) {
                for (Map.Entry<Stock, Integer> entry : order.getOrdered().entrySet()) {
                    shop.getStocks().get(entry.getKey().getId()).decreaseQuantity(entry.getValue());
                }
            } else {
                return false;
            }
        }
        return true;
    }

}
