package BYT;

public class Employee extends Person {
    public long salary;
    public static long baseSalary = 6000;

    public Employee(String firstName, String lastName, String phoneNumber, String email, long salary) {
        super(firstName, lastName, phoneNumber, email);
        this.salary = salary;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public static long getBaseSalary() {
        return baseSalary;
    }

    public static void setBaseSalary(long baseSalary) {
        Employee.baseSalary = baseSalary;
    }
}
