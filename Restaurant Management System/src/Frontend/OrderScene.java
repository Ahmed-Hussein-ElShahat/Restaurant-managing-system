package Frontend;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;


public class OrderScene implements Template {

    public OrderScene() {
        VBox orderPane = new VBox();
        
        orderPane.getChildren().addAll(getHheader("Select order method"), getOrderMethodpane());
        orderPane.setBackground(App.getBackground());
        orderPane.setAlignment(Pos.CENTER);
        App.getScene().setRoot(orderPane);
    }
    private GridPane getOrderMethodpane() {
        GridPane btngrid = new GridPane();
        configureGridpane(btngrid);
        
        Button tkeButton = getButton("Take\nAway");
        Button onSiteButton = getButton("Onsite");
        Button tablePayment = getButton("Table\nPayment");
        
        btngrid.add(tkeButton, 0, 0);
        btngrid.add(onSiteButton, 1, 0);
        btngrid.add(tablePayment, 0, 1, 2, 1); // spanning two columns
        // Adding row constrains
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setValignment(javafx.geometry.VPos.CENTER); // Sets the content of the row to be vertically centered
        btngrid.getRowConstraints().add(rowConstraints);
        GridPane.setHalignment(tablePayment, HPos.CENTER); // Center table payment button
        
        
        tkeButton.setOnAction(e -> {    //Go directly to item selection scene
            new ItemSelectionScene(null);
        });
        onSiteButton.setOnAction(e -> { 
            //Check if there is a table available
            boolean flag = false;
            for (int i = 0; i < App.getTables().size(); i++) {
                if (App.getTables().get(i).checkIfAvailable()){
                    flag = true;
                    break;
                }
            }
            if (flag) {
                // Go to the table selection scene
                new TableSelectionScene();
            }
            else {
                Alert messageAlert = new Alert(Alert.AlertType.INFORMATION);
                messageAlert.setHeaderText("Sorry, no table available");
                messageAlert.setContentText("Please wait until a table is available!");
                messageAlert.show();
                App.returnToMain();
            }
        });
        tablePayment.setOnAction(e -> {
            //Check if there is a table occupied
            boolean flag = false;
            for (int i = 0; i < App.getTables().size(); i++) {
                if (App.getTables().get(i).checkIfAvailable() == false){
                    flag = true;
                    break;
                }
            }
            if (flag) {
                new TablePaymentScene();
            }
            else {
                Alert messageAlert = new Alert(Alert.AlertType.INFORMATION);
                messageAlert.setHeaderText("Sorry, All tables are empty");
                messageAlert.setContentText("There is no occupied table to pay");
                messageAlert.show();
                App.returnToMain();
            }
        });

        return btngrid;
    }

}
