package Backend;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Order  {
    private LocalDate date;
    private LocalTime time;
    private ArrayList<Item> guestOrder = new ArrayList<>();        // Guest's order
    private SimpleDoubleProperty totalPrice = new SimpleDoubleProperty();
    private final SimpleIntegerProperty orderNum = new SimpleIntegerProperty();
    private static int TotalNumberOfOrders = 0;     // number of orders by all guests

    public Order(Item[] guestOrder){
        date = LocalDate.now();
        time = LocalTime.now();
        orderNum.set(++TotalNumberOfOrders);
        this.guestOrder = new ArrayList<>(Arrays.asList(guestOrder));
    }

    public Order(){
        date = LocalDate.now();
        time = LocalTime.now();
        orderNum.set(++TotalNumberOfOrders);
    }

    
    
    public SimpleIntegerProperty getOrderNumProperty() {
        return orderNum;
    }
    public int getOrderNum() {
        return orderNum.get();
    }

    public Item getOrder(int n){return guestOrder.get(n);}          //return 1 specific item only

    public Item[] getOrder(){return new Item[guestOrder.size()];}   //return the total order in the shape of an array

    public static int getTotalNumberOfOrders() {return TotalNumberOfOrders;}

    public int getNumOfItems(){return guestOrder.size();}

    public void setGuestOrder(ArrayList<Item> guestOrder){
        this.guestOrder = guestOrder;
        totalPrice.set(calcTotalPrice());
    }

    public void setGuestOrder(Item[] guestOrder){
        this.guestOrder = new ArrayList<>(Arrays.asList(guestOrder));
        totalPrice.set(calcTotalPrice());
    }

    public void addOrder(Item newOrder) {
        guestOrder.add(newOrder);
        totalPrice.set(calcTotalPrice());
    }

    public void removeOrder(int n) {
        guestOrder.remove(n);
        totalPrice.set(calcTotalPrice());
    }
    // Need a function that is passed the Item to be removed

    public void updatingOrder(int n,Item update){
        guestOrder.set(n,update);
        totalPrice.set(calcTotalPrice());
    }
    // Same here

    public double calcTotalPrice() {
        double total = 0 ;
        for (int i = 0; i < this.getNumOfItems(); i++) {
            total += this.getOrder(i).getPrice() ;
        }
        return total ;
    }
    public double getTotalPrice() {
        return totalPrice.get();
    }
    public SimpleDoubleProperty getTotalPriceProperty() {
        return totalPrice;
    }

    public LocalDate getDate() {
        return date;
    }
    public LocalTime getTime() {
        return time.truncatedTo(ChronoUnit.SECONDS);
    }
}
