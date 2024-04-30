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
    // Custom Table cell for modifiction of boolean properties

        private final ComboBox<SimpleBooleanProperty> comboBox; // Combo box for boolean values
        private int rowNum;
        //IMPORTANT NOTE: you need to specify the items in the list in the constructor.
        public BooleanComboBoxTableCell(String type) {
          comboBox = new ComboBox<>();
          comboBox.setConverter(new StringBooleanConverter("Available", "Not Available")); 
          // sets the display values of the comboBox to (Available/ Not Available)

          comboBox.getItems().addAll(new SimpleBooleanProperty(true), new SimpleBooleanProperty(false));

          setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
          
          // Action taken when interacting with the comboBox
          comboBox.setOnAction(e -> {
            if (getItem()!= null) {
                rowNum = this.getTableRow().getIndex();
                if (type == "item") {
                    // Sets the item availability to the selected value
                    App.getMenu().get(rowNum).getAvailableProperty().set(getSelection());
                }
                else if (type == "table") {
                    // Sets the table availability to the selected value
                    App.getTables().get(rowNum).getAvailableProperty().set(getSelection());
                }
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