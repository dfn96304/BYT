package BYT;

public class Customer extends Person {
    public long loyaltyPoints;

    public Customer(String firstName, String lastName, String phoneNumber, String email, long loyaltyPoints) {
        super(firstName, lastName, phoneNumber, email);
        this.loyaltyPoints = loyaltyPoints;
    }

    public long getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(long loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }
}
