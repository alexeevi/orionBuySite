package ivan.controller;

import ivan.data.Order;
import ivan.data.Shop;
import ivan.data.Stock;
import ivan.service.OrderProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class MainController {

    final public static String STOCK_PREFIX = "stock_";

    private Shop shop;

    @RequestMapping(value = "/")
    public String home(Model model) {
        model.addAttribute("stocks", shop.getStocks().values());
        return "main";
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String order(HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String,String> errors = new HashMap<String, String>();
        ResourceBundle messages = ResourceBundle.getBundle("messages");
        Order order = new Order();

        if (request != null) {
            // iterate parameters, pull having prefix and validate
            Enumeration parameters = request.getParameterNames();
            while (parameters.hasMoreElements()) {
                String name = parameters.nextElement().toString();
                if (name.startsWith(STOCK_PREFIX)) {
                    String valueStr = request.getParameter(name);
                    if (!"".equals(valueStr)) {
                        Long stockId = Long.parseLong(name.substring(name.indexOf(STOCK_PREFIX) + STOCK_PREFIX.length()));
                        Stock stock = shop.getStocks().get(stockId);
                        if (stock != null) {
                            Integer value = null;
                            String error = null;
                            try {
                                value = Integer.parseInt(valueStr);
                                if (value < 0) {
                                    error = messages.getString("error.quantity.negative");
                                }
                            } catch (NumberFormatException nfex) {
                                error = messages.getString("error.quantity.notinteger");
                            }

                            if (error != null) {
                                errors.put(stockId.toString(), error);
                            } else {
                                order.getOrdered().put(stock, value);
                            }
                        }
                    }
                }
            }
        }

        // check quantities against available amount and commit order or accumulate errors
        if (order.getOrdered().size() != 0) {
            OrderProcessor orderProcessor = new OrderProcessor(shop, order);
            if (orderProcessor.checkQuantities() && errors.size() == 0) {
                orderProcessor.commitOrder();
            }
            for (Stock stock : orderProcessor.getBadStocks()) {
                errors.put(stock.getId().toString(), messages.getString("error.quantity.toomuch"));
            }
        }

        if (errors.size() == 0 && order.getOrdered().size() != 0) {
            model.addAttribute("order", order);
            return "shopping";
        } else {
            // show products table again with errors
            model.addAttribute("stocks", shop.getStocks().values());
            model.addAttribute("errors", errors);
            return "main";
        }
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
