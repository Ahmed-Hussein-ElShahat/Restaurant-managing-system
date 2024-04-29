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
        GridPane tables = new GridPane();
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(50);
        configureGridpane(tables);
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
                tableBtn.setStyle("-fx-padding: 20px 0; -fx-alignment: center;-fx-font-weight: bold; -fx-font-size:20px;" + 
                "-fx-background-color:white; -fx-text-fill:black; -fx-background-radius: 20px; -fx-border-radius: 20px;" +
                "-fx-border-width: 2px; -fx-border-color:black");
                tableBtn.setOnMouseEntered(e -> {
                    tableBtn.setStyle("-fx-padding: 20px 0; -fx-alignment: center;-fx-font-weight: bold; -fx-font-size:20px;" + 
                    "-fx-background-color:black; -fx-text-fill:white; -fx-background-radius: 20px; -fx-border-radius: 20px;" +
                    "-fx-border-width: 2px; -fx-border-color:black");
                });
                tableBtn.setOnMouseExited(e -> {
                    tableBtn.setStyle("-fx-padding: 20px 0; -fx-alignment: center;-fx-font-weight: bold; -fx-font-size:20px;" + 
                    "-fx-background-color:white; -fx-text-fill:black; -fx-background-radius: 20px; -fx-border-radius: 20px;" +
                    "-fx-border-width: 2px; -fx-border-color:black");
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
        pane.setContent(tables);
        box.getChildren().addAll(getHeader("Select Table:"),pane);
        App.getScene().setRoot(box);
        
    }
    public Label getHeader(String str){
        Label label1 = new Label(str);
        Font hfont = Font.font("Helvetica", FontWeight.EXTRA_BOLD ,35);
        label1.setFont(hfont);
        return label1;
    }
}
