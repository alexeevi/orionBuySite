package ivan.data;

import java.util.HashMap;
import java.util.Map;

public class Shop {

    private Object orderLock = new Object();
    private Map<Long, Stock> stocks = new HashMap<Long, Stock>();

    public Map<Long, Stock> getStocks() {
        return stocks;
    }

    public void setStocks(Map<Long, Stock> stocks) {
        this.stocks = stocks;
    }

    public Object getOrderLock() {
        return orderLock;
    }
}
