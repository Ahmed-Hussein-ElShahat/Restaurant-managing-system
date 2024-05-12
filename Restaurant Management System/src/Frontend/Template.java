package Frontend;

import Backend.Available;
import Backend.Table;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    public static class RemoveButtonTableCell<T,S> extends TableCell<T,S> {
        private final Button removeBtn = new Button();
        private static Image removeImg;
        private int rowNum;
        public RemoveButtonTableCell() {
            if (removeImg == null) removeImg = new Image("Assets/remove.png");
            removeBtn.setGraphic(new ImageView(removeImg));
            removeBtn.setOnAction(e -> {
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirmation");
                confirmAlert.setHeaderText("Are you sure you want to remove this item?");
                confirmAlert.showAndWait();
                if (confirmAlert.getResult() == ButtonType.OK) {
                    this.getTableView().getItems().remove(rowNum);
                    this.getTableView().refresh();
                }
            });
        }
        @Override
        protected void updateItem(S item, boolean empty) {
            super.updateItem(item, empty);
            rowNum = this.getTableRow().getIndex();
            if (item == null){
                setGraphic(null);
            }
            else {
                setGraphic(removeBtn);
            }
            
        }
    }
    public static class BooleanComboBoxTableCell extends TableCell<Available, SimpleBooleanProperty> {
    // Custom Table cell for modifiction of the available property

        private final ComboBox<SimpleBooleanProperty> comboBox; // Combo box for boolean values
        //IMPORTANT NOTE: you need to specify the items in the list in the constructor.
        public BooleanComboBoxTableCell() {
            comboBox = new ComboBox<>();
            comboBox.setConverter(new StringBooleanConverter("Available", "Not Available")); 
            // sets the display values of the comboBox to (Available/ Not Available)

            comboBox.getItems().addAll(new SimpleBooleanProperty(true), new SimpleBooleanProperty(false));

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
          
            // Action taken when interacting with the comboBox
            comboBox.setOnAction(e -> {
                if (getItem() != null) {
                    boolean selected = getSelection();
                    this.getTableRow().getItem().getAvailableProperty().set(selected);
                }
            });

        }
        @Override
        protected void updateItem(SimpleBooleanProperty item, boolean empty) {
          super.updateItem(item, empty);
          if (empty || item == null) {
            // Empty the table cells that has no Item
            setText(null);
            setGraphic(null);
          } else {
            // Set the comboBox to the availability value and shows it
            comboBox.getSelectionModel().select(item);
            setText(null);
            setGraphic(comboBox);
          }
        }
        private boolean getSelection() {
            // Returns the selected value of the comboBox
            return comboBox.getSelectionModel().getSelectedItem().get();
        }
        class StringBooleanConverter extends StringConverter<SimpleBooleanProperty> {
        // Custom made Boolean property to string converter for selection display
            private final String trueValue;
            private final String falseValue;

            StringBooleanConverter(String trueValue, String falseValue) {
                super();
                this.trueValue = trueValue;
                this.falseValue = falseValue;
            }
            @Override
            public String toString(SimpleBooleanProperty object) {
                if (object == null) {
                    return null;
                }
                return object.getValue()? trueValue : falseValue;
            }
            @Override
            public SimpleBooleanProperty fromString(String string) {
                return new SimpleBooleanProperty(Boolean.parseBoolean(string));
            }
        }
    }
}