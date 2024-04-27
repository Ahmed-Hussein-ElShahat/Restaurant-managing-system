package Backend;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.beans.property.SimpleDoubleProperty;
import java.lang.IllegalArgumentException;
import javafx.beans.property.SimpleBooleanProperty;


public class Item implements Available, Cloneable, Comparable<Item> {
    private SimpleStringProperty category = new SimpleStringProperty();
    private SimpleDoubleProperty price = new SimpleDoubleProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty description = new SimpleStringProperty();
    private SimpleBooleanProperty available = new SimpleBooleanProperty(true);
    private int rating = 0;
    private Image image;

    public void setRating(int rating) {
        this.rating = rating;
    }
    public int getRating() {
        return rating;
    }

    public Item(String Name, double Price) {
        this.name.set(Name);
        this.setPrice(Price);
        this.setDescription("No description available");
    }
    public Item(String Name, double Price, String category) {
        this.name.set(Name);
        this.setPrice(Price);
        this.category.set(category);
        this.setDescription("No description available");
    }
    public Item(String Name, double Price, String category, String description) {
        this.name.set(Name);
        this.setPrice(Price);
        this.category.set(category);
        this.setDescription(description);
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
    
    @Override
    public int compareTo(Item o) {
        return ((Double)this.getPrice()).compareTo((Double)o.getPrice());
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        return (Item) super.clone();
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

    public String getDescription() {return this.description.get();}

    public void setDescription(String description) {this.description.set(description);}

    public boolean equalsCategory(Object obj) {
        return this.getCategory().equals(((Item) obj).getCategory());
    }
    public Image getImage() {
        return image;
    }
    public void setImage(String path) throws IllegalArgumentException {
        this.image = new Image("Assets/food.jpg");
    }
    public void deleteImage() {
        image = null;
    }
    public Boolean getIsImage() {
        return (image != null) ? true : false;
    }
}
