package BYT.Tests;
import BYT.Classes.Person.Manager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

public class ManagerTest {


    @Test
    void emptyNameFieldThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Manager(
                    "",
                    "Dylan",
                    "+48 766-999-678",
                    "example@gmail.com",
                    10000,
                    LocalDate.of(2024, Month.MAY, 15),
                    2000
            );
        });
    }
    @Test
    void nullNameFieldThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Manager(
                    null,
                    "Dylan",
                    "+48 766-999-678",
                    "example@gmail.com",
                    10000,
                    LocalDate.of(2024, Month.MAY, 15),
                    2000
            );
        });
    }
    @Test
    void emptySurnameFieldThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Manager(
                    "John",
                    "",
                    "+48 766-999-678",
                    "example@gmail.com",
                    10000,
                    LocalDate.of(2024, Month.MAY, 15),
                    2000
            );
        });
    }
    @Test
    void nullSurnameFieldThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Manager(
                    "John",
                    null,
                    "+48 766-999-678",
                    "example@gmail.com",
                    10000,
                    LocalDate.of(2024, Month.MAY, 15),
                    2000
            );
        });
    }
    @Test
    void invalidPhoneNumberFieldThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Manager(
                    "John",
                    "Doe",
                    "+48 766-999-xxx",
                    "example@gmail.com",
                    10000,
                    LocalDate.of(2024, Month.MAY, 15),
                    2000
            );
        });
    }
    @Test
    void invalidEmailThrowsException(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Manager(
                    "John",
                    "Doe",
                    "+48 766-999-xxx",
                    "example@hghj",
                    10000,
                    LocalDate.of(2024, Month.MAY, 15),
                    2000
            );
        });
    }
    @Test
    void startDateInTheFutureThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Manager(
                    "John",
                    "Doe",
                    "+48 766-999-xxx",
                    "example@gmail.com",
                    10000,
                    LocalDate.of(2030, Month.MAY, 15),
                    2000
            );
        });
    }
    @Test
    void salaryBonusNegativeThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Manager(
                    "John",
                    "Doe",
                    "+48 766-999-xxx",
                    "example@gmail.com",
                    10000,
                    LocalDate.of(2024, Month.MAY, 15),
                    -700
            );
        });
    }
    @Test
    void baseSalaryNegativeThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Manager.setBaseSalary(-800);
        });
    }
    @Test
    void totalSalaryTest(){
        Manager manager = new Manager(
                "John",
                "Doe",
                "+48 766-999-111",
                "example@gmail.com",
                10000,
                LocalDate.of(2024, Month.MAY, 15),
                2000
        );

        Assertions.assertEquals(12000, manager.getTotalSalary());
    }
    @Test
    void managerPersistency(){
        Manager manager = new Manager(
                "John",
                "Doe",
                "+48 766-999-111",
                "example@gmail.com",
                10000,
                LocalDate.of(2024, Month.MAY, 15),
                2000
        );
        Assertions.assertDoesNotThrow(() -> manager.managerWriter());
    }


}
