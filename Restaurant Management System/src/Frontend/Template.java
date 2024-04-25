package Frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public interface Template {

    default Button getButton(String str) {
        Button btn = new Button(str);
        btn.widthProperty().addListener((observable, oldValue, newValue) -> {
        double width = (double) newValue;
        double fontSize = Math.min(width * 0.1, 55);
        btn.setStyle("-fx-font-size: " + fontSize + "px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-family: Serif;" +
                    "-fx-opacity: 0.8;");
        });
        btn.setPrefHeight(400);
        btn.setPrefWidth(600);
        btn.setTextAlignment(TextAlignment.CENTER);
        return btn;
    }
    default Label getHeader(String str){
        Label label1 = new Label(str);
        Font hfont = Font.font("Helvetica", FontWeight.EXTRA_BOLD ,35);
        label1.setFont(hfont);
        label1.setTextFill(Color.BISQUE);
        return label1;
    }
    default void configureGridpane(GridPane pane){
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(10, 10, 10, 10));
    }
}