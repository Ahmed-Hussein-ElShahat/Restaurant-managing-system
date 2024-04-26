package Backend.Payments;

import Backend.Payment;

public class Cash extends Payment{

    @Override
    public String getMethod() {
        return "Cash";
    }
    
    public static double calcRest (double totalPrice , double amount) {  //الباقي
        return amount - totalPrice; 
    }
}
