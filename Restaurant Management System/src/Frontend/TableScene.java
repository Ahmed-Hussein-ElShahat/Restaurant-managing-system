package Frontend;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;
import Backend.Table;

public class TableScene implements Template {
    TableScene() {
        VBox root = new VBox();
        root.setSpacing(50);
        root.setPadding(new Insets(100,100,100,100));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(getHheader("Restaurant Tables"), getTable(), getFooter());
        root.setBackground(App.getBackground());
        App.getScene().setRoot(root);
    }
    
    private TableView<Table> getTable() {
        TableView<Table> table = new TableView<Table>();
        table.setMaxWidth(400);
        TableColumn numCol = new TableColumn("Table Number");
        numCol.prefWidthProperty().bind(table.widthProperty().divide(3).subtract(2));
        numCol.setCellValueFactory(new PropertyValueFactory<Table, Integer>("Table_id"));
        
        TableColumn capacityCol = new TableColumn("Capacity");
        capacityCol.prefWidthProperty().bind(table.widthProperty().divide(3).subtract(2));
        capacityCol.setCellValueFactory(new PropertyValueFactory<Table, Integer>("table_capacity"));

        TableColumn avlCol = new TableColumn("Availability");
        avlCol.prefWidthProperty().bind(table.widthProperty().divide(3));
        avlCol.setCellValueFactory(new PropertyValueFactory<Table, SimpleBooleanProperty>("availableProperty"));
        avlCol.setCellFactory(col -> new BooleanComboBoxTableCell("table"));
        
        table.getColumns().addAll(numCol, capacityCol, avlCol);
        table.setItems(App.getTables());
        return table;
    }
    private HBox getFooter() {
        HBox footer = new HBox();
        footer.setSpacing(3);
        final TextField addCapacity = new TextField();
        addCapacity.setPromptText("Capacity");
        addCapacity.setMinWidth(100);

        final Button addButton = new Button("Add Table");
        addButton.setOnAction(e -> {
            if (addCapacity.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Field Empty");
                alert.setContentText("Please fill in all fields");
                alert.showAndWait();
            }
            else {
                App.getTables().add(new Table(stringToInt(addCapacity)));
                addCapacity.clear();
            }
        });
        footer.getChildren().addAll(addCapacity, addButton);
        footer.setAlignment(Pos.CENTER);
        return footer;
    }
    private int stringToInt(TextField txt) {
        try {
            return Integer.parseInt(txt.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            txt.clear();
            alert.setTitle("Warning");
            alert.setHeaderText("Invalid price entered!");
            alert.setContentText("Please enter a valid Capacity!");
            alert.showAndWait();
            return 0;
        }
    }
}
