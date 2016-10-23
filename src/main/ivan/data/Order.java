package ivan.data;

import java.util.HashMap;
import java.util.Map;

public class Order {

    private Map<Stock,Integer> ordered = new HashMap<Stock, Integer>();

    public Map<Stock, Integer> getOrdered() {
        return ordered;
    }

    public void setOrdered(Map<Stock, Integer> ordered) {
        this.ordered = ordered;
    }
}
