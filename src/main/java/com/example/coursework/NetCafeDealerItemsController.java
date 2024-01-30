package com.example.coursework;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class NetCafeDealerItemsController {
    @FXML
    public ImageView backButtonView;
    @FXML
    public ImageView imageView;
    @FXML
    private Text firstDealerName;
    @FXML
    private Text fourthDealerName;
    @FXML
    private Text secondDealerName;
    @FXML
    private Text selectedDealerContactNo;
    @FXML
    private Text selectedDealerLocation;
    @FXML
    private Text selectedDealerRegDate;
    @FXML
    private Text thirdDealerName;
    @FXML
    private TableView<dealerItems> dealersItemTable;
    @FXML
    private TableColumn<dealerItems, String> viewDealerItemBrand;
    @FXML
    private TableColumn<dealerItems, String> viewDealerItemName;
    @FXML
    private TableColumn<dealerItems, Double> viewDealerItemPrice;
    @FXML
    private TableColumn<dealerItems, Integer> viewDealerItemQuantity;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    ObservableList<dealerItems> dealerItemList = FXCollections.observableArrayList();

    public void initialize(){
        String[] firstDealer = displaySelectedDealerDetails().get(0);
        String[] secondDealer = displaySelectedDealerDetails().get(1);
        String[] thirdDealer = displaySelectedDealerDetails().get(2);
        String[] fourthDealer = displaySelectedDealerDetails().get(3);

        firstDealerName.setText(firstDealer[2]);
        secondDealerName.setText(secondDealer[2]);
        thirdDealerName.setText(thirdDealer[2]);
        fourthDealerName.setText(fourthDealer[2]);
    }

    public List<String> getDealerItemCode() {

        List<String> selectedDealersCodes = new ArrayList<>();
        try {
            File file = new File("SelectedDealers.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] details = line.split(",");
                String dealerCode = details[0];
                selectedDealersCodes.add(dealerCode);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return selectedDealersCodes;
    }
    public List<String[]> displaySelectedDealerDetails(){
        List<String[]> dealerDataList = new ArrayList<>();
        try {
            File file = new File("SelectedDealers.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] details = line.split(",");
                dealerDataList.add(details);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return dealerDataList;

    }

    @FXML
    void onBackClick(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("manageDealersPage.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onDealerOneClick(MouseEvent event) {
        dealerItemList.clear();
        selectedDealerRegDate.setText(displaySelectedDealerDetails().get(0)[1]);
        selectedDealerContactNo.setText(displaySelectedDealerDetails().get(0)[3]);
        selectedDealerLocation.setText(displaySelectedDealerDetails().get(0)[4]);

        List<dealerItems> firstDealersItems = new ArrayList<>();
        try {
            File file = new File("DealerItems.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] details = line.split(",");
                String existItemCode = details[0];
                if(existItemCode.equals(getDealerItemCode().get(0))){
                    String itemName = details[1];
                    String itemBrand = details[2];
                    double itemPrice = Double.parseDouble(details[3]);
                    int itemQuantity = Integer.parseInt(details[4]);
                    firstDealersItems.add(new dealerItems(itemName,itemBrand,itemPrice,itemQuantity));
                }
            }
            try {
                viewDealerItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
                viewDealerItemBrand.setCellValueFactory(new PropertyValueFactory<>("itemBrand"));
                viewDealerItemPrice.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
                viewDealerItemQuantity.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));

                dealerItemList.addAll(firstDealersItems);

            } catch (RuntimeException re) {
                re.printStackTrace();
            }
            dealersItemTable.setItems(dealerItemList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onDealerTwoClick(MouseEvent event) {
        dealerItemList.clear();
        selectedDealerRegDate.setText(displaySelectedDealerDetails().get(1)[1]);
        selectedDealerContactNo.setText(displaySelectedDealerDetails().get(1)[3]);
        selectedDealerLocation.setText(displaySelectedDealerDetails().get(1)[4]);
        List<dealerItems> secondDealersItems = new ArrayList<>();
        try {
            File file = new File("DealerItems.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] details = line.split(",");
                String existItemCode = details[0];
                if(existItemCode.equals(getDealerItemCode().get(1))){
                    String itemName = details[1];
                    String itemBrand = details[2];
                    double itemPrice = Double.parseDouble(details[3]);
                    int itemQuantity = Integer.parseInt(details[4]);
                    secondDealersItems.add(new dealerItems(itemName,itemBrand,itemPrice,itemQuantity));
                }
            }
            try {
                viewDealerItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
                viewDealerItemBrand.setCellValueFactory(new PropertyValueFactory<>("itemBrand"));
                viewDealerItemPrice.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
                viewDealerItemQuantity.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));

                dealerItemList.addAll(secondDealersItems);

            } catch (RuntimeException re) {
                re.printStackTrace();
            }
            dealersItemTable.setItems(dealerItemList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onDealerThreeClick(MouseEvent event) {
        dealerItemList.clear();
        selectedDealerRegDate.setText(displaySelectedDealerDetails().get(2)[1]);
        selectedDealerContactNo.setText(displaySelectedDealerDetails().get(2)[3]);
        selectedDealerLocation.setText(displaySelectedDealerDetails().get(2)[4]);
        List<dealerItems> thirdDealersItems = new ArrayList<>();
        try {
            File file = new File("DealerItems.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] details = line.split(",");
                String existItemCode = details[0];
                if(existItemCode.equals(getDealerItemCode().get(2))){
                    String itemName = details[1];
                    String itemBrand = details[2];
                    double itemPrice = Double.parseDouble(details[3]);
                    int itemQuantity = Integer.parseInt(details[4]);
                    thirdDealersItems.add(new dealerItems(itemName,itemBrand,itemPrice,itemQuantity));
                }
            }
            try {
                viewDealerItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
                viewDealerItemBrand.setCellValueFactory(new PropertyValueFactory<>("itemBrand"));
                viewDealerItemPrice.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
                viewDealerItemQuantity.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));

                dealerItemList.addAll(thirdDealersItems);

            } catch (RuntimeException re) {
                re.printStackTrace();
            }
            dealersItemTable.setItems(dealerItemList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onDealerFourClick(MouseEvent event) {
        dealerItemList.clear();
        selectedDealerRegDate.setText(displaySelectedDealerDetails().get(3)[1]);
        selectedDealerContactNo.setText(displaySelectedDealerDetails().get(3)[3]);
        selectedDealerLocation.setText(displaySelectedDealerDetails().get(3)[4]);
        List<dealerItems> fourthDealersItems = new ArrayList<>();
        try {
            File file = new File("DealerItems.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] details = line.split(",");
                String existItemCode = details[0];
                if(existItemCode.equals(getDealerItemCode().get(3))){
                    String itemName = details[1];
                    String itemBrand = details[2];
                    double itemPrice = Double.parseDouble(details[3]);
                    int itemQuantity = Integer.parseInt(details[4]);
                    fourthDealersItems.add(new dealerItems(itemName,itemBrand,itemPrice,itemQuantity));
                }
            }
            try {
                viewDealerItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
                viewDealerItemBrand.setCellValueFactory(new PropertyValueFactory<>("itemBrand"));
                viewDealerItemPrice.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
                viewDealerItemQuantity.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));

                dealerItemList.addAll(fourthDealersItems);

            } catch (RuntimeException re) {
                re.printStackTrace();
            }
            dealersItemTable.setItems(dealerItemList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onRandomDealersBack(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("manageDealersPage.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static class dealerItems{
        private String itemName;
        private String itemBrand;
        private double itemPrice;
        private int itemQuantity;

        public dealerItems(String itemName, String itemBrand, double itemPrice,int itemQuantity) {
            this.itemName = itemName;
            this.itemBrand = itemBrand;
            this.itemPrice = itemPrice;
            this.itemQuantity = itemQuantity;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemBrand() {
            return itemBrand;
        }

        public void setItemBrand(String itemBrand) {
            this.itemBrand = itemBrand;
        }

        public double getItemPrice() {
            return itemPrice;
        }

        public void setItemPrice(double itemPrice) {
            this.itemPrice = itemPrice;
        }

        public int getItemQuantity() {
            return itemQuantity;
        }

        public void setItemQuantity(int itemQuantity) {
            this.itemQuantity = itemQuantity;
        }
    }
}