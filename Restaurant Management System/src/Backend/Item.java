package Backend;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Item implements Available {
    private SimpleStringProperty category = new SimpleStringProperty();
    private SimpleDoubleProperty price = new SimpleDoubleProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleBooleanProperty available = new SimpleBooleanProperty(true);
    private String itemDescription;
    public Item(String Name, double Price) {
        this.name.set(Name);
        this.setPrice(Price);
    }
    public Item(String Name, double Price, String category) {
        this.name.set(Name);
        this.setPrice(Price);
        this.category.set(category);
    }
    public SimpleStringProperty getNameProperty() {
        return name;
    }
    
    public SimpleStringProperty getCategoryProperty() {
        return category;
    }
    
    public SimpleDoubleProperty getPriceProperty() {
        return price;
    }
    
    public SimpleBooleanProperty getAvailableProperty() {
        return available;
    }
    
    public void setName(String Name) {
        this.name.set(Name);
    }
    public String getName() {
        return this.name.get();
    }

    public double getPrice() {
        return this.price.get();
    }

    public void setPrice(double Price) throws IllegalArgumentException {
        // will implement it with exception handling later
        if(Price > 0) {
            this.price.set(Price);
        }
        else {
            throw new IllegalArgumentException("Price can't be negative.");
        }
    }

    public void setAvailability(boolean available) {
        this.available.set(available);
    }

    @Override
    public boolean checkIfAvailable() {
        return available.get();
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public String getItemDescription() {return itemDescription;}

    public void setItemDescription(String itemDescription) {this.itemDescription = itemDescription;}

    @Override
    public String toString() {
        return this.getItemDescription();
    }
}

