package Backend;

public class Table {
    static private int table_num = 0;   // Total number of tables
    private Order order;                // On table order
    private int table_id;               // Table number
    private int table_capacity = 5;     // Default capacity
    private boolean available = true;   // Whether the table is occupied  


    public Table () {
        table_id = ++table_num;
    }

    // Maybe needed
/*     Table (Order order) {
        this.order = order;
        table_id = ++table_num;
        available = false;
    } */
    public Table (int table_capacity) {
        table_id = ++table_num;
        this.table_capacity = table_capacity;
    } 
    public static int getTable_num() {
        return table_num;
    }
    public Order getOrder() {
        return order;
    }
    public int getTable_id() {
        return table_id;
    }
    public int getTable_capacity() {
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
        return available && people <= table_capacity;
    }
    public static void remove_table() {
        Table.table_num--;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public void setTable_capacity(int table_capacity) {
        this.table_capacity = table_capacity;
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
}