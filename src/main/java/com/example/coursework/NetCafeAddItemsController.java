package com.example.coursework;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;

public class NetCafeAddItemsController {
    @FXML
    public ImageView imageView;
    @FXML
    public Button addButton;
    @FXML
    public ImageView backButtonView;
    @FXML
    private TextField itemBrandField;
    @FXML
    private TextField itemCategoryField;
    @FXML
    private TextField itemCodeField;
    @FXML
    private TextField itemNameField;
    @FXML
    private TextField itemPriceField;
    @FXML
    private DatePicker itemPurchaseDateField;
    @FXML
    private TextField itemQuantityField;
    @FXML
    private ImageView itemView;
    @FXML
    private Items newItem;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;
    @FXML
    private File targetFolder = new File("userInputImages");


    @FXML
    void initialize() {
        itemCodeField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                validateItemCode(newValue);
            } catch (IllegalAccessException e) {
                showErrorMessage(e.getMessage());
            }
        });
    }

    @FXML
    void onAddBack(MouseEvent contextMenuEvent) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("manageItemsPage.fxml")));
        stage = (Stage) ((Node) contextMenuEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onAddClick(ActionEvent event) {
        try {
            String code = itemCodeField.getText();
            String name = itemNameField.getText();
            String brand = itemBrandField.getText();
            String priceStr = itemPriceField.getText();
            String quantityStr = itemQuantityField.getText();
            String category = itemCategoryField.getText();
            LocalDate purchasedDate = itemPurchaseDateField.getValue();
            String image = itemView.getImage().getUrl();

            double price = Double.parseDouble(priceStr);
            int quantity = Integer.parseInt(quantityStr);

            newItem = new Items(code, name, brand, price, quantity, category, purchasedDate, image);

            if (newItem.isValid()) {
                Alert addConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                addConfirm.setHeaderText("Do you want to save this item ?");
                addConfirm.getDialogPane().setContentText(null);
                addConfirm.getButtonTypes().setAll(ButtonType.YES,ButtonType.NO);
                Optional<ButtonType> result = addConfirm.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.YES) {
                    ItemFileWriter.writeItemToFile(newItem, "Inventory.txt");
                }
            }else{
                Items.result();
            }

        } catch (NumberFormatException e) {
            showErrorMessage("Please check whether the price and quantity are numeric");
        }catch(NullPointerException e){
            showErrorMessage(" Please check whether you have inserted an image");
        }
    }

    @FXML
    void validateItemCode(String itemCode) throws IllegalAccessException {
        if (itemCode.trim().isEmpty()) {
            throw new IllegalAccessException("Item code cannot be empty");
        } else {
            try {
                File file = new File("Inventory.txt");
                Scanner inventoryFile = new Scanner(file);
                while (inventoryFile.hasNextLine()) {
                    String line = inventoryFile.nextLine();
                    String[] details = line.split(",");

                    String textFileItemCode = details[0].trim();
                    if (itemCode.equals(textFileItemCode)) {
                        throw new IllegalAccessException("Entered item code has been used");
                    }
                }
            } catch (FileNotFoundException fe) {
                System.out.println("File not found");
            }
        }
    }

    @FXML
    public List<File> onUploadImage() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Pictures");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png"));
        fileChooser.setInitialFileName(itemCodeField.getText());
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        if (selectedFiles != null) {
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
            String itemCode = itemCodeField.getText(); // Get the itemCode from the text field
            String originalFileName = sourceFile.getName();
            String extension = getFileExtension(originalFileName); // Get the file extension of the source file

            if (itemCode == null || itemCode.trim().isEmpty()) {
                break;
            } else {
                File targetFile = new File(targetFolder.getAbsolutePath() + "/" + itemCode + extension);
                Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    @FXML
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex);
        }
        return "";
    }

    @FXML
    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void onManageItemsButtonClick(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("manageItemsPage.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onManageDealersButtonClick(MouseEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("manageDealersPage.fxml")));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onClearClick(MouseEvent event) {
        clearFields();
    }
    @FXML
    public void clearFields() {
        itemCodeField.clear();
        itemNameField.clear();
        itemBrandField.clear();
        itemPriceField.clear();
        itemQuantityField.clear();
        itemCategoryField.clear();
        itemPurchaseDateField.setValue(null);
        itemView.setImage(null);
    }

    public void oExitButtonClick(MouseEvent event) {
        System.exit(0);
    }
}
