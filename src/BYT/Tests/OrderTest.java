package BYT.Tests;

import BYT.Classes.Menu.Menu;
import BYT.Classes.MenuItem.MenuItem;
import BYT.Classes.Order.Order;
import BYT.Classes.Order.OrderMenuItem;
import BYT.Classes.Order.OrderStatus;
import BYT.Classes.Person.Waiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest extends TestBase<Order> {

    protected OrderTest(){
        super(Order.class);
    }

    private Order order;
    private Menu testMenu;

    @BeforeEach
    void setup(){
        clearExtentInMemoryList();

        testMenu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
        order = new Order(1, null, new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu));
    }

    // extent

    @Test
    void testPersistence_SavingAndLoading() throws IOException, ClassNotFoundException {
        List<Order> list = new ArrayList<>();
        //Order order = new Order();
        list.add(order);
        order.prepare();
        order.serve();

        testPersistence(list); // tests that the status is Served too
    }

    // Order-only attributes and methods

    @Test
    void checkOrderBeingPrepareStatusUpdated() {
        //Order order = new Order();
        order.prepare();
        assertEquals(order.getStatus(), OrderStatus.InPREPARATION, "Order didn't changed its status to PREPARE!");
    }

    @Test
    void checkOrderBeingCreatedStatus() {
        //Order order = new Order();
        assertEquals(order.getStatus(), OrderStatus.CREATED, "Order status is not CREATED! Order failure to be Created!");
    }

    @Test
    void checkOrderHasBeenCancelled() {
        //Order order = new Order();
        order.cancelled();
        assertEquals(order.getStatus(), OrderStatus.CANCELLED, "Order did not cancelled! Failure to cancel!");
    }

    @Test
    void checkOrderCancelAfterBeingPreparedToBeFailureOrThrowsException(){
        //Order order = new Order();
        order.prepare();
        order.serve();
        assertThrows(IllegalStateException.class,
                () -> order.cancelled(), "Order can not be cancelled, after being served!");
    }

    @Test
    void checkOrderCanBeServedOnlyAfterPreparingOrThrowsException() {
        //Order order = new Order();
        assertThrows(IllegalStateException.class,
                () -> order.serve(), "Order can not be served, if it is not prepared!");
    }

    // associations

    @Test
    void addItemsToOrderAfterPrepareThrows(){
        order.prepare();
        assertThrows(IllegalStateException.class, () -> {
            order.createOrderMenuItem(2, null, new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu));
        });
    }

    @Test
    void addItemsToOrderAfterServedThrows(){
        order.prepare();
        order.serve();
        assertThrows(IllegalStateException.class, () -> {
            order.createOrderMenuItem(2, null, new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu));
        });
    }

    @Test
    void addItemsToOrderAfterCancelledThrows(){
        order.cancelled();
        assertThrows(IllegalStateException.class, () -> {
            order.createOrderMenuItem(2, null, new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu));
        });
    }

    @Test
    void removeItemsFromOrderAfterPrepareThrows(){
        order.createOrderMenuItem(3, "test1", new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu));
        order.prepare();
        assertThrows(IllegalStateException.class, () -> {
           order.deleteOrderMenuItem(order.getOrderMenuItems().iterator().next());
        });
    }

    @Test
    void removeItemsFromOrderAfterServedThrows(){
        order.createOrderMenuItem(3, "test1", new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu));
        order.prepare();
        order.serve();
        assertThrows(IllegalStateException.class, () -> {
            order.deleteOrderMenuItem(order.getOrderMenuItems().iterator().next());
        });
    }

    @Test
    void removeItemsFromOrderAfterCancelledThrows(){
        order.createOrderMenuItem(3, "test1", new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu));
        order.cancelled();
        assertThrows(IllegalStateException.class, () -> {
            order.deleteOrderMenuItem(order.getOrderMenuItems().iterator().next());
        });
    }

    @Test
    void removeOnlyItemFromOrderThrows(){
        assertEquals(1, order.getOrderMenuItems().size());

        OrderMenuItem orderMenuItem = order.getOrderMenuItems().iterator().next();

        assertThrows(IllegalStateException.class, () -> {
            order.deleteOrderMenuItem(orderMenuItem);
        });
    }

    @Test
    void checkOrderAddingItemsReverseConnectionAndTotalPrices(){
        assertEquals(1, order.getOrderMenuItems().size());

        MenuItem menuItem1 = new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu);
        MenuItem menuItem2 = new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu);

        OrderMenuItem omu1 = order.createOrderMenuItem(12, "test1", menuItem1);
        OrderMenuItem omu2 = order.createOrderMenuItem(31, "test2", menuItem2);
        assertEquals(3, order.getOrderMenuItems().size(), "Order should have 3 junction classes (OrderMenuItem)");
        assertEquals(7 * (31+12+1), order.getTotalPrice(), "Total price should be correct");

        assertTrue(order.getOrderMenuItems().contains(omu1), "Order should be linked to omu1");
        assertTrue(order.getOrderMenuItems().contains(omu2), "Order should be linked to omu2");

        assertEquals(omu1.getOrder(), order, "omu1 should be linked to the Order which created it");
        assertEquals(omu2.getOrder(), order, "omu2 should be linked to the Order which created it");

        assertEquals(omu1.getMenuItem(), menuItem1, "omu1 should be linked to menuItem1");
        assertEquals(omu2.getMenuItem(), menuItem2, "omu2 should be linked to menuItem2");

        assertTrue(menuItem1.getOrderMenuItems().contains(omu1), "menuItem1 should be linked to omu1");
        assertTrue(menuItem2.getOrderMenuItems().contains(omu2), "menuItem2 should be linked to omu2");

        order.deleteOrderMenuItem(omu1);
        order.deleteOrderMenuItem(omu2);
    }

    @Test
    void deletingLastOrderMenuItemForOrderThrows(){
        assertThrows(IllegalStateException.class, () -> {
            order.deleteOrderMenuItem(order.getOrderMenuItems().iterator().next());
        });
    }

    @Test
    void addNullMenuItemThrows(){
        assertThrows(IllegalArgumentException.class, () -> order.createOrderMenuItem(3, "test1", null));
    }

    @Test
    void addEmptyStringOrderNotesThrows(){
        assertThrows(IllegalArgumentException.class, () -> order.createOrderMenuItem(3, "", new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu)));
    }

    @Test
    void removeNullMenuItemThrows(){
        assertThrows(Exception.class, () -> order.deleteOrderMenuItem(null));
    }
}
