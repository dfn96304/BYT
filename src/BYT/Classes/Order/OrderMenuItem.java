package BYT.Classes.Order;

import BYT.Classes.MenuItem.MenuItem;
import BYT.Helpers.Validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// association class converted to junction class
public class OrderMenuItem implements Serializable {
    //private static List<OrderMenuItem> extent = new ArrayList<>();
    private int quantity;
    private String orderNotes; // [0..1]

    private Order order; // 1
    private MenuItem menuItem; // 1

    public OrderMenuItem(int quantity, String orderNotes, Order order, MenuItem menuItem) {
        this.quantity = Validator.validateNonZeroPhysicalAttribute(quantity);
        this.orderNotes = Validator.validateOptionalAttributes(orderNotes);

        if(order == null) throw new IllegalArgumentException("Order cannot be null");
        this.order = order;
        if(menuItem == null) throw new IllegalArgumentException("MenuItem cannot be null");
        this.menuItem = menuItem;
        menuItem.addOrderMenuItem(this);
    }

    //public int getLineNumber() {
        //return order.getOrderMenuItems().
    //}

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = Validator.validateNonZeroPhysicalAttribute(quantity);;
    }

    public String getOrderNotes() {
        return orderNotes;
    }

    public void setOrderNotes(String orderNotes) {
        this.orderNotes = Validator.validateAttributes(orderNotes);;
    }

    public Order getOrder() {
        return order;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }
}
