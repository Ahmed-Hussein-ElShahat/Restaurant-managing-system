package Frontend;


import Backend.Item;
import Backend.Order;
import Backend.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.Comparator;


public class ItemSelectionScene implements Template{
    final private ScrollPane sp = new ScrollPane();
    // private Image[] images;
    private Order order;
    private Item wantedItem;
    private Item previtem = new Item("",1,"");
    private ListView<GridPane> lv;
    public ItemSelectionScene(Order tableOrder, Table reservingTable) {
        
        Button btnreview = new Button("Place Order");
        btnreview.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-background-color: #f0f8ff");

        // Check if its on site
        if(tableOrder != null) {
            order = tableOrder;
            btnreview.setOnAction(e -> {
                reservingTable.setAvailability(false);
                App.returnToMain();
            });
        }
        else {
            order = new Order();
            btnreview.setOnAction(e -> new ItemReviewScene(order, null));
        }

        // openImageFolder();
        ObservableList<GridPane> ov = FXCollections.observableArrayList();

        FXCollections.sort(App.getMenu(), Comparator.comparing(Item::getCategory).reversed().thenComparing(Item::compareTo));
        
        for (int i = 0; i < App.getMenu().size(); i++) {
            if(App.getMenu().get(i).checkIfAvailable()) {
                ov.add(showItem(App.getMenu().get(i)));
            }
        }

        lv = new ListView<>(ov);
        lv.getStylesheets().add("Frontend/application.css");
        // lv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // lv.getSelectionModel().selectedItemProperty().addListener(o -> {
        //     wantedItem = App.getMenu().get(lv.getSelectionModel().getSelectedIndex());
        // });

        //Configure the scroll pane and set items to it
        sp.setContent(lv);
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);
        sp.prefHeightProperty().bind(App.getScene().heightProperty());
        sp.prefWidthProperty().bind(App.getScene().widthProperty());

        //create a VBox, configure and put nodes in it
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20,20,20,20));
        vbox.setSpacing(20);
        vbox.prefWidthProperty().bind(App.getScene().widthProperty());
        vbox.prefHeightProperty().bind(App.getScene().heightProperty());
        vbox.setBackground(App.getBackground());

        vbox.getChildren().addAll(getHheader("Item selection"),sp,btnreview);
        //start the scene
        App.getScene().setRoot(vbox);
    }

    private GridPane showItem(Item item){


        //create a GridPane and configure it
        GridPane gpane = new GridPane();
        gpane.setPadding(new Insets(10,10,10,10));
        gpane.setVgap(10);
        gpane.prefWidthProperty().bind(sp.widthProperty().divide(3));
        gpane.prefHeightProperty().bind(sp.heightProperty().divide(3));
        gpane.hgapProperty().bind(sp.widthProperty().divide(2).subtract(30));
        gpane.setVgap(10);
        gpane.setStyle("-fx-background-color: #f0f8ff");

        //create a VBox and configure it
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setSpacing(10);

        //create labels and configure them
        Label lbl2 = new Label(item.getName());
        Label lbl3 = new Label(item.getDescription());
        Label lbl4 = new Label(item.getPrice() + "");

        //create a text field and configure it
        TextField tf = getTextField();

        //create a font and set it to the labels and the text field
        Font font = new Font("Freestyle Script", 40);
        lbl2.setFont(font);
        lbl4.setFont(font);
        lbl3.setFont(Font.font("Helvetica"));
        lbl3.setWrapText(true);
        lbl3.setMaxWidth(500);

        Label lbl1;
        if(!item.equalsCategory(previtem)) {
            lbl1 = new Label(item.getCategory());
            lbl1.setStyle("-fx-font-size: 40px; -fx-font-weight: bold;-fx-font-family: Freestyle Script");
            GridPane.setColumnSpan(lbl1, 2);
            gpane.add(lbl1,0,0);
            previtem = item;
        }

        //create a wrapper vbox
        VBox wrapper = new VBox();
        wrapper.getChildren().addAll(lbl2, lbl3);
        GridPane.setColumnSpan(vbox, 2);

        //create a border pane for the labels and the text field
        BorderPane bp = new BorderPane();
        bp.setTop(wrapper);
        bp.setBottom(lbl4);
        bp.prefHeightProperty().bind(sp.heightProperty().divide(2).add(40));

        vbox.getChildren().addAll(bp, tf);

        //create an ImageView and configure it
        if (item.getIsImage()){
            ImageView iv = new ImageView(item.getImage());
            iv.fitHeightProperty().bind(sp.heightProperty().divide(3).add(50));
            //GridPane.setRowSpan(iv, 2);
            iv.setPreserveRatio(true);
            gpane.add(iv, 1, 1);
        }
        gpane.add(vbox, 0, 1);
        gpane.add(tf,0,2);
        // For debuging purposes
        // gpane.setGridLinesVisible(true);
        return gpane;
    }

    private TextField getTextField() {
        TextField tf = new TextField();
        tf.setPromptText("Number of items");
        tf.setOnKeyPressed(e ->{
            if(e.getCode().equals(KeyCode.ENTER)){
                int n = Integer.parseInt(tf.getText());
                int maxItemNum = 150;
                if (n > maxItemNum) {
                    Template.getWarning("Maximum items exceeded", "Number of items ordered can't exceed " + maxItemNum + ".", "Please enter a valid amount.");
                }
                else{
                    int index = lv.getItems().indexOf((GridPane)tf.getParent());
                    try{
                        wantedItem = App.getMenu().get(index);
                        order.addnOrder(wantedItem, n);

                        tf.clear();
                        tf.setPromptText("Number of items");

                        sp.requestFocus();
                    }
                    catch (IllegalArgumentException ex){
                        Template.getWarning("Warning", "Invalid Input", "Number of items must be a positive integer.");
                    }
                    catch (IndexOutOfBoundsException ex){
                        Template.getWarning("Warning", "No item selected", "Please select an item first.");
                    }
                }
            }
        });
        return tf;
    }

    // private void openImageFolder() throws NullPointerException{

    //     File[] files = folder.listFiles();

    //     if (files != null)
    //     {
    //         images = new Image[files.length];
    //         for(int i = 0; i< files.length; i++) images[i] = new Image(files[i].toURI().toString());
    //     }
    // }
}