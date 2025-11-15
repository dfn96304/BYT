package BYT.Tests;
import BYT.Classes.Person.Chef;
import BYT.Classes.Person.Waiter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import BYT.Classes.Person.Employee;

public class EmployeeTest {




    @Test
    void salaryCannotBeLowerThanBaseSalaryTest(){

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Employee waiter=new Waiter("John","Doe","555555555",
                    "example@gmail.com",4000);
        });

    }
    @Test
    void  emailCannotBeEmptyTest(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Employee emp=new Chef("John","Doe","55555555","",8000);
        });
    }
    @Test
    void youCannotSetSalaryLowerThanBaseSalaryTest(){
        Employee emp=new Waiter("John","Doe","55555555",
                "example@gmail.com",8000);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            emp.setSalary(5000);
        });
    }
    void youCannotSetBaseSalaryNegativeTest(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Employee.setBaseSalary(-1000);
        });
    }




}
