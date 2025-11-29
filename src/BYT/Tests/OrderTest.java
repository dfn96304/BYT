package BYT.Tests;

import BYT.Classes.MenuItem.MenuItem;
import BYT.Classes.Order.Order;
import BYT.Classes.Order.OrderStatus;
import BYT.Classes.Person.Waiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderTest extends TestBase<Order> {

    protected OrderTest(){
        super(Order.class);
    }

    private Order order;

    @BeforeEach
    void setup(){
        clearExtentInMemoryList();

        order = new Order(1, null, new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7));
    }

    @Test
    void checkOrderAddingItemsAndTotalPrice(){
        order.addOrderMenuItem(3, "test1", new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7));
        order.addOrderMenuItem(5, "test2", new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7));
        assertEquals(7 * (5+3+1), order.getTotalPrice());
    }

    @Test
    void testPersistence_SavingAndLoading() throws IOException, ClassNotFoundException {
        List<Order> list = new ArrayList<>();
        //Order order = new Order();
        list.add(order);
        order.prepare();
        order.serve();

        testPersistence(list); // tests that the status is Served too
    }

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

    @Test
    void addItemsToOrderAfterPrepareThrows(){
        order.prepare();
        assertThrows(IllegalStateException.class, () -> {
            order.addOrderMenuItem(2, null, new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7));
        });
    }

    @Test
    void addItemsToOrderAfterServedThrows(){
        order.prepare();
        order.serve();
        assertThrows(IllegalStateException.class, () -> {
            order.addOrderMenuItem(2, null, new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7));
        });
    }

    @Test
    void addItemsToOrderAfterCancelledThrows(){
        order.cancelled();
        assertThrows(IllegalStateException.class, () -> {
            order.addOrderMenuItem(2, null, new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7));
        });
    }

}
