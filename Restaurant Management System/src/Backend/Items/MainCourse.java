package Backend.Items;

public class MainCourse implements Available {
    private double Price;
    private final String Name;
    private boolean available = true;

    MainCourse(String Name, double Price) {
        this.Name = Name;
        this.setPrice(Price);
    }

    public String getName() {
        return this.Name;
    }

    public double getPrice() {
        return this.Price;
    }

    public void setPrice(double Price) {
        // will implement it with exception handling later
        if(Price > 0) {
            this.Price = Price;
        }
        else {
            this.Price = 0;
        }
    }

    public void setAvailability(boolean available) {
        this.available = available;
    }

    @Override
    public boolean checkIfAvailable() {
        return available;
    }
}
