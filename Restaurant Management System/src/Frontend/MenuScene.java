package Frontend;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import Backend.Item;
public class MenuScene implements Template {
    MenuScene() {
        VBox root = new VBox();
        root.setSpacing(50);
        root.setPadding(new Insets(20,20,20,20));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(getHheader("Restaurant menu"), getTable(), getFooter());
        root.setBackground(App.getBackground());
        App.getScene().setRoot(root);
    }
    private TableView<Item> getTable() {
        TableView<Item> table = new TableView<Item>();
        table.setMaxWidth(1000);
        TableColumn<Item, String> nameCol = new TableColumn<>("Name");
        nameCol.prefWidthProperty().bind(table.widthProperty().divide(5));
        nameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
        
        TableColumn<Item, String> categoryCol = new TableColumn<>("Category");
        categoryCol.prefWidthProperty().bind(table.widthProperty().divide(5).subtract(3));
        categoryCol.setCellValueFactory(new PropertyValueFactory<Item, String>("Category"));
        
        TableColumn<Item, Double> priceCol = new TableColumn<>("Price");
        priceCol.prefWidthProperty().bind(table.widthProperty().divide(5).subtract(3));
        priceCol.setCellValueFactory(new PropertyValueFactory<Item, Double>("Price"));
        
        TableColumn<Item, SimpleBooleanProperty> avaCol = new TableColumn<>("Availability");
        avaCol.prefWidthProperty().bind(table.widthProperty().divide(5));
        avaCol.setCellValueFactory(new PropertyValueFactory<Item, SimpleBooleanProperty>("availableProperty"));
        avaCol.setCellFactory(col -> new BooleanComboBoxTableCell("item"));

        TableColumn<Item, Integer> ratingCol = new TableColumn<>("Rating");
        ratingCol.prefWidthProperty().bind(table.widthProperty().divide(5));
        ratingCol.setCellValueFactory(new PropertyValueFactory<Item, Integer>("Rating"));

        //avaCol.setSortable(false);
        table.getColumns().addAll(nameCol, categoryCol, priceCol, avaCol ,ratingCol);
        table.autosize();
        table.setEditable(true);
        table.setItems(App.getMenu());
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null) {
                Item selectedItem = newSelection;
                //System.out.println("Name: " + selectedItem.getName());
                editItem(selectedItem, table);
                table.refresh();
            }
        });
        // table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        //     if(newSelection != null) {
        //         Item selectedItem = newSelection;
        //         System.out.println("Name: " + selectedItem.getName());
        //     }
        // });
        return table;
    }
    private HBox getFooter() {
        HBox footer = new HBox();
        footer.setSpacing(3);
        footer.setAlignment(Pos.CENTER);
        final TextField addName = new TextField();
        addName.setPromptText("Name");
        addName.setMinWidth(100);

        final TextField addprice = new TextField();
        addprice.setMinWidth(100);
        addprice.setPromptText("Price");
        
        final ComboBox addcategory = new ComboBox();
        addcategory.setPromptText("Category");
        addcategory.getItems().addAll("Appetizer", "Main Course", "Dessert", "Drink");
        addcategory.setMinWidth(100);

        final Button addButton = new Button("Add Item");
        addButton.setOnAction(e -> {
            if (addName.getText().isEmpty() || addprice.getText().isEmpty() || addcategory.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Field Empty");
                alert.setContentText("Please fill in all fields");
                alert.showAndWait();
                
            }
            else {
                App.getMenu().add(new Item(
                    addName.getText(),
                    stringToDouble(addprice),
                    addcategory.getSelectionModel().getSelectedItem().toString()));
                addName.clear();
                addprice.clear();
                addcategory.getSelectionModel().clearSelection();
                addButton.setText("Category");
            }
        });
        footer.getChildren().addAll(addName, addprice, addcategory, addButton);
        return footer;
    }
    private double stringToDouble(TextField txt) {
        try {
            return Double.parseDouble(txt.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            txt.clear();
            alert.setTitle("Warning");
            alert.setHeaderText("Invalid price entered!");
            alert.setContentText("Please enter a valid price!");
            alert.showAndWait();
            return 0;
        }
    }
    private void editItem(Item selectedItem, TableView table) {
        Stage stage = new Stage();
        VBox pane = new VBox();
        pane.setSpacing(10);
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);

        String css = "-fx-padding: 20px;" + 
        "-fx-font-size: 18px;" +
        "-fx-font-weight: bold;";

        final Label label = new Label("Enter the new price:");
        label.setStyle(css);
        
        final TextField editprice = new TextField();
        editprice.setStyle(css);
        editprice.setMinWidth(100);
        editprice.setMaxWidth(150);
        editprice.setPromptText("New Price");
        
        Button editButton = new Button("Edit Price");
        Button deleteButton = new Button("Delete Item");

        editButton.setStyle(css);
        deleteButton.setStyle(css);
        editButton.setMinWidth(150);
        deleteButton.setMinWidth(150);

        editButton.setOnAction(e -> {
            if (editprice.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Field Empty");
                alert.setContentText("Please fill in the price field");
                alert.showAndWait();
            }
            else {
                try {
                    selectedItem.setPrice(stringToDouble(editprice));
                    table.refresh();
                    stage.close();
                } catch (IllegalArgumentException i) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(i.getMessage());
                    alert.setContentText("Please enter a valid input");
                    alert.showAndWait();
                }
            }
            });            
            
        pane.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(editButton, deleteButton);
        pane.getChildren().addAll(label, editprice, buttons);

        deleteButton.setOnAction(e -> {
            App.getMenu().remove(selectedItem);
            table.refresh();
            stage.close();
        });

        Scene scene = new Scene(pane, 500,300);

        try {
            stage.getIcons().add(new Image("Assets/restaurant.png"));   // Adds icon   
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Failed to load Icon image!");
            alert.setContentText("Path was not found!");
            alert.show();
        }
        stage.setTitle("Edit " + selectedItem.getName());
        stage.setScene(scene);
        stage.setMinHeight(300);
        stage.setMinWidth(400);
        stage.show();
    }
}