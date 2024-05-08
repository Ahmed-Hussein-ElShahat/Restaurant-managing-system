package Frontend;

import Backend.Table;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class TablePaymentScene implements Template {
        TablePaymentScene() {
        ScrollPane pane = new ScrollPane();
        GridPane tables = new GridPane();
        tables.setStyle("-fx-background-color: transparent");
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(50);
        box.setBackground(App.getBackground());
        configureGridpane(tables);
        pane.setContent(tables);
        box.getChildren().addAll(getHheader("Select Table to Pay:"),pane);
        int row = 0, column = 0;
        for(int i = 0; i < App.getTables().size(); i++) {
            Table table = App.getTables().get(i);
            if(column == 3){
                column = 0;
                row++;
            }
            if(table.checkIfAvailable() == false){
                String tableInfo = "Table Number: " + 
                table.getTable_id() + 
                "\n" + "Capacity: " + 
                table.getTable_capacity();
                Button tableBtn = new Button(tableInfo);
                String BtnStyle = "-fx-padding: 20px 0; -fx-alignment: center;-fx-font-weight: bold; -fx-font-size:20px;" + 
                "-fx-background-color:white; -fx-text-fill:black; -fx-background-radius: 20px; -fx-border-radius: 20px;" +
                "-fx-border-width: 2px; -fx-border-color:black;";

                tableBtn.setStyle(BtnStyle + "-fx-background-color:white; -fx-text-fill:black;");
                tableBtn.setOnMouseEntered(e -> {
                    tableBtn.setStyle(BtnStyle + "-fx-background-color:black; -fx-text-fill:white;");
                });
                tableBtn.setOnMouseExited(e -> {
                    tableBtn.setStyle(BtnStyle + "-fx-background-color:white; -fx-text-fill:black;");
                });
                tableBtn.prefWidthProperty().bind(pane.widthProperty().divide(3).subtract(20));
                tableBtn.setOnAction(e -> {
                    if(table.getOrder() == null) {
                        Alert messageAlert = new Alert(Alert.AlertType.INFORMATION);
                        messageAlert.setHeaderText("Sorry, The table is not occupied");
                        messageAlert.setContentText("You can change it to available in the modify tables");
                        messageAlert.show();
                    }
                    else {
                        new ItemReviewScene(table.getOrder(), table);
                    }
                });
                tables.add(tableBtn, column, row);
                column++;
            }
        }

        App.getScene().setRoot(box);
        
    }
}
