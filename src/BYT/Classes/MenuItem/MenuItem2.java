package BYT.Classes.MenuItem;

import BYT.Classes.Order.OrderMenuItem;
import BYT.Helpers.Validator;

import java.io.Serializable;
import java.util.*;

public class MenuItem implements Serializable {
    private static final List<MenuItem> extent = new ArrayList<>();

    private String name;
    private String description;
    private long price;

    private Set<OrderMenuItem> orderMenuItems; // [0..*]
    // Normal, Vegan; Food, Drink = multi-aspect inheritance

    public MenuItem(String name, String description, long price) {
        this.name = Validator.validateAttributes(name);
        this.description = Validator.validateAttributes(description);
        this.price = Validator.validatePrice(price);
        orderMenuItems = new HashSet<>();

        extent.add(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = Validator.validateAttributes(description);
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Set<OrderMenuItem> getOrderMenuItems() {
        return Collections.unmodifiableSet(orderMenuItems);
    }

    public void addOrderMenuItem(OrderMenuItem orderMenuItem) {
        if(orderMenuItem == null) throw new IllegalArgumentException("OrderMenuItem cannot be null");
        orderMenuItems.add(orderMenuItem);
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return price == menuItem.price && Objects.equals(name, menuItem.name) && Objects.equals(description, menuItem.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price);
    }
}
