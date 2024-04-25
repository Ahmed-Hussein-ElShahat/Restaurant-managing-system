package Frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
        root.getChildren().addAll(getheader(), getTable(), getFooter());
        root.setBackground(App.getBackground());
        App.getScene().setRoot(root);
    }
    private HBox getheader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        Button returnbtn = new Button("Return to Main Menu");
        returnbtn.setOnAction(e -> {
            App.returnToMain();
        });
        header.setSpacing(100);
        header.getChildren().addAll(getHeader("Restaurant Menu"),  returnbtn);
        return header;
    }
    private TableView<Item> getTable() {
        TableView<Item> table = new TableView<Item>();
        table.setMaxWidth(1000);
        TableColumn nameCol = new TableColumn("Name");
        nameCol.prefWidthProperty().bind(table.widthProperty().divide(4));
        nameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
        
        TableColumn categoryCol = new TableColumn("Category");
        categoryCol.prefWidthProperty().bind(table.widthProperty().divide(4));
        categoryCol.setCellValueFactory(new PropertyValueFactory<Item, String>("Category"));
        
        TableColumn priceCol = new TableColumn("Price");
        priceCol.prefWidthProperty().bind(table.widthProperty().divide(4).subtract(3));
        priceCol.setCellValueFactory(new PropertyValueFactory<Item, Double>("Price"));
        
        TableColumn ratingCol = new TableColumn("Rating");
        ratingCol.prefWidthProperty().bind(table.widthProperty().divide(4));
        ratingCol.setCellValueFactory(new PropertyValueFactory<Item, String>("Rating"));
        //TableColumn editCol = new TableColumn("Edit");
        //editCol.setCellValueFactory(new PropertyValueFactory<Item, Button>("Edit"));
        //editCol.setSortable(false);
        table.getColumns().addAll(nameCol, categoryCol, priceCol, ratingCol);
        table.autosize();
        table.setEditable(true);
        table.setItems(App.getMenu());
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null) {
                Item selectedItem = newSelection;
                System.out.println("Name: " + selectedItem.getName());
                editItem(selectedItem, table);
                table.refresh();
            }
        });
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
        
        final TextField addcategory = new TextField();
        addcategory.setPromptText("Category");
        addcategory.setMinWidth(100);

        final Button addButton = new Button("Add Item");
        addButton.setOnAction(e -> {
            if (addName.getText().isEmpty() || addprice.getText().isEmpty() || addcategory.getText().isEmpty()) {
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
                    addcategory.getText()));
                addName.clear();
                addprice.clear();
                addcategory.clear();
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
                selectedItem.setPrice(stringToDouble(editprice));
                table.refresh();
                stage.close();
            }
        });
        pane.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(editButton, deleteButton);
        pane.getChildren().addAll(editprice, buttons);

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
        stage.setTitle("Edit Item");
        stage.setScene(scene);
        stage.setMinHeight(300);
        stage.setMinWidth(400);
        stage.show();
    }
}
