package Frontend;

import Backend.Payments.*;
import Backend.Order;
import Backend.Table;
import Backend.Item;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;

public class ItemReviewScene implements Template {
    private double amount = 0.0;

    String css = "-fx-padding: 5px;" + 
        "-fx-font-size: 16px;" +
        "-fx-font-weight: bold;" +
        "-fx-background-radius: 10px;" +
        "-fx-border-radius: 10px;";
    ItemReviewScene(Order order, Table payingTable){
        VBox review = new VBox();
        review.setSpacing(50);
        review.setPadding(new Insets(50, 100, 50, 100));
        review.setAlignment(Pos.CENTER);
        review.getChildren().addAll(getHheader("Return to Main Menu"), getTable(order) , paymentTextBox(order, payingTable));
        review.setBackground(App.getBackground());
        App.getScene().setRoot(review);
    }

    private TableView<Item> getTable (Order order){
        TableView<Item> table = new TableView<Item>();

        TableColumn nameCol = new TableColumn("Name");
        nameCol.prefWidthProperty().bind(table.widthProperty().divide(4));
        nameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
        
        TableColumn categoryCol = new TableColumn("Category");
        categoryCol.prefWidthProperty().bind(table.widthProperty().divide(4));
        categoryCol.setCellValueFactory(new PropertyValueFactory<Item, String>("Category"));
        
        TableColumn priceCol = new TableColumn("Price");
        priceCol.prefWidthProperty().bind(table.widthProperty().divide(4).subtract(3));
        priceCol.setCellValueFactory(new PropertyValueFactory<Item, Double>("Price"));

        TableColumn removeCol = new TableColumn("Remove");
        removeCol.prefWidthProperty().bind(table.widthProperty().divide(4).subtract(3));
        removeCol.setCellValueFactory(new PropertyValueFactory<Item, Double>("availableProperty"));
        removeCol.setCellFactory(col -> new RemoveButtonTableCell());

        table.getColumns().addAll(nameCol , categoryCol , priceCol, removeCol);
        table.autosize();
        //table.setEditable(true);
        table.setItems(order.getGuestOrder());
        return table;
    }

    private HBox paymentTextBox (Order order, Table payingTable) {
        HBox totalpayment = new HBox();
        totalpayment.setSpacing(3);
        totalpayment.setAlignment(Pos.CENTER);
        totalpayment.setStyle("-fx-background-color:linear-gradient(from 0% 0% to 100% 100%, #136a8a, #267871); -fx-padding:15px; -fx-background-radius: 20px;");
        totalpayment.setMinWidth(850);
        totalpayment.setMaxWidth(900);
        
        Label label1 = new Label("Total Price : ") ;
        label1.setStyle(css);
        label1.setTextFill(Color.WHITE);
        Label label2 = new Label("Choose Payment Method : ") ;
        label2.setStyle(css);
        label2.setTextFill(Color.WHITE);


        final TextField payment = new TextField();
        payment.setStyle(css + "-fx-padding: 10px 5px;");
        payment.setPromptText("Name");
        payment.setMinWidth(100);
        payment.setText(Double.toString(order.calcTotalPrice()));
        order.getGuestOrder().addListener((ListChangeListener.Change<? extends Item> e) -> {
        payment.setText(Double.toString(order.calcTotalPrice()));
        }); // we can make an alert if the total price = 0
        payment.setEditable(false);

        ComboBox method = new ComboBox();
        method.setStyle(css);
        method.setPromptText("How to Pay ?");
        method.getItems().addAll("Cash" , "Visa") ;
        method.setMinWidth(100);

        Button bt = new Button("OK");
        bt.setStyle(css + "-fx-padding: 10px 5px");
        bt.setMinWidth(100);

        bt.setOnAction( e -> {
            //System.out.println(method.getSelectionModel().getSelectedItem());
            try {
                //System.out.println(payment.getText());
                if ( payment.getText().equals("0.0") ){
                    //System.out.println(payment.getText());
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("No Payment");
                    alert.setContentText("Make an Order First");
                    alert.showAndWait();
                }
                else if ( method.getSelectionModel().getSelectedItem().equals("Cash")  ){
                    cashScene(order, payingTable);
                }
                else if( method.getSelectionModel().getSelectedItem().equals("Visa")  ){
                    visaScene(order, payingTable);
                }
            } catch (NullPointerException g) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Not Choosen");
                alert.setContentText("Please Choose a Method");
                alert.showAndWait();
            }
        });

        totalpayment.getChildren().addAll(label1 , payment , label2 , method , bt);
        return totalpayment;
    }

    private void cashScene(Order order, Table payingTable) {
        VBox container = new VBox();
        StackPane pane = new StackPane(container);
        container.setSpacing(25);
        container.setPadding(new Insets(20, 20, 20, 20));
        container.setAlignment(Pos.CENTER);
        container.setStyle("-fx-background-color:linear-gradient(to bottom, #16222a, #3a6073); -fx-padding:15px; -fx-background-radius: 20px;");
        //container.setStyle("-fx-background-color:linear-gradient(to bottom, #0e1e3e, #3758a3); -fx-padding:15px; -fx-background-radius: 20px;");
        container.setMaxWidth(400);
        container.setMaxHeight(350);

        Label l1 = new Label("The payed amount :") ;
        Font l1Font = Font.font("Helvetica", FontWeight.EXTRA_BOLD ,35);
        l1.setFont(l1Font);
        l1.setTextFill(Color.WHITE);

        final TextField addAmount = new TextField();
        addAmount.setMaxWidth(250);
        addAmount.setPromptText("amount");
        addAmount.setStyle(css + "-fx-padding: 10px 5px; -fx-font-size: 20px;");

        pane.setBackground(App.getBackground());

        Button returnbtn = new Button("Change Payment Method");
        returnbtn.setStyle(css + "-fx-padding: 15px 5px");
        returnbtn.setPrefWidth(250);
        returnbtn.setOnAction(e -> {
            new ItemReviewScene(order, payingTable);
        });
        
        Button returnmain = new Button("Cancel Order");
        returnmain.setStyle(css + "-fx-padding: 15px 5px");
        returnmain.setPrefWidth(250);
        returnmain.setOnAction(e -> {
            if(payingTable != null) {
                payingTable.setAvailability(true);
                payingTable.setOrder(null);
            }
            App.returnToMain();
            // if the customer cancels the order
            // we must clear the order and the order history 
            // Table t = new Table();
            // t.setAvailability(true);
            // App.getTables().getLast().setAvailability(true);
        });

        container.getChildren().addAll(l1 , addAmount   , returnbtn , returnmain );

        App.getScene().setRoot(pane);
        
        addAmount.setOnKeyPressed(f -> {
            if (f.getCode() == KeyCode.ENTER) {
                try{
                    amount = Double.parseDouble(addAmount.getText());
                    if (amount >= order.calcTotalPrice()){
                        if(payingTable != null) {
                            payingTable.setAvailability(true);
                            payingTable.setOrder(null);
                        }
                        App.getPastOrders().add(order);
                        thanksSceneCash(Double.toString(Cash.calcRest(order.calcTotalPrice(), amount)));
                    }
                    else {
                        addAmount.clear();
                        Template.getWarning("Warning", "The paid amount is less than the total price",
                            "Please enter a valid amount.");
                    }
                }catch(NumberFormatException e) {
                    addAmount.clear();
                    Template.getWarning("Warning", "Invalid input!",
                        "Please enter a valid amount.");
                }
            }
        });
    }
    private void visaScene(Order order, Table payingTable){
        Visa v = new Visa();
        VBox container = new VBox();
        StackPane pane = new StackPane(container);
        container.setSpacing(25);
        container.setPadding(new Insets(20, 20, 20, 20));
        container.setAlignment(Pos.CENTER);
        container.setStyle("-fx-background-color:linear-gradient(to bottom, #16222a, #3a6073); -fx-padding:15px; -fx-background-radius: 20px;");
        container.setMaxWidth(400);
        container.setMaxHeight(350);

        Label l1 = new Label("Enter Visa Number :") ;
        Font hfont = Font.font("Helvetica", FontWeight.EXTRA_BOLD ,35);
        l1.setFont(hfont);
        l1.setTextFill(Color.WHITE);
        final TextField VisaNo = new TextField();
        VisaNo.setPromptText("Visa Number");
        VisaNo.setStyle(css + "-fx-padding: 10px 5px; -fx-font-size: 20px;");
        VisaNo.setMaxWidth(250);
        VisaNo.setOnKeyPressed(a -> {
            if(a.getCode() == KeyCode.ENTER){
                try{
                    v.setId(VisaNo.getText());
                    if(payingTable != null) {
                        payingTable.setAvailability(true);
                        payingTable.setOrder(null); // empties order for the table
                    }
                    App.getPastOrders().add(order);     // past orders
                    thanksScene();
                }catch(IllegalArgumentException e){
                    System.out.println(e.getLocalizedMessage());
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(e.getLocalizedMessage());
                    alert.setContentText("Please try again");
                    alert.showAndWait();
                }
            }            
        } );

        Button returnbtn = new Button("Change Payment Method");
        returnbtn.setStyle(css + "-fx-padding: 15px 5px");
        returnbtn.setPrefWidth(250);
        returnbtn.setOnAction(e -> {
            new ItemReviewScene(order, payingTable);
        });

        Button returnmain = new Button("Cancel Order");
        returnmain.setStyle(css + "-fx-padding: 15px 5px");
        returnmain.setPrefWidth(250);
        returnmain.setOnAction(e -> {
            if(payingTable != null) {
                payingTable.setAvailability(true);
                payingTable.setOrder(null);
            }
            App.returnToMain();
            // if the customer cancels the order
            // we must clear the order and the order history 
        });
        pane.setBackground(App.getBackground());
        container.getChildren().addAll(l1 , VisaNo , returnbtn , returnmain);

        App.getScene().setRoot(pane);

    }
    
    private void thanksScene(){
        Label thanks = new Label("Thank you") ;
        Font hfont = Font.font("Freestyle Script", FontWeight.EXTRA_BOLD ,60);
        thanks.setFont(hfont);
        thanks.setTextFill(Color.WHITE);
        VBox container = new VBox();
        StackPane pane = new StackPane(container);
        container.setSpacing(25);
        container.setAlignment(Pos.CENTER);
        container.setStyle("-fx-background-color:linear-gradient(to bottom, #0e1e3e, #3758a3); -fx-padding:15px; -fx-background-radius: 20px;");
        container.setMaxWidth(450);
        container.setMaxHeight(350);
        Button returnbtn = new Button("Return to Main Menu");
        returnbtn.setOnAction(e -> {
            App.returnToMain();
        });
        returnbtn.setStyle(css + "-fx-padding: 15px 5px");
        returnbtn.setPrefWidth(250);
        container.getChildren().addAll(thanks , returnbtn );
        pane.setBackground(App.getBackground());
        App.getScene().setRoot(pane);
    }

    private void thanksSceneCash(String Rest){
        Label thanks = new Label("Thank you") ;
        Font hfont = Font.font("Freestyle Script", FontWeight.EXTRA_BOLD ,60);
        thanks.setFont(hfont);
        thanks.setTextFill(Color.WHITE);
        VBox container = new VBox();
        StackPane pane = new StackPane(container);
        container.setSpacing(25);
        container.setAlignment(Pos.CENTER);
        container.setStyle("-fx-background-color:linear-gradient(to bottom, #0e1e3e, #3758a3); -fx-padding:15px; -fx-background-radius: 20px;");
        container.setMaxWidth(450);
        container.setMaxHeight(350);
        Button returnbtn = new Button("Return to Main Menu");
        returnbtn.setOnAction(e -> {
            App.returnToMain();
        });
        returnbtn.setStyle(css + "-fx-padding: 15px 5px");
        returnbtn.setPrefWidth(250);

        final TextField rest = new TextField();
        rest.setStyle(css + "-fx-padding: 10px 5px; -fx-font-size: 20px;");
        rest.setMaxWidth(250);
        //rest.setEditable(false);
        rest.setPromptText("change");
        rest.setMinWidth(100);
        rest.setEditable(true);
        rest.setText(Rest);
        rest.setEditable(false);

        Label l2 = new Label("Change:") ;
        Font l2Font = Font.font("Helvetica", FontWeight.BOLD ,25);
        l2.setFont(l2Font);
        l2.setTextFill(Color.WHITE);


        pane.setBackground(App.getBackground());
        container.getChildren().addAll(thanks , l2 , rest , returnbtn );
        App.getScene().setRoot(pane);
    }
}
