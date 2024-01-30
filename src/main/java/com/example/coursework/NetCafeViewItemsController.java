package com.example.coursework;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class NetCafeViewItemsController {
    @FXML
    public ImageView backButtonView;
    @FXML
    public ImageView imageView;
    @FXML
    private Label displayItemBrand;

    @FXML
    private Label displayItemCategory;

    @FXML
    private Label displayItemDate;

    @FXML
    private Label displayItemName;

    @FXML
    private Label displayItemPrice;

    @FXML
    private Label displayItemQuantity;

    @FXML
    private TextField itemSearch;

    @FXML
    private ImageView itemView;

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
    private TableColumn<viewItems, ImageView> viewImage;

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
                viewImage.setCellValueFactory(new PropertyValueFactory<>("viewImage"));


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

            int itemListSize = itemList.size();
            boolean swapped;
            for (int i = 0; i < itemListSize - 1; i++) {
                swapped = false;
                for (int j = 0; j < itemListSize - i - 1; j++) {
                    viewItems item1 = itemList.get(j);
                    viewItems item2 = itemList.get(j + 1);

                    if (item1.getViewCategory().compareTo(item2.getViewCategory()) > 0) {
                        itemList.set(j, item2);
                        itemList.set(j + 1, item1);
                        swapped = true;
                    }
                }
                // If no two elements were swapped in the inner loop, the list is already sorted
                if (!swapped) {
                    break;
                }
            }
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

                String imagePath = "file:" + targetFolder.getAbsolutePath() + "\\" + viewCode + ".png";
                Image thumbnailImage = new Image(imagePath);
                ImageView viewThumbnail = new ImageView(thumbnailImage);
                viewThumbnail.setFitHeight(50);
                viewThumbnail.setFitWidth(60);

                savedItems.add(new viewItems(viewCode, viewName, viewBrand, viewPrice, viewQuantity, viewCategory, viewDate, viewThumbnail));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return savedItems;
    }

    @FXML
    void onDeleteBack(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("manageItemsPage.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void viewRecordClicked(MouseEvent event) {
        try {
            viewItems selectedItem = itemsTable.getSelectionModel().getSelectedItem();
            displayItemName.setText(selectedItem.getViewName());
            displayItemBrand.setText(selectedItem.getViewBrand());
            displayItemPrice.setText(String.valueOf(selectedItem.getViewPrice()));
            displayItemQuantity.setText(String.valueOf(selectedItem.getViewQuantity()));
            displayItemCategory.setText(selectedItem.getViewCategory());
            displayItemDate.setText(String.valueOf(selectedItem.getViewDate()));
            String imagePath = "file:" + targetFolder.getAbsolutePath() + "\\" + selectedItem.getViewCode() + ".png";
            itemView.setImage(new Image(imagePath));

        } catch (RuntimeException re) {
            System.out.println("Click on a record");
        }
    }

    public File targetFolder = new File("userInputImages");

    @FXML
    public void onViewItemsBack(MouseEvent event) throws IOException {
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
        private ImageView viewItemImage;

        public viewItems(String viewCode, String viewName, String viewBrand, double viewPrice, int viewQuantity, String viewCategory, LocalDate viewDate, ImageView viewItemImage) {
            this.viewCode = viewCode;
            this.viewName = viewName;
            this.viewBrand = viewBrand;
            this.viewPrice = viewPrice;
            this.viewQuantity = viewQuantity;
            this.viewCategory = viewCategory;
            this.viewDate = viewDate;
            this.viewItemImage = viewItemImage;
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
        public ImageView getViewImage() {
            return viewItemImage;
        }
        public ImageView getViewItemImage() {
            return viewItemImage;
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

        public void setViewItemImage(ImageView viewItemImage) {
            this.viewItemImage = viewItemImage;
        }


    }
}
