package com.example.coursework;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;

public class NetCafeDeleteUpdateItemsController {
    @FXML
    public ImageView backButtonView;
    @FXML
    public ImageView imageView;
    @FXML
    public ImageView itemView;
    @FXML
    public DatePicker itemPurchaseDateField;
    @FXML
    public TextField itemCategoryField;
    @FXML
    public TextField itemQuantityField;
    @FXML
    public TextField itemPriceField;
    @FXML
    public TextField itemBrandField;
    @FXML
    public TextField itemNameField;
    @FXML
    public Label displayItemCode;
    @FXML
    public Button updateButton;
    @FXML
    private TextField itemSearch;
    @FXML
    private TableView<viewItems> itemsTable;
    @FXML
    private TableColumn<viewItems, String> viewBrand;
    @FXML
    private TableColumn<viewItems, String> viewCategory;
    @FXML
    private TableColumn<viewItems, String> viewCode;
    @FXML
    private TableColumn<viewItems, LocalDate> viewDate;
    @FXML
    private TableColumn<viewItems, String> viewName;
    @FXML
    private TableColumn<viewItems, Double> viewPrice;
    @FXML
    private TableColumn<viewItems, Integer> viewQuantity;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    ObservableList<viewItems> itemList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        try {
            try {
                viewCode.setCellValueFactory(new PropertyValueFactory<>("viewCode"));
                viewName.setCellValueFactory(new PropertyValueFactory<>("viewName"));
                viewBrand.setCellValueFactory(new PropertyValueFactory<>("viewBrand"));
                viewPrice.setCellValueFactory(new PropertyValueFactory<>("viewPrice"));
                viewQuantity.setCellValueFactory(new PropertyValueFactory<>("viewQuantity"));
                viewCategory.setCellValueFactory(new PropertyValueFactory<>("viewCategory"));
                viewDate.setCellValueFactory(new PropertyValueFactory<>("viewDate"));

                itemList.addAll(readDataFromFile("Inventory.txt"));
            } catch (RuntimeException re) {
                re.printStackTrace();
            }


            FilteredList<viewItems> searchedData = new FilteredList<>(itemList, p -> true);
            itemSearch.textProperty().addListener((observable, oldValue, newValue) -> searchedData.setPredicate(item -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String turnTextToLowercase = newValue.toLowerCase();

                return item.getViewCode().toLowerCase().contains(turnTextToLowercase);
            }));
            itemsTable.setItems(searchedData);
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
    }

    public List<viewItems> readDataFromFile(String fileName) {
        List<viewItems> savedItems = new ArrayList<>();
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] details = line.split(",");
                String viewCode = details[0];
                String viewName = details[1];
                String viewBrand = details[2];
                double viewPrice = Double.parseDouble(details[3]);
                int viewQuantity = Integer.parseInt(details[4]);
                String viewCategory = details[5];
                LocalDate viewDate = LocalDate.parse(details[6]);

                savedItems.add(new viewItems(viewCode, viewName, viewBrand, viewPrice, viewQuantity, viewCategory, viewDate));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return savedItems;
    }

    public void onDeleteBack(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("manageItemsPage.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onDeleteButtonClick(ActionEvent event) {
        viewItems selectedItem = itemsTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Delete item");

            Optional<ButtonType> output = alert.showAndWait();
            if (output.isPresent() && output.get() == ButtonType.OK) {
                ItemFileWriter.deleteItemFromFile(selectedItem.getViewCode());
                itemList.remove(selectedItem);
                itemSearch.clear();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("no item selected");
            alert.showAndWait();
        }
    }

    @FXML
    public void onUpdateBack(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("manageItemsPage.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public static class viewItems {
        private String viewCode;
        private String viewName;
        private String viewBrand;
        private double viewPrice;
        private int viewQuantity;
        private String viewCategory;
        private LocalDate viewDate;

        public viewItems(String viewCode, String viewName, String viewBrand, double viewPrice, int viewQuantity, String viewCategory, LocalDate viewDate) {
            this.viewCode = viewCode;
            this.viewName = viewName;
            this.viewBrand = viewBrand;
            this.viewPrice = viewPrice;
            this.viewQuantity = viewQuantity;
            this.viewCategory = viewCategory;
            this.viewDate = viewDate;
        }

        public String getViewCode() {
            return viewCode;
        }

        public String getViewName() {
            return viewName;
        }
        public String getViewBrand() {
            return viewBrand;
        }

        public double getViewPrice() {
            return viewPrice;
        }

        public int getViewQuantity() {
            return viewQuantity;
        }

        public String getViewCategory() {
            return viewCategory;
        }

        public LocalDate getViewDate() {
            return viewDate;
        }
        public void setViewCode(String viewCode) {
            this.viewCode = viewCode;
        }

        public void setViewName(String viewName) {
            this.viewName = viewName;
        }

        public void setViewBrand(String viewBrand) {
            this.viewBrand = viewBrand;
        }

        public void setViewPrice(double viewPrice) {
            this.viewPrice = viewPrice;
        }

        public void setViewQuantity(int viewQuantity) {
            this.viewQuantity = viewQuantity;
        }

        public void setViewCategory(String viewCategory) {
            this.viewCategory = viewCategory;
        }

        public void setViewDate(LocalDate viewDate) {
            this.viewDate = viewDate;
        }

        public boolean isValid() {
            return isItemNameValid() && isItemBrandValid() && isItemPriceValid() && isItemQuantityValid() && isItemCategoryValid() && isItemPurchasedDateValid();
        }

        private boolean isItemNameValid() {
            if (viewName.isEmpty()) {
                Alert saveConfirm = new Alert(Alert.AlertType.WARNING);
                saveConfirm.setHeaderText("Please enter the item name");
                saveConfirm.getButtonTypes().setAll(ButtonType.OK);
                saveConfirm.show();
                return false;
            }
            return true;
        }

        private boolean isItemBrandValid() {
            if (viewBrand.isEmpty()) {
                Alert saveConfirm = new Alert(Alert.AlertType.WARNING);
                saveConfirm.setHeaderText("Please enter the item brand");
                saveConfirm.getButtonTypes().setAll(ButtonType.OK);
                saveConfirm.show();
                return false;
            }
            return true;
        }

        private boolean isItemPriceValid() {
            if (viewPrice < 1) {
                Alert saveConfirm = new Alert(Alert.AlertType.WARNING);
                saveConfirm.setHeaderText("Please enter the item price");
                saveConfirm.getButtonTypes().setAll(ButtonType.OK);
                saveConfirm.show();
                return false;
            }
            return true;
        }

        private boolean isItemQuantityValid() {
            if (viewQuantity < 0) {
                Alert saveConfirm = new Alert(Alert.AlertType.WARNING);
                saveConfirm.setHeaderText("Please enter the item price");
                saveConfirm.getButtonTypes().setAll(ButtonType.OK);
                saveConfirm.show();
                return false;
            }
            return true;
        }

        private boolean isItemCategoryValid() {
            if (viewCategory.isEmpty()) {
                Alert saveConfirm = new Alert(Alert.AlertType.WARNING);
                saveConfirm.setHeaderText("Please enter the item category");
                saveConfirm.getButtonTypes().setAll(ButtonType.OK);
                saveConfirm.show();
                return false;
            }
            return true;
        }

        private boolean isItemPurchasedDateValid() {
            Alert saveConfirm = new Alert(Alert.AlertType.WARNING);
            saveConfirm.getButtonTypes().setAll(ButtonType.OK);
            if (viewDate == null) {
                saveConfirm.setHeaderText("Please enter the purchased date of the item");
                saveConfirm.show();
                return false;
            } else if (viewDate.isAfter(LocalDate.now())) {
                saveConfirm.setHeaderText("Please enter the correct purchased date");
                saveConfirm.show();
                return false;
            }
            return true;
        }
    }

    @FXML
    public void onUpdateClick(MouseEvent event) throws IOException {
        try {
            String code = displayItemCode.getText();
            String name = itemNameField.getText();
            String brand = itemBrandField.getText();
            String priceStr = itemPriceField.getText();
            String quantityStr = itemQuantityField.getText();
            String category = itemCategoryField.getText();
            LocalDate purchasedDate = itemPurchaseDateField.getValue();

            double price = Double.parseDouble(priceStr);
            int quantity = Integer.parseInt(quantityStr);

            viewItems newItem = new viewItems(code, name, brand, price, quantity, category, purchasedDate);

            if (newItem.isValid()) {
                newItem.setViewName(name);
                newItem.setViewBrand(brand);
                newItem.setViewPrice(price);
                newItem.setViewQuantity(quantity);
                newItem.setViewCategory(category);
                newItem.setViewDate(purchasedDate);

                int index = getItemIndexByCode(code);
                itemList.set(index, newItem);

                ItemFileWriter.updateItemInFile(code, newItem);
                itemsTable.refresh();

                Alert saveConfirm = new Alert(Alert.AlertType.INFORMATION);
                saveConfirm.setHeaderText("Item has been added successfully updated");
                saveConfirm.getDialogPane().setContentText(null);
                saveConfirm.getDialogPane().setMaxHeight(350);
                saveConfirm.getDialogPane().setMaxWidth(400);
                saveConfirm.show();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Do you want to change the image of the item?");
                alert.getDialogPane().setContentText(null);
                alert.getDialogPane().setMaxHeight(350);
                alert.getDialogPane().setMaxWidth(400);
                alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.YES) {
                    onUploadImage();
                }
            }

        } catch (NullPointerException event1) {
            System.out.println("No items in the inventory management database");
        }catch (RuntimeException event2){
            System.out.println(" ");
        }
    }
    private int getItemIndexByCode(String code) {
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getViewCode().equals(code)) {
                return i;
            }
        }
        return -1; // Return -1 if the item is not found
    }

    @FXML
    void recordClicked(MouseEvent e) {
        try{
            viewItems selectedItem = itemsTable.getSelectionModel().getSelectedItem();
            displayItemCode.setText(selectedItem.getViewCode());
            itemNameField.setText(selectedItem.getViewName());
            itemBrandField.setText(selectedItem.getViewBrand());
            itemPriceField.setText(String.valueOf(selectedItem.getViewPrice()));
            itemQuantityField.setText(String.valueOf(selectedItem.getViewQuantity()));
            itemCategoryField.setText(selectedItem.getViewCategory());
            itemPurchaseDateField.setValue(selectedItem.getViewDate());
            String imagePath = "file:"+targetFolder.getAbsolutePath()+"\\"+selectedItem.getViewCode()+".png";

            itemView.setImage(new Image(imagePath));

        }catch (RuntimeException re){
            System.out.println("Click on a record");
        }
    }

    @FXML
    public void onClearClick(MouseEvent event) {
        clearFields();
    }
    @FXML
    public void clearFields() {
        displayItemCode.setText("");
        itemNameField.clear();
        itemBrandField.clear();
        itemPriceField.clear();
        itemQuantityField.clear();
        itemCategoryField.clear();
        itemPurchaseDateField.setValue(null);
        itemView.setImage(null);
    }

    public File targetFolder = new File("userInputImages");

    @FXML
    private List<File> onUploadImage() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Pictures");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.png"));
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        if (!selectedFiles.isEmpty()) {
            File firstImageFile = selectedFiles.get(0);
            Image image = new Image(firstImageFile.toURI().toString());
            itemView.setImage(image);
            addPicturesToFolder(selectedFiles);
        }
        return selectedFiles;
    }

    @FXML
    private void addPicturesToFolder(List<File> sourceFiles) throws IOException {
        for (File sourceFile : sourceFiles) {

            String itemCode = displayItemCode.getText(); // Get the itemCode from the text field

            if(itemCode==null||itemCode.trim().isEmpty()){
                break;
            }else {
                File targetFile = new File(targetFolder.getAbsolutePath() + "/" + itemCode + ".png");
                Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
}

