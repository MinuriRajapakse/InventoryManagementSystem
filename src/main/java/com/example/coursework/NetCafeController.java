package com.example.coursework;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class NetCafeController {
    @FXML
    public ImageView imageView;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Label validLogin;


    @FXML
    public void onClickButton(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loginPage.fxml")));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onContinueButtonClick(ActionEvent e) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (!Objects.equals(username, "1") || !Objects.equals(password, "1")) {
            validLogin.setText("Invalid username or password");
        } else {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("welcomePage.fxml")));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        if (username.isEmpty() || password.isEmpty()) {
            validLogin.setText("Please enter all the details");
        }
    }

    @FXML
    public void onManageItemsButtonClick(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("manageItemsPage.fxml")));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onAddItemButtonClick(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addItems.fxml")));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onDeleteItemButtonClick(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("deleteItems.fxml")));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onUpdateItemButtonClick(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("updateItems.fxml")));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onViewItemButtonClick(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("viewItems.fxml")));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onManageDealersButtonClick(MouseEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("manageDealersPage.fxml")));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onRandomClick(MouseEvent event) throws IOException {
        Alert randomlySelected = new Alert(Alert.AlertType.INFORMATION);
        randomlySelected.setHeaderText(" Click 'OK' to view the random selected dealers ");
        randomlySelected.getButtonTypes().setAll(ButtonType.OK,ButtonType.CANCEL);
        ButtonType result = randomlySelected.showAndWait().orElse(ButtonType.CANCEL);

        if(result==ButtonType.OK){
            SelectRandomDealers.selectDealer();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("viewSelectedDealers.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void onViewDealerClick(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("viewAllDealers.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onViewRandomDealerClick(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("lastSelectedDealers.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onExitButtonClick(MouseEvent event) {
        Alert randomlySelected = new Alert(Alert.AlertType.CONFIRMATION);
        randomlySelected.setHeaderText(" Do you want to exit ? ");
        randomlySelected.getButtonTypes().setAll(ButtonType.YES,ButtonType.NO);
        ButtonType result = randomlySelected.showAndWait().orElse(ButtonType.NO);

        if(result==ButtonType.YES){
            System.exit(0);
        }
    }
}