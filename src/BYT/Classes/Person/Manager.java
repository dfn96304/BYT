package BYT.Classes.Person;


import BYT.Helpers.Validator2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Manager implements Serializable{
    private String name;
    private String lastName;
    private String phoneNumber;
    private String email;
    private int salary;
    private static int baseSalary=9000;
    private int salaryBonus;
    LocalDate startDate;
    //optional and multi value
    private ArrayList<String> previouslyWorkedRestaturants;




    public Manager(String name, String lastName, String phoneNumber, String email, int salary
    ,LocalDate startDate, int salaryBonus) {
        this.name=Validator2.validateStringMandatoryValues(name);
        this.lastName=Validator2.validateStringMandatoryValues(lastName);
        this.email=Validator2.validateStringMandatoryValues(email);
        this.salary=Validator2.compareInts(salary,baseSalary);
        this.email=Validator2.validateEmail(email);
        this.startDate = Validator2.validateDate(startDate);
        this.phoneNumber=Validator2.validateStringMandatoryValues(phoneNumber);
        this.salaryBonus = Validator2.validateNegativeNumbers(salaryBonus);


    }

    //devried
    public int  getTotalSalary(){
        return (int) (salary+salaryBonus);
    }

    public String getLastName() {
        return lastName;
    }

    public int getSalary() {
        return salary;
    }



    public ArrayList<String> getPreviouslyWorkedRestaturants() {
        return previouslyWorkedRestaturants;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getSalaryBonus() {
        return salaryBonus;
    }

    public static int getBaseSalary() {
        return baseSalary;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public static void setBaseSalary(int baseSalary) {
        Manager.baseSalary = Validator2.validateNegativeNumbers(baseSalary);
    }
    public void managerWriter(){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("C:\\Users\\bebek\\IdeaProjects\\cover\\src\\BYT\\Files\\Manager.bin"))) {
            out.writeObject(this);
            System.out.println("Manager serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



