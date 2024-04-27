package Backend.Payments;

import Backend.Payment;
import java.lang.IllegalArgumentException; 

public class Visa extends Payment {
    private String id ;

    public Visa(){
        this.id = "0000000000000000";
    }
    
    public Visa(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
// make exception 
    public void setId(String id) throws IllegalArgumentException {
        
        if ( id.length() == 16 ) {
            for ( int i = 0; i < id.length() ; i++ ) {
                if ( !(Character.isDigit(id.charAt(i))) ) throw new IllegalArgumentException("illegal character")  ;
            }
            this.id = id;
        }
        else throw new IllegalArgumentException("illegal length") ;

    }
    @Override
    public String getMethod() {
        return "Visa";
    }

}
