package BYT;

public class Drink {
    private long drinkVolume;

    public Drink(long drinkVolume) {
        this.drinkVolume = Validator.validateNonZeroPhysicalAttribute(drinkVolume);
    }

    public long getDrinkVolume() {
        return drinkVolume;
    }

    public void setDrinkVolume(long drinkVolume) {
        this.drinkVolume = Validator.validateNonZeroPhysicalAttribute(drinkVolume);
    }
}
