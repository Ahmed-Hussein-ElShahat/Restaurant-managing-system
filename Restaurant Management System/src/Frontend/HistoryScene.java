package Frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
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
        root.getChildren().addAll(getheader(), getTable());
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
        header.getChildren().addAll(getHeader("Past Orders"),  returnbtn);
        return header;
    }
    private TableView<Order> getTable() {
        TableView<Order> table = new TableView<Order>();

        TableColumn numCol = new TableColumn("Order Number");
        numCol.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderNum"));
        numCol.prefWidthProperty().bind(table.widthProperty().divide(4));

        TableColumn priceCol = new TableColumn("Total Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<Order, Double>("totalPrice"));
        priceCol.prefWidthProperty().bind(table.widthProperty().divide(4).subtract(3));

        TableColumn dateCol = new TableColumn("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<Order, LocalDate>("date"));
        dateCol.prefWidthProperty().bind(table.widthProperty().divide(4));

        TableColumn timeCol = new TableColumn("Time");
        timeCol.prefWidthProperty().bind(table.widthProperty().divide(4));
        timeCol.setCellValueFactory(new PropertyValueFactory<Order, LocalTime>("time"));


        table.getColumns().addAll(numCol, priceCol, dateCol, timeCol);
        table.autosize();
        table.setItems(App.getPastOrders());
        return table;
    }
}
