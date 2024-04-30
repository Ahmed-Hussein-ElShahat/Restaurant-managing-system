package Backend;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.beans.property.SimpleDoubleProperty;
import java.lang.IllegalArgumentException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


import javafx.beans.property.SimpleBooleanProperty;


public class Item implements Available, Cloneable, Comparable<Item> {
    @JsonIgnore
    private SimpleStringProperty category = new SimpleStringProperty();
    @JsonIgnore
    private SimpleDoubleProperty price = new SimpleDoubleProperty();
    @JsonIgnore
    private SimpleStringProperty name = new SimpleStringProperty();
    @JsonIgnore
    private SimpleStringProperty description = new SimpleStringProperty();

    @JsonIgnore
    private SimpleBooleanProperty available = new SimpleBooleanProperty(true);
    private int rating = 0;
    private String path;
    @JsonIgnore
    private Image image;

    public void setRating(int rating) {
        this.rating = rating;
    }
    public int getRating() {
        return rating;
    }
    public Item() {};
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
    @JsonIgnore
    public SimpleStringProperty getNameProperty() {
        return name;
    }
    @JsonIgnore
    public SimpleStringProperty getCategoryProperty() {
        return category;
    }
    @JsonIgnore
    public SimpleDoubleProperty getPriceProperty() {
        return price;
    }
    @JsonIgnore
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
    @JsonProperty("name")
    public void setName(String Name) {
        this.name.set(Name);
    }
    @JsonProperty("name")
    public String getName() {
        return this.name.get();
    }
    @JsonProperty("price")
    public double getPrice() {
        return this.price.get();
    }
    @JsonProperty("price")
    public void setPrice(double Price) throws IllegalArgumentException {
        if(Price > 0) {
            this.price.set(Price);
        }
        else {
            throw new IllegalArgumentException("Price can't be negative.");
        }
    }
    @JsonProperty(value = "available")
    public void setAvailability(boolean available) {
        this.available.set(available);
    }

    @JsonProperty(value = "available")
    public boolean checkIfAvailable() {
        return available.get();
    }
    @JsonProperty(value = "category")
    public String getCategory() {
        return category.get();
    }
    @JsonProperty(value = "category")
    public void setCategory(String category) {
        this.category.set(category);
    }
    @JsonProperty(value = "description")
    public String getDescription() {return this.description.get();}
    @JsonProperty(value = "description")
    public void setDescription(String description) {this.description.set(description);}

    public boolean equalsCategory(Object obj) {
        return this.getCategory().equals(((Item) obj).getCategory());
    }
    public Image getImage() {
        return image;
    }
    public void setImage(String path) throws IllegalArgumentException {
        this.image = new Image(path);
        this.path = path;
    }
    public void deleteImage() {
        image = null;
        path = null;
    }
    @JsonIgnore
    public Boolean getIsImage() {
        return (image != null) ? true : false;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public void reloadImg() {
        if (path != null) {
            this.image = new Image(path);
        }
    }
}