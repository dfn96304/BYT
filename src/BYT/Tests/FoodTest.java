package BYT.Tests;

import BYT.Classes.MenuItem.Food;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FoodTest extends TestBase<Food> {

    protected FoodTest() {
        super(Food.class);
    }

    @BeforeEach
    void setUp() {
        clearExtentInMemoryList();
    }

    @Test
    void checkFoodWeightNonZeroAttribute() {
        assertThrows(IllegalArgumentException.class,
                () -> new Food("A", "B", 20, 0),
                "Weights of physical attributes bust be > 0!"
        );
    }

    @Test
    void checkPriceNonZero() {
        assertThrows(IllegalArgumentException.class,
                () -> new Food("A", "B", 0, 10),
                "Price must be greater than 0!"
                );
    }

    @Test
    void checkPriceNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> new Food("A", "B", -10, 10),
                "Price must be positive number!"
        );
    }
}
