public class Appetizers {
    private double soup = 0;
    private double salad = 0;
    private double garlicBread = 0;

    public void setSoup(double soup) {
        this.soup = soup;
    }

    public void setSalad(double salad) {
        this.salad = salad;
    }

    public void setGarlicBread(double garlicBread) {
        this.garlicBread = garlicBread;
    }

    public double getItemPrice(String item) {
        return switch (item) {
            case "Soup" -> soup;
            case "Salad" -> salad;
            case "GarlicBread" -> garlicBread;
            default -> 0;
        };
    }
}
