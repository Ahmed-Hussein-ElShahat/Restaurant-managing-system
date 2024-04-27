package Frontend;

import Backend.Item;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import Backend.Order;

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


        btngrid.add(tkeButton, 0,0);
        btngrid.add(onSiteButton, 1,0);

        tkeButton.setOnAction(e -> {    //Go directly to item selection scene
            Item [] items = {new Item("Salad", 20, "Appetizer"), new Item("Sushi", 100, "Main Course")} ;
            Order order = new Order(items);
            new ItemSelectionScene();
            //new ItemReviewScene(order);
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
                // Go to the item selection scene
                new selectionScene();
            }
            else {
                Alert messageAlert = new Alert(Alert.AlertType.INFORMATION);
                messageAlert.setHeaderText("Sorry, no table available");
                messageAlert.setContentText("Please wait until a table is available!");
                messageAlert.show();
                App.returnToMain();
            }
        });

        return btngrid;
    }

}
