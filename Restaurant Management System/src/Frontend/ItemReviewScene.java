package Frontend;

import Backend.Payments.Cash;
import Backend.Order;
//import Backend.Payment;
import Backend.Item;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;

public class ItemReviewScene implements Template {

    private double amount = 0.0;

    ItemReviewScene(Order order){
        VBox review = new VBox();
        review.setSpacing(50);
        review.setPadding(new Insets(20, 20, 20, 20));
        review.setAlignment(Pos.CENTER);
        review.getChildren().addAll( getheader(), getTable() , paymentTextBox(order) );
        review.setBackground(App.getBackground());
        App.getScene().setRoot(review);
    }

    private HBox getheader() { //not sure we will use it or not 
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        Button returnbtn = new Button("Return to Main Menu");
        returnbtn.setOnAction(e -> {
            App.returnToMain();
        });
        header.setSpacing(100);
        header.getChildren().addAll(getHeader("Restaurant Menu"),  returnbtn);
        return header;
    }

    private TableView<Item> getTable (){
        TableView<Item> table = new TableView<Item>();
        table.setMaxWidth(1000);

        TableColumn nameCol = new TableColumn("Name");
        nameCol.prefWidthProperty().bind(table.widthProperty().divide(3));
        nameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
        
        TableColumn categoryCol = new TableColumn("Category");
        categoryCol.prefWidthProperty().bind(table.widthProperty().divide(3));
        categoryCol.setCellValueFactory(new PropertyValueFactory<Item, String>("Category"));
        
        TableColumn priceCol = new TableColumn("Price");
        priceCol.prefWidthProperty().bind(table.widthProperty().divide(3).subtract(3));
        priceCol.setCellValueFactory(new PropertyValueFactory<Item, Double>("Price"));

        table.getColumns().addAll(nameCol , categoryCol , priceCol);
        table.autosize();
        table.setEditable(true);
        table.setItems(App.getMenu());
        return table;
    }

    private HBox paymentTextBox (Order order) {
        HBox totalpayment = new HBox();
        totalpayment.setSpacing(3);
        totalpayment.setAlignment(Pos.CENTER);

        Label label1 = new Label("Total Price : ") ;
        Label label2 = new Label("Choose Payment Method") ;

        final TextField payment = new TextField();
        payment.setPromptText("Name");
        payment.setMinWidth(100);
        payment.setText(Double.toString(order.calcTotalPrice()));

        ComboBox method = new ComboBox();
        method.setPromptText("How to Pay ?");
        method.getItems().addAll("Cash" , "Visa") ;
        method.setMinWidth(100);

        Button bt = new Button("OK");
        bt.setMinWidth(100);

        bt.setOnAction( e -> {
            //System.out.println(method.getSelectionModel().getSelectedItem());
            if ( method.getSelectionModel().getSelectedItem() .equals("Cash")  ){
                cashScene(order);
                //System.out.println("cash");
            }
            else if( method.getSelectionModel().getSelectedItem() .equals("Visa")  ){
                visaScene();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Not Choosen");
                alert.setContentText("Please Choose a Method");
                alert.showAndWait();
            }
        });

        totalpayment.getChildren().addAll(label1 , payment , label2 , method , bt );
        return totalpayment ;
    }

    private void cashScene (Order order){
        VBox vb = new VBox();
        vb.setBackground(App.getBackground());
        vb.setSpacing(50);
        vb.setPadding(new Insets(20, 20, 20, 20));
        vb.setAlignment(Pos.CENTER);

        Label l1 = getHeader("The payed amount :");
        final TextField addAmount = new TextField();
        addAmount.setPromptText("amount");
        addAmount.setMinWidth(100);
        Label l2 = getHeader("The Rest:");
        final TextField rest = new TextField();
        vb.getChildren().addAll(l1 , addAmount , l2 , rest);
        App.getScene().setRoot(vb);
        rest.setEditable(false);
        rest.setPromptText("rest");
        rest.setMinWidth(100);
        addAmount.setOnKeyPressed(f -> {
            if (f.getCode() == KeyCode.ENTER) {
                try{
                    amount = Double.parseDouble(addAmount.getText());
                    if (amount >= order.calcTotalPrice()){
                        rest.setEditable(true);
                        rest.setText(Double.toString(Cash.calcRest(order.calcTotalPrice(), amount)));
                        rest.setEditable(false);
                    }
                    else {
                        addAmount.clear();
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Invalid input!");
                        alert.setContentText("Please enter a valid amount.");
                        alert.showAndWait();
                        
                    }
                }catch(NumberFormatException e) {
                    //System.out.println("Error parsing amount!");
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid input!");
                    alert.setContentText("Please enter a valid amount.");
                }
            }
        });
    }
    private void visaScene (){
        VBox vb = new VBox();
        vb.setBackground(App.getBackground());
        vb.setSpacing(50);
        vb.setPadding(new Insets(20, 20, 20, 20));
        vb.setAlignment(Pos.CENTER);
    }
    
}
