package Backend;

public abstract class Payment {
    private double amount;
    

    public Payment() {
    }
    public Payment(double amount) {
        this.setAmount(amount);
    }

    abstract public String getMethod();

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) throws IllegalArgumentException {
        if ( amount >= 0 ) {
            this.amount = amount;
        }
        else {
            throw new IllegalArgumentException ("the amount can't be negative");
        }
    }
    @Override
    public String toString(){
        return this.getMethod() + " " + this.getAmount();
    }
    
    



}
