import Backend.Item;

public class Item {
    private MainCourse m = new MainCourse();
    private Drink d = new Drink();
    private Appetizer a = new Appetizer();

    public double getMainCourseItemPrice(String item) {
        return m.getItemPrice(item);
    }

    public double getDrinksItemPrice(String item) {
        return d.getItemPrice(item);
    }

    public double getAppetizerItemPrice(String item) {
        return a.getItemPrice(item);
    }

    public void setAppetizersItemPrice(String item, double price) {
        switch (item){
            case "Soup" -> a.setSoup(price);
            case "Salad" -> a.setSalad(price);
            case "GarlicBread" -> a.setGarlicBread(price);
        }
    }

    public void setMainCourseItemPrice(String item, double price) {
        switch (item){
            case "Pizza" -> m.setPizza(price);
            case "Pasta" -> m.setPasta(price);
            case "Lasagna" -> m.setLasagna(price);
            case "Risotto" -> m.setRisotto(price);
        }
    }

    public void setDrinksItemPrice(String item, double price) {
        switch (item){
            case "water" -> d.setWater(price);
            case "soda" -> d.setSoda(price);
            case "coffee" -> d.setCoffee(price);
        }
    }
}
