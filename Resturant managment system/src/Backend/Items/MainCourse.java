public class MainCourse {
    private double pizza = 0;
    private double pasta = 0;
    private double lasagna = 0;
    private double risotto = 0;

    public void setPizza(double pizza) {
        this.pizza = pizza;
    }

    public void setPasta(double pasta) {
        this.pasta = pasta;
    }

    public void setLasagna(double lasagna) {
        this.lasagna = lasagna;
    }

    public void setRisotto(double risotto) {
        this.risotto = risotto;
    }

    public double getItemPrice(String item) {
        return switch (item) {
            case "Pizza" -> pizza;
            case "Pasta" -> pasta;
            case "Lasagna" -> lasagna;
            case "Risotto" -> risotto;
            default -> 0;
        };
    }
}
