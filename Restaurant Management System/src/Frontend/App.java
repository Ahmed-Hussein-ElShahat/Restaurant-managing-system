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

import java.lang.IllegalArgumentException;
import java.nio.file.Path;
import java.nio.file.Paths;

import Backend.*;

public class App extends Application implements Template{

    private static ObservableList<Table> tables = FXCollections.observableArrayList();
    private static ObservableList<Item> menu = FXCollections.observableArrayList();
    private static ObservableList<Order> pastOrders = FXCollections.observableArrayList();
    private static Background background = App.loadBackground("Assets/food.jpg");
    private static Scene scene;
    private static VBox pane = new VBox();
    private static Path imgDistPath = Paths.get("bin/temp/"); // Distination directory for storing item images
    private static Stage primaryStage;
    @Override
    public void start(Stage stage) {
        scene = new Scene(pane, 1024, 576);
        primaryStage = stage;
        // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        String css = this.getClass().getResource("application.css").toExternalForm();
        scene.getStylesheets().add(css);
        pane.getChildren().addAll(getHeader("Restaurant management system"), getmainBtnGrid());
        try {
            pane.setBackground(background);
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Failed to load background image!");
            alert.setContentText("Path was not found!");
            scene.setFill(Color.ALICEBLUE);
            alert.showAndWait();
        }

        menu.addAll(new Item("Salad", 20, "Appetizer"), new Item("Sushi", 100, "Main Course"));
        pastOrders.add(new Order());
        try {
        pastOrders.get(0).addOrder((Item)App.getMenu().get(0).clone());
        pastOrders.get(0).addOrder((Item)App.getMenu().get(1).clone());
        }
        catch (CloneNotSupportedException e) {
            System.out.println("Adding Order failed");
        }
        //System.out.println(menu.get(1).getName());

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
        // Button statsButton = getButton("Plate\nStats");  //Optional until main scenes are finished
        // Button finceButton = getButton("Finances");


        btngrid.add(strtButton, 0,0);
        btngrid.add(historyButton, 0,1);
        btngrid.add(menuButton,1,0);
        btngrid.add(tableButton, 1,1);
        //btngrid.add(statsButton, 2,0);    //Optional until main scenes are finished
        //btngrid.add(finceButton, 2,1);

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
}