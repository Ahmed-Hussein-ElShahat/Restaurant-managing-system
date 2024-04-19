package Frontend;

import javafx.application.Application;
import javafx.geometry.Pos;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.*;


import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FlowPane pane = new FlowPane();
        Image bkgrndimage = new Image("Assets/food.jpg");
        BackgroundSize size = new BackgroundSize(1280,720, false, false, true, true);
        BackgroundImage backgroundimage = new BackgroundImage(bkgrndimage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, size);
        Background bg = new Background(backgroundimage);
        Label label1 = new Label("Welcome to our restaurant");
        Font hfont = Font.font("Helvetica", FontWeight.EXTRA_BOLD ,35);
        label1.setFont(hfont);
        label1.setTextFill(Color.BEIGE);
        pane.getChildren().add(label1);
        pane.setAlignment(Pos.BASELINE_CENTER);
        Scene scene = new Scene(pane, 1280, 720);
        pane.setBackground(bg);
        stage.setTitle("Application");
        stage.setScene(scene);
        //stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}