package Frontend;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.util.StringConverter;



public interface Template {

    
    default HBox getHheader(String header) {
        HBox Hheader = new HBox();
        Hheader.setAlignment(Pos.CENTER);
        Button returnbtn = new Button("Return to Main Menu");
        returnbtn.setStyle("-fx-padding: 10px;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 20px;" +
            " -fx-border-radius: 20px;");
        returnbtn.setOnAction(e -> {
            App.returnToMain();
        });
        Hheader.setSpacing(100);
        Hheader.getChildren().addAll(getHeader(header),  returnbtn);
        return Hheader;
    }
    default Button getButton(String str) {
        Button btn = new Button(str);
        btn.widthProperty().addListener((observable, oldValue, newValue) -> {
        double width = (double) newValue;
        double fontSize = Math.min(width * 0.1, 55);
        btn.setStyle("-fx-font-size: " + fontSize + "px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-family: Serif;" +
                    "-fx-opacity: 0.8;" + 
                    "-fx-background-radius: 20px;" +
                    "-fx-border-radius: 20px;");
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
        label1.setTextFill(Color.WHITE);
        label1.setStyle("-fx-background-color:linear-gradient(from 0% 0% to 100% 100%, #1f3a4d, #597d97); -fx-padding:15px; -fx-background-radius: 20px;");
        return label1;
    }
    default void configureGridpane(GridPane pane){
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(10, 10, 10, 10));
    }
    static void getError(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.show();
    }
    static void getInfo(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.show();
    }
    static void getWarning(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.show();
    }
    public static class BooleanComboBoxTableCell<T> extends TableCell<T, SimpleBooleanProperty> {

        private final ComboBox<SimpleBooleanProperty> comboBox;
        //IMPORTANT NOTE: you need to specify the items in the list in the constructor.
        public BooleanComboBoxTableCell(String type) {
          comboBox = new ComboBox<>();
          comboBox.setConverter(new StringBooleanConverter());
          comboBox.getItems().addAll(new SimpleBooleanProperty(true), new SimpleBooleanProperty(false));
          setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
          comboBox.setOnAction(e -> {
            if (getItem()!= null) {
                if (type == "item") {
                    App.getMenu().get(this.getTableRow().getIndex()).getAvailableProperty().set(comboBox.getSelectionModel().getSelectedItem().get());
                    commitEdit(getItem());
                }
                else if (type == "table") {
                    App.getTables().get(this.getTableRow().getIndex()).getAvailableProperty().set(comboBox.getSelectionModel().getSelectedItem().get());
                }
            }
          });
        }
        @Override
        protected void updateItem(SimpleBooleanProperty item, boolean empty) {
          super.updateItem(item, empty);
          if (empty || item == null) {
            setText(null);
            setGraphic(null);
          } else {
            comboBox.getSelectionModel().select(item);
            // Bind selection or update property
            
            setText(null);
            setGraphic(comboBox);
          }
        }
        class StringBooleanConverter extends StringConverter<SimpleBooleanProperty> {
            @Override
            public String toString(SimpleBooleanProperty object) {
                if (object == null) {
                    return null;
                }
                return object.getValue()? "Available" : "Not Available";
            }
            @Override
            public SimpleBooleanProperty fromString(String string) {
                return new SimpleBooleanProperty(Boolean.parseBoolean(string));
            }
        }
    }
}