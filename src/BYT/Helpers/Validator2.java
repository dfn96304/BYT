package BYT.Helpers;

import BYT.Classes.Person.Person;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
// +48 999-999-999
public class Validator2 {
    public static String validatePhoneNumber(String phoneNumber) throws IllegalArgumentException{
        if(phoneNumber.startsWith("+48")){
            for(int i=4;i<phoneNumber.length();i++){
                if(i==7 || i==11){
                    continue;
                }else{
                    if (!Character.isDigit(phoneNumber.charAt(i))) {
                        throw new IllegalArgumentException("Invalid phone number");
                    }
                }
            }

        }else{
            throw new IllegalArgumentException();
        }
        return phoneNumber;
    }
    public static String validateStringMandatoryValues(String value) throws IllegalArgumentException {
        if(value==null|| value.isEmpty()||value.trim().equals("")){
            throw new IllegalArgumentException();
        }
        return value;
    }
    public static int validateIntMandatoryValues(int value) throws IllegalArgumentException {
        if ( value==0) {
            throw new IllegalArgumentException();
        }
        return value;
    }
    public static int validateNegativeNumbers(int value) throws IllegalArgumentException {
        if ( value<0) {
            throw new IllegalArgumentException();
        }
        return value;
    }
    public static LocalDate validateDate(LocalDate date) throws DateTimeException {
        if(date==null || date.isAfter(LocalDate.now())){
            throw new IllegalArgumentException();
        }
        return date;
    }
    public static int compareInts(int _new, int base) {
        if(_new<base){
            throw new IllegalArgumentException();
        }
        return _new;
    }
    public static String validateEmail(String email) throws IllegalArgumentException {
        if(email==null||email.isEmpty()||!email.endsWith("@gmail.com")){
            throw new IllegalArgumentException();
        }
        return email;
    }
}
