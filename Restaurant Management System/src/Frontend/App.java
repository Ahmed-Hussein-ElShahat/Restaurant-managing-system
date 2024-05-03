package Frontend;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.lang.IllegalArgumentException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import Backend.*;

public class App extends Application implements Template{

    private static ObservableList<Table> tables = FXCollections.observableArrayList();  // List of available tables
    private static ObservableList<Item> menu = FXCollections.observableArrayList();     // List of menu items
    private static ObservableList<Order> pastOrders = FXCollections.observableArrayList();  // List of past orders
    private static Background background = App.loadBackground("Assets/food.jpg"); // Default background for the program
    private static Scene scene; // Default scene
    private static VBox pane = new VBox();
    private static Path imgDistPath = Paths.get("temp/"); // Distination directory for storing item images
    private static Path savedPath = Paths.get("saved/");
    private static File menuFile = new File(savedPath.toString()+"/menu.json");
    private static File tableFile = new File(savedPath.toString()+"/tables.json");
    private static File orderFile = new File(savedPath.toString()+"/pastOrders.json");
    private static Stage primaryStage;
    @Override
    public void start(Stage stage) {
        scene = new Scene(pane, 1024, 576);
        ObjectMapper mapper = new ObjectMapper();
        loadSavedData(mapper);
        // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        String css = this.getClass().getResource("application.css").toExternalForm();
        scene.getStylesheets().add(css);
        pane.getChildren().addAll(getHeader("Restaurant management system"), getmainBtnGrid());
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
        stage.setMinHeight(576);        //*Sets minimum window size
        stage.setMinWidth(1024);        //*
        try {
            stage.getIcons().add(new Image("Assets/restaurant.png"));   // Adds icon   
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Failed to load Icon image!");
            alert.setContentText("Path was not found!");
            alert.show();
        }
        stage.setOnCloseRequest(e -> {
            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(menuFile, menu);
            } catch (Exception e1) {
                Template.getError("Saving Error","Failed to save menu file!", e1.getMessage());
                // System.out.println(e1.getMessage());
                // e1.printStackTrace();
            }
            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(tableFile, tables);
            } catch (Exception e2) {
                Template.getError("Saving Error","Failed to seve table file!", e2.getMessage());
                // System.out.println(e2.getMessage());
                // e2.printStackTrace();
            }
            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(orderFile, pastOrders);                
            } catch (Exception e3) {
                Template.getError("Saving Error","Failed to seve order file.", e3.getMessage());
                // System.out.println(e3.getMessage());
                // e3.printStackTrace();
            }
            System.exit(0);
        });
        //stage.setResizable(false);
        //stage.setFullScreen(true);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    private GridPane getmainBtnGrid() {
        GridPane btngrid = new GridPane();
        configureGridpane(btngrid);
        
        Button strtButton = getButton("Start\nOrder");
        Button historyButton = getButton("Order\nHistory");
        Button menuButton = getButton("Modify\nMenu");
        Button tableButton = getButton("Modify\nTables");

        btngrid.add(strtButton, 0,0);
        btngrid.add(historyButton, 0,1);
        btngrid.add(menuButton,1,0);
        btngrid.add(tableButton, 1,1);

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
        //statsButton.getOnAction(e -> );
        //finceButton.getOnAction(e -> );
        return btngrid;
    }
    protected static Background loadBackground(String path) throws IllegalArgumentException {
        Image bkgrndimage = new Image(path);
        BackgroundSize size = new BackgroundSize(1280,720, false, false, true, true);
        BackgroundImage backgroundimage = new BackgroundImage(bkgrndimage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, size);
        Background bg = new Background(backgroundimage);
        return bg;
    }
    protected static ObservableList<Table> getTables() {
        return tables;
    }
    protected static ObservableList<Item> getMenu() {
        return menu;
    }
    protected static ObservableList<Order> getPastOrders() {
        return pastOrders;
    }
    protected static Background getBackground() {
        return background;
    }
    protected static Scene getScene() {
        return scene;
    }
    public static void setBackground(Background background) {
        App.background = background;
    }
    public static void returnToMain() {
        scene.setRoot(pane);
    }
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    public static Path getImgDistPath() {
        return imgDistPath;
    }
    private void loadSavedData(ObjectMapper mapper) {
        JavaType menuType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Item.class);
        JavaType tableType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Table.class);
        JavaType orderType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Order.class);
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (savedPath.toFile().exists()) {
            try {
                if(menuFile.exists())
                {
                    ArrayList<Item> items = mapper.readValue(menuFile, menuType);
                    menu = FXCollections.observableArrayList(items);
                for (Item item : menu) {
                    item.reloadImg();
                }
                }
                if(tableFile.exists()) {
                    ArrayList<Table> tbles = mapper.readValue(tableFile, tableType);
                    tables = FXCollections.observableArrayList(tbles);
                }
                if (orderFile.exists()) {
                    ArrayList<Order> orders = mapper.readValue(orderFile, orderType);
                    pastOrders = FXCollections.observableArrayList(orders);
                    Order.setTotalNumberOfOrders(pastOrders.size());
                }
            } catch(Exception e){
                Template.getError("Loading Error","Failed to load saved files", e.getMessage());
            }
        }
        else {
            try {
                Files.createDirectory(savedPath);
            } catch (Exception e) {
                Template.getError("Loading Error","Failed to create directory", e.getMessage());
            }
        }
    }
}