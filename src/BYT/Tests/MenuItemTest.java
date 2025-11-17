package BYT.Tests;

import BYT.Classes.MenuItem.MenuItem;
import BYT.Classes.MenuItem.Normal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MenuItemTest extends TestBase<MenuItem> {

    protected MenuItemTest() {
        super(MenuItem.class);
    }

    @BeforeEach
    void setUp() {
        clearExtentInMemoryList();
    }

    @Test
    void testMenuItemPersistence_SavingAndLoading() throws IOException, ClassNotFoundException {
        List<MenuItem> menuItem = new ArrayList<>();
        menuItem.add(new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7));
        testPersistence(menuItem);
    }

    @Test
    void emptyStringAttributesThrow(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MenuItem normal = new MenuItem("", "Marinated mixed olives with orange zest and herbs", 7);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MenuItem normal = new MenuItem("Citrus-Brined Olives", "", 7);
        });
    }

    @Test
    void nullNonOptionalAttributesThrow(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MenuItem normal = new MenuItem(null, "Marinated mixed olives with orange zest and herbs", 7);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MenuItem normal = new MenuItem("Citrus-Brined Olives", null, 7);
        });
    }

    @Test
    void zeroPricesThrow() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MenuItem normal = new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 0);
        });
    }

    @Test
    void negativePricesThrow(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MenuItem normal = new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", -3);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MenuItem normal = new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", -6);
        });
    }
}
