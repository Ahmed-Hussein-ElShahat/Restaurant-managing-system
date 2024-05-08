package Frontend;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.net.MalformedURLException;

import Backend.Item;

public class MenuScene implements Template {
    MenuScene() {
        VBox root = new VBox();
        root.setSpacing(50);
        root.setPadding(new Insets(20,20,20,20));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(getHheader("Restaurant menu"), getTable(), getFooter());
        root.setBackground(App.getBackground());
        App.getScene().setRoot(root);
    }
    private TableView<Item> getTable() {
        TableView<Item> table = new TableView<Item>();
        table.setMaxWidth(1000);
        TableColumn<Item, String> nameCol = new TableColumn<>("Name");
        nameCol.prefWidthProperty().bind(table.widthProperty().divide(5));
        nameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
        
        TableColumn<Item, String> categoryCol = new TableColumn<>("Category");
        categoryCol.prefWidthProperty().bind(table.widthProperty().divide(5).subtract(3));
        categoryCol.setCellValueFactory(new PropertyValueFactory<Item, String>("Category"));
        
        TableColumn<Item, Double> priceCol = new TableColumn<>("Price");
        priceCol.prefWidthProperty().bind(table.widthProperty().divide(5).subtract(3));
        priceCol.setCellValueFactory(new PropertyValueFactory<Item, Double>("Price"));
        
        TableColumn<Item, SimpleBooleanProperty> avaCol = new TableColumn<>("Availability");
        avaCol.prefWidthProperty().bind(table.widthProperty().divide(5));
        avaCol.setCellValueFactory(new PropertyValueFactory<Item, SimpleBooleanProperty>("availableProperty"));
        avaCol.setCellFactory(col -> new BooleanComboBoxTableCell("item"));

        // TableColumn<Item, Integer> ratingCol = new TableColumn<>("Rating");
        // ratingCol.prefWidthProperty().bind(table.widthProperty().divide(6).subtract(70));
        // ratingCol.setCellValueFactory(new PropertyValueFactory<Item, Integer>("Rating"));

        TableColumn<Item, Boolean> imgCol = new TableColumn<>("Image");
        imgCol.prefWidthProperty().bind(table.widthProperty().divide(5));
        imgCol.setCellValueFactory(new PropertyValueFactory<Item, Boolean>("isImage"));
        imgCol.setCellFactory(col -> new ImgEditTableCell());

        //avaCol.setSortable(false);
        table.getColumns().addAll(nameCol, categoryCol, priceCol, avaCol , imgCol);
        table.autosize();
        table.setEditable(true);
        table.setItems(App.getMenu());
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null) {
                Item selectedItem = newSelection;
                editItem(selectedItem, table);
                table.refresh();
            }
        });
        return table;
    }
    private HBox getFooter() {
        HBox footer = new HBox();
        footer.setSpacing(3);
        footer.setAlignment(Pos.CENTER);
        final TextField addName = new TextField();
        addName.setPromptText("Name");
        addName.setMinWidth(100);

        final TextField addprice = new TextField();
        addprice.setMinWidth(100);
        addprice.setPromptText("Price");
        
        final ComboBox addcategory = new ComboBox();
        addcategory.setPromptText("Category");
        addcategory.getItems().addAll("Appetizer", "Main Course", "Dessert", "Drink");
        addcategory.setMinWidth(100);

        final Button addButton = new Button("Add Item");
        addButton.setOnAction(e -> {
            if (addName.getText().isEmpty() || addprice.getText().isEmpty() || addcategory.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Field Empty");
                alert.setContentText("Please fill in all fields");
                alert.showAndWait();
                
            }
            else {
                App.getMenu().add(new Item(
                    addName.getText(),
                    stringToDouble(addprice),
                    addcategory.getSelectionModel().getSelectedItem().toString()));
                addName.clear();
                addprice.clear();
                addcategory.getSelectionModel().clearSelection();
            }
        });
        footer.getChildren().addAll(addName, addprice, addcategory, addButton);
        return footer;
    }
    private double stringToDouble(TextField txt) {
        try {
            return Double.parseDouble(txt.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            txt.clear();
            alert.setTitle("Warning");
            alert.setHeaderText("Invalid price entered!");
            alert.setContentText("Please enter a valid price!");
            alert.showAndWait();
            return 0;
        }
    }
    private void editItem(Item selectedItem, TableView table) {
        Stage stage = new Stage();
        VBox pane = new VBox();
        pane.setSpacing(10);
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);

        String css = "-fx-padding: 20px;" + 
        "-fx-font-size: 18px;" +
        "-fx-font-weight: bold;";

        final Label priceLabel = new Label("Enter the new price:");
        priceLabel.setStyle(css);
        
        final Label descriptionLabel = new Label("Enter a description:");
        descriptionLabel.setStyle(css);

        final TextField editprice = new TextField();
        editprice.setStyle(css);
        editprice.setMinWidth(100);
        editprice.setMaxWidth(150);
        editprice.setPromptText("New Price");
        
        TextArea editDescription = new TextArea();
        //editDescription.setStyle(css);
        editDescription.setMinWidth(350);
        editDescription.setMaxWidth(450);
        editDescription.setMaxHeight(100);
        editDescription.setWrapText(true);
        editDescription.setText(selectedItem.getDescription());

        Button editButton = new Button("Edit Item");
        Button deleteButton = new Button("Delete Item");

        editButton.setStyle(css);
        deleteButton.setStyle(css);
        editButton.setMinWidth(150);
        deleteButton.setMinWidth(150);

        editButton.setOnAction(e -> {
            if (!editprice.getText().isBlank()) {
                try {
                    selectedItem.setPrice(stringToDouble(editprice));
                } catch (IllegalArgumentException i) {

                    Template.getWarning("Warning", i.getMessage(), "Please enter a valid input");
                }
            }
            if (!editDescription.getText().equals(selectedItem.getDescription())) {
                if (editDescription.getText().isBlank()){
                    selectedItem.setDescription("No description available");
                }
                else {
                    selectedItem.setDescription(editDescription.getText());
                }
            }
            table.refresh();
            stage.close();
            });
        pane.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(editButton, deleteButton);
        pane.getChildren().addAll(priceLabel, editprice, descriptionLabel, editDescription, buttons);

        deleteButton.setOnAction(e -> {
            App.getMenu().remove(selectedItem);
            table.refresh();
            stage.close();
        });

        Scene scene = new Scene(pane, 500,450);

        try {
            stage.getIcons().add(new Image("Assets/restaurant.png"));   // Adds icon   
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Failed to load Icon image!");
            alert.setContentText("Path was not found!");
            alert.show();
        }
        stage.setTitle("Edit " + selectedItem.getName());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    public static class ImgEditTableCell<T> extends TableCell<T, Boolean> {

        private HBox container = new HBox();
        private Button addBtn = new Button();
        private static Image addImg;
        private Button viewBtn = new Button();
        private static Image viewImg;
        private Button removeBtn = new Button();
        private static Image removeImg;
        private int rowNum;
        //IMPORTANT NOTE: you need to specify the items in the list in the constructor.
        public ImgEditTableCell() {
        if (addImg == null || viewImg == null || removeImg == null) loadImgs();
        setupButtons();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);
        container.getChildren().addAll(addBtn, viewBtn, removeBtn);
        }
        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            setGraphic(container);
            rowNum = this.getTableRow().getIndex();
            if (rowNum >= App.getMenu().size()){
                setGraphic(null);
            }
            else if (empty || !item) {
                viewBtn.setVisible(false);
                removeBtn.setVisible(false);
            } else {
                viewBtn.setVisible(true);
                removeBtn.setVisible(true);
            }
        }
        private void loadImgs() {
            addImg = new Image("Assets/add.png");
            viewImg = new Image("Assets/view.png");
            removeImg = new Image("Assets/remove.png");
        }
        private void setupButtons() {
            ImageView addView = new ImageView(addImg);
            addBtn.setGraphic(addView);
            addBtn.setOnAction(e1 -> {
                selectImg();
            });

            ImageView viewView = new ImageView(viewImg);
            viewBtn.setGraphic(viewView);
            viewBtn.setOnAction(e1 -> {
                Stage imageViewStage = new Stage();
                ImageView imageView = new ImageView(App.getMenu().get(rowNum).getImage());
                imageView.setPreserveRatio(true);
                StackPane pane = new StackPane(imageView);
                pane.setMaxHeight(500);
                pane.setMaxWidth(700);
                Scene scene = new Scene(pane);
                imageViewStage.setScene(scene);
                imageViewStage.setTitle(App.getMenu().get(rowNum).getName() + " image");
                imageViewStage.setResizable(false); // Resizing disabled
                imageViewStage.show();
            });
            
            ImageView removeView = new ImageView(removeImg);
            removeBtn.setGraphic(removeView);
            removeBtn.setOnAction(e1 -> {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Delete image");
                confirm.setHeaderText("Are you sure you want to delete "
                     + App.getMenu().get(rowNum).getName() + "'s image?");
                confirm.showAndWait();
                if (confirm.getResult() == ButtonType.OK) {
                    deleteExistingImg();
                    this.getTableView().refresh();
                }
            });
        }
        private void deleteExistingImg() {
            String extension = getExtension(App.getMenu().get(rowNum).getImage().getUrl());
            File fileToDelete = new File(App.getImgDistPath().toString() + "/" + App.getMenu().get(rowNum).getName() + extension);
            if (fileToDelete.exists()) {
                boolean deleted = fileToDelete.delete();
            if (deleted) {
                // File deleted successfully!
                Template.getInfo("File deletion", "File deleted successfully",
                "confirmation");
            } else {
                // Deletion failed!
                Template.getError("File deletion", "File deletion failed",
                    "Error deleting file: " + fileToDelete.getPath());
            }
            } else {
                // File doesn't exist!
                Template.getWarning("File deletion", "File not found", "File not found: "
                    + fileToDelete.getAbsolutePath());
            }
            App.getMenu().get(rowNum).deleteImage();
        }
        private void selectImg(){
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Image File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg", "*.jpeg"));
            File file = fileChooser.showOpenDialog(App.getPrimaryStage());

            if (file!= null) {
                try {
                    // Create the image folder if it doesn't exist
                    if (!App.getImgDistPath().toFile().exists()) Files.createDirectory(App.getImgDistPath());
                    Path destinationPath = Paths.get(App.getImgDistPath().toString(), App.getMenu().get(rowNum).getName() + getExtension(file.toPath()));
                    Files.copy(file.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                    if (App.getMenu().get(rowNum).getImage() != null) {
                        if (getExtension(file.toPath()) != getExtension(App.getMenu().get(rowNum).getImage().getUrl())) deleteExistingImg();
                    }
                    App.getMenu().get(rowNum).setPath(getURLString(destinationPath));
                    updateItem(true, false);
                } catch (Exception e) {
                    Template.getError("Copy Failed", "Failed to copy", "");
                }
            }
        }
        private String getURLString(Path path) throws MalformedURLException {
            return path.toUri().toURL().toString();
        }
        private String getExtension(Path path) { 
            String fileName = path.getFileName().toString(); 
            int dotIndex = fileName.lastIndexOf('.'); 
            // handle cases with no extension or multiple dots 
            if (dotIndex == -1 || dotIndex == fileName.length() - 1) { 
                return "";          // no extension found 
            } else { 
                return fileName.substring(dotIndex);
            } 
        }
        public String getExtension(String strpath) {
            int dotIndex = strpath.lastIndexOf('.'); 
            // handle cases with no extension or multiple dots 
            if (dotIndex == -1 || dotIndex == strpath.length() - 1) { 
                return "";          // no extension found 
            } else { 
                return strpath.substring(dotIndex); 
            }
        }
    }
}