package Frontend;

import Backend.Table;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TableSelectionScene implements Template {
    TableSelectionScene() {
        ScrollPane pane = new ScrollPane();
        pane.setStyle("-fx-background-color: transparent;");
        GridPane tables = new GridPane();
        tables.setStyle("-fx-background-color: transparent;");
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(50);
        box.setBackground(App.getBackground());
        configureGridpane(tables);
        pane.setContent(tables);
        box.getChildren().addAll(getHheader("Select Table:"),pane);
        int row = 0, column = 0;
        for(int i = 0; i < App.getTables().size(); i++) {
            Table table = App.getTables().get(i);
            if(column == 3){
                column = 0;
                row++;
            }
            if(table.checkIfAvailable()){
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
                    table.setAvailability(false);
                    new ItemSelectionScene(table.getOrder());
                });
                tables.add(tableBtn, column, row);
                column++;
            }
        }

        App.getScene().setRoot(box);
        
    }
}
