package BYT;


public class Customer extends Person {
    private long loyaltyPoints;

    public Customer(String firstName, String lastName, String phoneNumber, String email, long loyaltyPoints) {
        super(firstName, lastName, phoneNumber, email);
        this.loyaltyPoints = Validator.negativeNumberEntered(loyaltyPoints);
    }

    public long getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(long loyaltyPoints) {
        this.loyaltyPoints = Validator.negativeNumberEntered(loyaltyPoints);
    }

    public void addLoyaltyPoints(long add) {
        if(add <= 0){
            throw new IllegalArgumentException("Added number of loyalty points must be greater than 0.");
        }
        this.loyaltyPoints += add;
    }
}