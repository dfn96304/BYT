package BYT.Tests.Inheritance;
import BYT.Classes.Restaurant.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static BYT.Classes.Restaurant.MenuItem.DietInheritanceTypes.NORMAL;
import static BYT.Classes.Restaurant.MenuItem.DietInheritanceTypes.VEGAN;
import static java.awt.SystemColor.menu;
import static org.junit.jupiter.api.Assertions.*;

public class MenuItemInhTest {

    private Menu menu;

    @BeforeEach
    void setUp() {
        menu = new Menu(LocalDate.now(), LocalDate.now().plusDays(3));
    }

    // 1. CLASSICAL INHERITANCE (Food / Drink extends MenuItem)
    @Test
    void foodIsAMenuItem() {
        MenuItem food = new Food(
                "Pizza",
                "Pepperoni pizza",
                1200,
                300,
                menu,
                NORMAL
        );

        assertTrue(food instanceof MenuItem);
        assertTrue(food instanceof Food);
    }

    @Test
    void drinkIsAMenuItem() {
        MenuItem drink = new Drink(
                "Cola",
                "Sparkling soft drink",
                500,
                330,
                menu,
                VEGAN
        );

        assertTrue(drink instanceof MenuItem);
        assertTrue(drink instanceof Drink);
    }

    // =========================================================
    // 2. DISJOINT INHERITANCE (Normal XOR Vegan)
    // =========================================================

    @Test
    void normalMenuItemHasNormalPartOnly() {
        MenuItem item = new Food(
                "Burger",
                "Beef burger",
                1500,
                400,
                menu,
                NORMAL
        );

        assertNotNull(item.getNormalPart());
        assertNull(item.getVeganPart());
    }

    @Test
    void veganMenuItemHasVeganPartOnly() {
        MenuItem item = new Food(
                "Falafel",
                "Vegan falafel wrap",
                1300,
                350,
                menu,
                VEGAN
        );

        assertNotNull(item.getVeganPart());
        assertNull(item.getNormalPart());
    }



    // =========================================================
    // 3. TOTAL INHERITANCE (MenuItem MUST be Normal or Vegan)
    // =========================================================

    @Test
    void menuItemAlwaysHasExactlyOneDietType() {
        MenuItem normalItem = new Food(
                "Steak",
                "Grilled steak",
                2500,
                500,
                menu,
                NORMAL
        );

        MenuItem veganItem = new Food(
                "Tofu Bowl",
                "Tofu with veggies",
                1800,
                400,
                menu,
                VEGAN
        );

        assertTrue(
                (normalItem.getNormalPart() != null && normalItem.getVeganPart() == null)
        );

        assertTrue(
                (veganItem.getVeganPart() != null && veganItem.getNormalPart() == null)
        );
    }

    // =========================================================
    // 4. LIFECYCLE OWNERSHIP (composition behavior)
    // =========================================================

    @Test
    void deletingMenuItemDeletesDietPart() {
        MenuItem item = new Food(
                "Soup",
                "Tomato soup",
                700,
                300,
                menu,
                VEGAN
        );

        Vegan veganPart = item.getVeganPart();
        assertNotNull(veganPart);

        item.delete();

        assertFalse(Vegan.getExtent().contains(veganPart));
    }
}
