package BYT;

public class Food {
    private long foodWeight;

    public Food(long foodWeight) {
        this.foodWeight = Validator.validateNonZeroPhysicalAttribute(foodWeight);
    }

    public long getFoodWeight() {
        return foodWeight;
    }

    public void setFoodWeight(long foodWeight) {
        this.foodWeight = Validator.validateNonZeroPhysicalAttribute(foodWeight);
    }
}
