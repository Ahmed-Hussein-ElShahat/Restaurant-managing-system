package Frontend;


import Backend.Item;
import Backend.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import java.util.Arrays;
import static javafx.scene.text.Font.font;

public class TakeAwayScene implements Template{
    private ScrollPane sp = new ScrollPane();

    public TakeAwayScene() {


        Item[] items =  new Item[App.getMenu().size()];
        App.getMenu().toArray(items);

        ObservableList<GridPane> obs = FXCollections.observableArrayList();
        for(Item item : items){
            obs.add(showItem(item,new Image("Assets/food.jpg")));
        }
        ListView <GridPane> lv = new ListView<>(obs);

        Order order = new Order();
        lv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lv.getSelectionModel().selectedItemProperty().addListener(ov -> {
            order.addOrder(items[lv.getSelectionModel().getSelectedIndex()]);
            for(Item item : order.getGuestOrder()){
                System.out.println(item.getName());
            }
        });

        //Configure the scroll pane and set items to it
        sp.setContent(lv);
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);


        //start the scene
        App.getScene().setRoot(sp);
    }

    private GridPane showItem(Item item, Image img){
        //create a GridPane and configure it
        GridPane gpane = new GridPane();
        gpane.setAlignment(Pos.CENTER);
        gpane.hgapProperty().bind(sp.widthProperty().divide(2));
        gpane.setPadding(new Insets(10, 10, 10, 10));

        //create labels and configure them
        Label lbl1 = new Label(item.getName());
        Label lbl2 = new Label(item.toString());
        Label lbl3 = new Label(item.getPrice() + "");

        //create a text field and configure it
        TextField tf = new TextField();
        tf.setPromptText("nmber of items");
        //put the nodes in their positions
        lbl1.setAlignment(Pos.BOTTOM_LEFT);
        lbl2.setAlignment(Pos.TOP_LEFT);
        lbl3.setAlignment(Pos.BOTTOM_LEFT);
        tf.setAlignment(Pos.BOTTOM_LEFT);

        //create a font and set it to the labels and the textfield
        Font font = new Font("Freestyle Script", 40);
        lbl1.setFont(font);
        lbl3.setFont(font);

        //create an ImageView and configure it
        ImageView iv = new ImageView(img);
        iv.fitHeightProperty().bind(sp.heightProperty().divide(2));
        iv.fitWidthProperty().bind(sp.widthProperty().divide(2));

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 10, 10, 10));
        borderPane.setTop(lbl1);
        borderPane.setCenter(lbl2);
        borderPane.setCenter(tf);
        borderPane.setBottom(lbl3);
        gpane.add(borderPane, 0, 0);
        gpane.add(iv, 1, 0);

        return gpane;
    }
}

