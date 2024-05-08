package Frontend;

import Backend.Item;
import Backend.Order;
import Backend.Table;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

// The program's main class
public class App extends Application implements Template{

    private static ObservableList<Table> tables = FXCollections.observableArrayList();  // List of available tables
    private static ObservableList<Item> menu = FXCollections.observableArrayList();     // List of menu items
    private static ObservableList<Order> pastOrders = FXCollections.observableArrayList();  // List of past orders
    private static Background background = App.loadBackground("Assets/food.jpg"); // Default background for the program
    private static Scene scene;            // Main menu scene
    private static VBox pane = new VBox(); // Main menu pane
    private static Path imgDistPath = Paths.get("temp/"); // Distination directory for storing item images
    private static Path savedPath = Paths.get("saved/");  // Distination directory for saving program data
    private static File menuFile = new File(savedPath.toString()+"/menu.json");         // menu json file
    private static File tableFile = new File(savedPath.toString()+"/tables.json");      // tables json file
    private static File orderFile = new File(savedPath.toString()+"/pastOrders.json");  // past orders json file
    private static Stage primaryStage;     // Main stage

    @Override
    public void start(Stage stage) {
        // Make the main scene with dimention 1024 * 576
        scene = new Scene(pane, 1024, 576);

        // Load saved data
        ObjectMapper mapper = new ObjectMapper();
        loadSavedData(mapper);

        // Set styking of the scene
        String css = this.getClass().getResource("application.css").toExternalForm();
        scene.getStylesheets().add(css);

        // Add the Scene elements to the main pane
        pane.getChildren().addAll(getHeader("Restaurant management system"), getmainBtnGrid());
        
        // Load the background image
        try {
            pane.setBackground(background);
        } catch (IllegalArgumentException e) {
            Template.getError("Loading Error","Failed to load background image", "Path was not found!");
            scene.setFill(Color.ALICEBLUE);
        }
        /* // Test for warnings
        Template.getWarning("Loading Error","Mock error",
             "It is good practice to wrap the sleep method in a try/catch block in case another thread interrupts the sleeping thread. In this case, we catch the InterruptedException and explicitly interrupt the current thread, so it can be caught later and handled. This is more important in a multi-threaded program, but still good practice in a single-threaded program in case we add other threads later.");
        */
        pane.setAlignment(Pos.CENTER);
        stage.setTitle("Application");
        stage.setScene(scene);

        // Sets minimum window size
        stage.setMinHeight(576);
        stage.setMinWidth(1024);    
        
        // Load icon
        try {
            stage.getIcons().add(new Image("Assets/restaurant.png"));   // Adds icon   
        } catch (IllegalArgumentException e) {
            Template.getError("Error", "Failed to load Icon image!", "Path was not found!");
        }
        
        // Updates the total number of orders at any change in past orders list
        pastOrders.addListener((ListChangeListener.Change<? extends Order> e) -> {
            Order.setTotalNumberOfOrders(pastOrders.size());
            pastOrders.getLast().setOrderNum(Order.getTotalNumberOfOrders());
        });
        // Save The menu, tables and past orders on closing the application
        stage.setOnCloseRequest(e -> {
            try { // Attempting to save the menu.
                mapper.writerWithDefaultPrettyPrinter().writeValue(menuFile, menu);
            } catch (Exception e1) {
                Template.getError("Saving Error", e1.getMessage(),"Failed to save menu file!");
            }
            try { // Attempting to save the tables.
                mapper.writerWithDefaultPrettyPrinter().writeValue(tableFile, tables);
            } catch (Exception e2) {
                Template.getError("Saving Error", e2.getMessage(), "Failed to seve table file!");
            }
            try { // Attempting to save the past orders.
                mapper.writerWithDefaultPrettyPrinter().writeValue(orderFile, pastOrders);                
            } catch (Exception e3) {
                Template.getError("Saving Error", e3.getMessage(), "Failed to seve order file.");
            }

            // Close the Application completely so that no stages are open (Ex: edit item stage or alerts,...)
            System.exit(0);
        });
        //stage.setResizable(false);
        //stage.setFullScreen(true);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

    // Make a grid pane for the main menu
    private GridPane getmainBtnGrid() {

        GridPane btngrid = new GridPane();
        configureGridpane(btngrid);
        
        // Make the buttons
        Button strtButton = getButton("Start\nOrder");
        Button historyButton = getButton("Order\nHistory");
        Button menuButton = getButton("Modify\nMenu");
        Button tableButton = getButton("Modify\nTables");

        // Add the buttons to the grid pane with their respective positions
        btngrid.add(strtButton, 0,0);
        btngrid.add(historyButton, 0,1);
        btngrid.add(menuButton,1,0);
        btngrid.add(tableButton, 1,1);

        // Setting the functionality of each button
        strtButton.setOnAction(e -> {
            new OrderScene();
        });
        historyButton.setOnAction(e -> {
            new HistoryScene();
        });
        menuButton.setOnAction(e -> {
            new MenuScene();
        });
        tableButton.setOnAction(e -> {
            new TableScene();
        });

        return btngrid;
    }
    
    // Function to load the background given its directory
    protected static Background loadBackground(String path) throws IllegalArgumentException {
        Image bkgrndimage = new Image(path);

        // Sets the background size properties
        // width = 100%, height = 100% and fits all of the pane
        BackgroundSize size = new BackgroundSize(100,100, true, true, true, true);
        
        // Make the background image object & putting it inside a background object
        BackgroundImage backgroundimage = new BackgroundImage(bkgrndimage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, size);
        Background bg = new Background(backgroundimage);
        return bg;
    }
    // getter for the tables list
    protected static ObservableList<Table> getTables() {
        return tables;
    }
    // getter for the menu list
    protected static ObservableList<Item> getMenu() {
        return menu;
    }
    // getter for the past orders list
    protected static ObservableList<Order> getPastOrders() {
        return pastOrders;
    }
    // getter for the default background
    protected static Background getBackground() {
        return background;
    }
    // getter for the main scene
    protected static Scene getScene() {
        return scene;
    }
    // setter for the default background    
    public static void setBackground(Background background) {
        App.background = background;
    }
    // returns to the main menu scene
    public static void returnToMain() {
        scene.setRoot(pane);
    }
    // getter for the main stage of the program
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    // getter for the path of saving the item images
    public static Path getImgDistPath() {
        return imgDistPath;
    }
    // Function that loads the saved data from the json files
    private void loadSavedData(ObjectMapper mapper) {
        // Making the java types for the menu, tables and past orders list for retrieval
        JavaType menuType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Item.class);
        JavaType tableType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Table.class);
        JavaType orderType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Order.class);
        
        // Adding retrieval of local date and time functionality to the mapper
        mapper.registerModule(new JavaTimeModule());

        // Disable failing of load when facing an unknown property (Skiping unknown properties) in json file
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (savedPath.toFile().exists()) { // Checking if the saved folder exists
            try {
                if(menuFile.exists())    // Checking if the menu json file exists
                {
                // Reads the files to an array list and puts the array list inside the list used by the program
                    ArrayList<Item> items = mapper.readValue(menuFile, menuType);
                    menu = FXCollections.observableArrayList(items);
                }
                if(tableFile.exists()) { // Checking if the tables json file exists
                // Reads the files to an array list and puts the array list inside the list used by the program
                    ArrayList<Table> tbles = mapper.readValue(tableFile, tableType);
                    tables = FXCollections.observableArrayList(tbles);
                }
                if (orderFile.exists()) {// Checking if the past orders json file exists
                // Reads the files to an array list and puts the array list inside the list used by the program
                    ArrayList<Order> orders = mapper.readValue(orderFile, orderType);
                    pastOrders = FXCollections.observableArrayList(orders);
                    // sets the total number of tables to the order size
                    Order.setTotalNumberOfOrders(pastOrders.size());
                }
            } catch(Exception e){ // Handling of failing to read the json files 
                Template.getError("Loading Error", e.getMessage(), "Failed to load saved files");
            }
        }
        else { // If the saved folder doesn't exist create one
            try {
                Files.createDirectory(savedPath);
            } catch (IOException e) { // Handling of failing to create the saved folder
                Template.getError("Loading Error", e.getMessage(), "Failed to create directory");
            }
        }
    }
}