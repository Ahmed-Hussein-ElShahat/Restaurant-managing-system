package Backend;

public class Table {
    static private int table_num = 1;
    private Order order;                // On table order
    private int table_id;               // Table number
    private int table_capacity = 5;     // Default capacity
    private boolean available = true;   // Whether the table is occupied  

    Table () {
        table_id = ++table_num;
    }

    // Maybe needed
/*     Table (Order order) {
        this.order = order;
        table_id = ++table_num;
        available = false;
    }
    Table (Order order, int table_capacity) {
        this.order = order;
        table_id = ++table_num;
        this.table_capacity = table_capacity;
        available = false;
    } */
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
    
    
}
