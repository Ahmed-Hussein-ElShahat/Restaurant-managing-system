package Backend;

import javafx.beans.property.SimpleIntegerProperty;

public class Table implements Available{
    static private int table_num = 0;   // Total number of tables
    private Order order;                // On table order
    private SimpleIntegerProperty table_id = new SimpleIntegerProperty();            // Table number
    private SimpleIntegerProperty table_capacity = new SimpleIntegerProperty(5);     // Default capacity
    private boolean available = true;   // Whether the table is occupied  


    public Table () {
        table_id.set(++table_num);
    }

    // Maybe needed
/*     Table (Order order) {
        this.order = order;
        table_id = ++table_num;
        available = false;
    } */
    public Table (int table_capacity) {
        table_id.set(++table_num);
        this.table_capacity.set(table_capacity);
    } 
    public static int getTable_num() {
        return table_num;
    }
    public Order getOrder() {
        return order;
    }
    public int getTable_id() {
        return table_id.get();
    }
    public SimpleIntegerProperty getTable_IDProperty() {
        return table_id;
    }

    public int getTable_capacity() {
        return table_capacity.get();
    }
    public SimpleIntegerProperty getTable_CapacityProperty() {
        return table_capacity;
    }
    public boolean isAvailable() {
        return available;
    }
    public static void resetTable_num() {
        table_num = 0;
    }
    public boolean isAvailable(int people) throws IllegalArgumentException {
        if (people <= 0) throw new IllegalArgumentException("people must be positive");
        return available && people <= table_capacity.get();
    }
    public static void remove_table() {
        Table.table_num--;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public void setTable_capacity(int table_capacity) {
        this.table_capacity.set(table_capacity);
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }
    public void preserve() {
        this.available = false;
    }
    public void preserve(Order order) {
        available = false;
        this.order = order;
    }
    // public double checkout() {
    //     available = true;
    //     return order.getTotalPrice();
    // }

    @Override
    public boolean checkIfAvailable() {
        return available;
    }

    public void setAvailability(boolean available) {
        this.available = available;
    }
}