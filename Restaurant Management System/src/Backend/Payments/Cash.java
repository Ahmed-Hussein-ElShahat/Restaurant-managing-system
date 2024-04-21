package Backend.Payments;

import Backend.Payment;

public class Cash extends Payment{

    @Override
    public String getMethod() {
        return "Cash";
    }
    @Override
    public double calcRest (double amount) {  //الباقي
        return super.getAmount() - amount;
    }
}
