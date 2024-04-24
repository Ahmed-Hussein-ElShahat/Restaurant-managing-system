package Frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;

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

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setPrefWidth(100);
        nameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
        
        TableColumn categoryCol = new TableColumn("Category");
        categoryCol.setPrefWidth(100);
        categoryCol.setCellValueFactory(new PropertyValueFactory<Item, String>("Category"));
        
        TableColumn priceCol = new TableColumn("Price");
        priceCol.setPrefWidth(100);
        priceCol.setCellValueFactory(new PropertyValueFactory<Item, Double>("Price"));
        
        TableColumn ratingCol = new TableColumn("Rating");
        ratingCol.setPrefWidth(100);
        ratingCol.setCellValueFactory(new PropertyValueFactory<Item, String>("Rating"));
        //TableColumn editCol = new TableColumn("Edit");
        //editCol.setCellValueFactory(new PropertyValueFactory<Item, Button>("Edit"));
        //editCol.setSortable(false);
        table.getColumns().addAll(nameCol, categoryCol, priceCol, ratingCol);
        table.autosize();
        table.setEditable(true);
        table.setItems(App.getMenu());
        return table;
    }
    private HBox getFooter() {
        HBox footer = new HBox();
        footer.setSpacing(3);
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
}
