package Backend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

@JsonIncludeProperties({"id", "capacity", "available"})
public class Table implements Available{
    static private int table_num = 0;   // Total number of tables
    @JsonIgnore
    private Order order;            // On table order
    private SimpleIntegerProperty table_id = new SimpleIntegerProperty();            // Table number
    private SimpleIntegerProperty table_capacity = new SimpleIntegerProperty(5);     // Default capacity
    private SimpleBooleanProperty available = new SimpleBooleanProperty(true);   // Whether the table is occupied  

    public Table () {
        table_id.set(++table_num);
    }
    public Table (int id, int capacity, boolean available) {
        table_id.set(id);
        this.table_capacity.set(capacity);
        this.available.set(available);
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
    @JsonProperty("id")
    public int getTable_id() {
        return table_id.get();
    }
    @JsonProperty("id")
    public void setTable_id(int table_id) {
        this.table_id.set(table_id);
    }
    @JsonIgnore
    public SimpleIntegerProperty getTable_IDProperty() {
        return table_id;
    }
    @JsonProperty("capacity")
    public int getTable_capacity() {
        return table_capacity.get();
    }
    public SimpleIntegerProperty getTable_CapacityProperty() {
        return table_capacity;
    }
    public SimpleBooleanProperty getAvailableProperty() {
        return available;
    }
    public static void resetTable_num() {
        table_num = 0;
    }
    public boolean isAvailable(int people) throws IllegalArgumentException {
        if (people <= 0) throw new IllegalArgumentException("people must be positive");
        return available.get() && people <= table_capacity.get();
    }
    public static void remove_table() {
        Table.table_num--;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    @JsonProperty("capacity")
    public void setTable_capacity(int table_capacity) {
        this.table_capacity.set(table_capacity);
    }
    public void preserve() {
        this.available.set(false);
    }
    public void preserve(Order order) {
        available.set(false);
        this.order = order;
    }
    // public double checkout() {
    //     available = true;
    //     return order.getTotalPrice();
    // }

    @JsonProperty("available")
    public boolean checkIfAvailable() {
        return available.get();
    }
    @JsonProperty("available")
    public void setAvailability(boolean available) {
        this.available.set(available);
    }
    public static void updateTtableID(ObservableList<Table> tables) {
        for (int i = 0; i < tables.size(); i++) {
            tables.get(i).setTable_id(i + 1);
        }
        Table.table_num = tables.size();
    }
}