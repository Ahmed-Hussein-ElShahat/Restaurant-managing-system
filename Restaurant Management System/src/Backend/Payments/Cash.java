package Backend.Payments;

import Backend.Payment;

public class Cash extends Payment{

    @Override
    public String getMethod() {
        return "Cash";
    }
}
