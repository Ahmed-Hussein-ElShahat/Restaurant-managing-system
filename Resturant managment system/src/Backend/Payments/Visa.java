package Backend.Payments;

import Backend.Payment;

public class Visa extends Payment {
    private String id ;

    public Visa(){}
    
    public Visa(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
// make exception 
    public void setId(String id) throws Exception {
        
        if ( id.length() == 16 ) {
            for ( int i = 0; i < id.length() ; i++ ) {
                if ( !(Character.isDigit(id.charAt(i))) ) throw IllegalArgumentException("illegal character")  ;
            }
            this.id = id;
        }
        else throw IllegalArgumentException("illegal length") ;

    }
    
    

}
