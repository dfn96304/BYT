package BYT;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class Validator {

    public static LocalDateTime validateDate(LocalDateTime startDate, LocalDateTime endDate) throws DateTimeException {
        if (startDate == null || endDate == null) {
            throw new DateTimeException("Dates must not be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new DateTimeException("Start Date should be before the end date!");
        }
        if (startDate.isEqual(endDate)) {
            throw new DateTimeException("End date has to be later than start date!");
        }
        return startDate;
    }

    public static double negativeNumberEntered(double value) throws IllegalArgumentException {
        if (value < 0) {
            throw new IllegalArgumentException("Value can not be negative!");
        }
        return value;
    }

    public static long negativeNumberEntered(long value) throws IllegalArgumentException {
        if (value < 0) {
            throw new IllegalArgumentException("Value can not be negative!");
        }
        return value;
    }

    public static long validateSalary(long salary) throws IllegalArgumentException {
        if(salary <= 0) {
            throw new IllegalArgumentException("Salary must be positive");
        }
        return salary;
    }

    public static long validateNonZeroPhysicalAttribute(long amount) throws IllegalArgumentException {
        if(amount <= 0) {
            throw new IllegalArgumentException("Physical attributes of items (volumes, weights) must be positive");
        }
        return amount;
    }

    public static String validateOptionalEmail(String email) throws IllegalArgumentException {
        if (email == null) return null;

        String trimmedEmail = email.trim();
        if (trimmedEmail.isEmpty()) return null;
        if (!trimmedEmail.contains("@")) {
            throw new IllegalArgumentException("Email address is invalid, include'@ symbol.");
        }
        return trimmedEmail;
    }


    public static String validateAttributes(String value) throws IllegalArgumentException {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("A value, should not be empty!");
        }
        return value.trim();
    }
}
