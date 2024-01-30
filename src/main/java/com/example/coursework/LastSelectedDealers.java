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
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class LastSelectedDealers {
    @FXML
    public ImageView imageView;
    @FXML
    public ImageView backButtonView;
    @FXML
    private ViewDealers viewDealers;

    @FXML
    private TableView<ViewDealers> dealersTable;

    @FXML
    private TableColumn<ViewDealers, String> viewDealerContactNo;

    @FXML
    private TableColumn<ViewDealers, String> viewDealerLocation;

    @FXML
    private TableColumn<ViewDealers, String> viewDealerName;

    @FXML
    private TableColumn<ViewDealers, LocalDate> viewDealerRegDate;

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    @FXML
    ObservableList<ViewDealers> dealersList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        try {
            try {
                viewDealerRegDate.setCellValueFactory(new PropertyValueFactory<>("registerDate"));
                viewDealerName.setCellValueFactory(new PropertyValueFactory<>("dealerName"));
                viewDealerContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
                viewDealerLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

                dealersList.addAll(readDataFromDealerFile("SelectedDealers.txt"));

            } catch (RuntimeException re) {
                re.printStackTrace();
            }
            dealersTable.setItems(dealersList);

        } catch (RuntimeException re) {
            re.printStackTrace();
        }
    }

    @FXML
    public List<ViewDealers> readDataFromDealerFile(String fileName) {
        List<ViewDealers> dealerItems = new ArrayList<>();
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] details = line.split(",");
                LocalDate dealerRegDate = LocalDate.parse(details[1]);
                String dealerName = details[2];
                String dealerContact = details[3];
                String dealerLocation = details[4];

                dealerItems.add(new ViewDealers(dealerRegDate, dealerName, dealerContact, dealerLocation));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dealerItems;
    }

    @FXML
    void onLastSelectedDealersBack(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("manageDealersPage.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

