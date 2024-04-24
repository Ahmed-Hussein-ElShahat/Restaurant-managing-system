package Backend;

import java.util.ArrayList;
import java.util.Arrays;

public class Order {
    private ArrayList<Item> guestOrder;             // Guest's order
    private static int TotalNumberOfOrders = 0;     // number of orders by all guests

    public Order(Item[] guestOrder){
        TotalNumberOfOrders++;
        this.guestOrder = new ArrayList<>(Arrays.asList(guestOrder));
    }

    public Order(){
        TotalNumberOfOrders++;
    }

    public Item getOrder(int n){return guestOrder.get(n);}          //return 1 specific item only

    public Item[] getOrder(){return new Item[guestOrder.size()];}   //return the total order in the shape of an array

    public static int getTotalNumberOfOrders() {return TotalNumberOfOrders;}

    public int getNumOfItems(){return guestOrder.size();}

    public void setGuestOrder(ArrayList<Item> guestOrder){this.guestOrder = guestOrder;}

    public void setGuestOrder(Item[] guestOrder){this.guestOrder = new ArrayList<>(Arrays.asList(guestOrder));}

    public void addOrder(Item newOrder) {guestOrder.add(newOrder);}

    public void removeOrder(int n) {guestOrder.remove(n);}
    // Need a function that is passed the Item to be removed

    public void updatingOrder(int n,Item update){guestOrder.set(n,update);}
    // Same here

    double getTotalPrice ( Order order ) {
        double total = 0 ;
        for (int i = 0; i < order.getNumOfItems(); i++) {
            total += order.getOrder(i).getPrice() ;
        }
        return total ;
    }

}
