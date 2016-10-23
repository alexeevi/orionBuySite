package ivan.data;

import junit.framework.TestCase;

public class StockTest extends TestCase {
    public void testDecreaseQuantity() throws Exception {
        Stock stock = new Stock();
        stock.setQuantity(100);
        stock.decreaseQuantity(30);
        assertEquals(stock.getQuantity(), (Integer) 70);
    }

}