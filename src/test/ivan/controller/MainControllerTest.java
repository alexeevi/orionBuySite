package ivan.controller;

import junit.framework.TestCase;
import org.springframework.validation.support.BindingAwareModelMap;

public class MainControllerTest extends TestCase {

    private ivan.controller.MainController controller;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        controller = new ivan.controller.MainController();
        controller.setShop(new ivan.data.Shop());
    }

    public void testHome() throws Exception {
        assertEquals(controller.home(new BindingAwareModelMap()), "main");
    }

    public void testOrder() throws Exception {
        assertEquals(controller.order(null, null, new BindingAwareModelMap()), "main");

    }

}