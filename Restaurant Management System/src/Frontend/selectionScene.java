package Frontend;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class selectionScene implements Template {
    selectionScene() {
        ScrollPane pane = new ScrollPane();
        GridPane tables = new GridPane();
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(50);
        configureGridpane(tables);
        int row = 0, column = 0;
        for(int i = 0; i < App.getTables().size(); i++) {
            if(column == 3){
                column = 0;
                row++;
            }
            if(App.getTables().get(i).checkIfAvailable()){
                String tableInfo = "Table Number: " + 
                App.getTables().get(i).getTable_id() + 
                "\n" + "Capacity: " + 
                App.getTables().get(i).getTable_capacity();
                Button table = new Button(tableInfo);
                table.setStyle("-fx-padding: 20px 0; -fx-alignment: center;-fx-font-weight: bold; -fx-font-size:20px;");
                table.prefWidthProperty().bind(pane.widthProperty().divide(3).subtract(15));
                tables.add(table, column, row);
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
        label1.setTextFill(Color.BLACK);
        return label1;
    }
}
