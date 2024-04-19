public class Drinks {
    private double water = 0;
    private double soda = 0;
    private double coffee = 0;

    public void setWater(double water) {
        this.water = water;
    }

    public void setSoda(double soda) {
        this.soda = soda;
    }

    public void setCoffee(double coffee) {
        this.coffee = coffee;
    }

    public double getItemPrice(String item){
        return switch (item) {
            case "water" -> water;
            case "soda" -> soda;
            case "coffee" -> coffee;
            default -> 0;
        };
    }
}
