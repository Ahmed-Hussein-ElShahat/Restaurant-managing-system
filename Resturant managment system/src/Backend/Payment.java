package Backend;

public abstract class Payment {
    String method; // May not be needed
    double amount;

    public Payment() {
    }
    public Payment(String method, double amount) {
        this.method = method;
        this.setAmount(amount);
    }

    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
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

    public void print(){
        System.out.println(this.getMethod() + " " + this.getAmount()) ;
    }
    
}
