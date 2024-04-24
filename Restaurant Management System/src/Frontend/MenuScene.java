package Frontend;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;

import Backend.Item;
public class MenuScene implements Template {
    MenuScene() {
        VBox root = new VBox();
        HBox header = new HBox();
        Button returnbtn = new Button("Return to Main Menu");
        Button addButton = new Button("Add item");
        header.getChildren().addAll(getHeader("Menu"), addButton, returnbtn);
        
        table.
    }
    private TableView getTable() {
        TableView<Item> table = new TableView<Item>();

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
        TableColumn categoryCol = new TableColumn("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<Item, String>("Category"));
        TableColumn priceCol = new TableColumn("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<Item, String>("Price"));
        TableColumn ratingCol = new TableColumn("Rating");
        ratingCol.setCellValueFactory(new PropertyValueFactory<Item, String>("Rating"));
        TableColumn editCol = new TableColumn("Edit");
        editCol.setCellValueFactory(new PropertyValueFactory<Item, Button>("Edit"));
        editCol.setSortable(false);

        table.getColumns().addAll(nameCol, categoryCol, priceCol, ratingCol);
        table.autosize();

    }
}
