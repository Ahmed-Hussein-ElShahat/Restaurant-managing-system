package Frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalTime;

import Backend.Order;

class HistoryScene implements Template {

    HistoryScene() {
        VBox root = new VBox();
        root.setSpacing(50);
        root.setPadding(new Insets(20,20,20,20));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(getHheader("Past Orders"), getTable());
        root.setBackground(App.getBackground());
        App.getScene().setRoot(root);
    }
    
    private TableView<Order> getTable() {
        TableView<Order> table = new TableView<Order>();

        TableColumn<Order, Integer> numCol = new TableColumn<>("Order Number");
        numCol.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderNum"));
        numCol.prefWidthProperty().bind(table.widthProperty().divide(4));

        TableColumn<Order, Double> priceCol = new TableColumn<>("Total Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<Order, Double>("totalPrice"));
        priceCol.prefWidthProperty().bind(table.widthProperty().divide(4).subtract(3));

        TableColumn<Order, LocalDate> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<Order, LocalDate>("date"));
        dateCol.prefWidthProperty().bind(table.widthProperty().divide(4));

        TableColumn<Order, LocalTime> timeCol = new TableColumn<>("Time");
        timeCol.prefWidthProperty().bind(table.widthProperty().divide(4));
        timeCol.setCellValueFactory(new PropertyValueFactory<Order, LocalTime>("time"));

        
        table.getColumns().addAll(numCol, priceCol, dateCol, timeCol);
        table.autosize();
        table.setItems(App.getPastOrders());
        return table;
    }
}