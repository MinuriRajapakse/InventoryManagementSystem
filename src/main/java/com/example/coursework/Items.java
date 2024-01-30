package com.example.coursework;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;


public class Items {
    private static String itemCode;
    private static String itemName;
    private static String itemBrand;
    private final double price;
    private final int quantity;
    private static String category;
    private static LocalDate purchaseDate;
    private static String image;

    public Items(String itemCode, String itemName, String itemBrand, double price, int quantity, String category, LocalDate purchaseDate, String image) {
        Items.itemCode = itemCode;
        Items.itemName = itemName;
        Items.itemBrand = itemBrand;
        this.price = price;
        this.quantity = quantity;
        Items.category = category;
        Items.purchaseDate = purchaseDate;
        Items.image = image;
    }

    public String getItemCode() {
        return itemCode;
    }

    public static String getItemName() {
        return itemName;
    }

    public String getItemBrand() {
        return itemBrand;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isValid() {
        return isItemCodeValid(itemCode) && isItemNameValid(itemName) && isItemBrandValid(itemBrand) && isItemPriceValid(price) && isItemQuantityValid(quantity) && isItemCategoryValid(category) && isItemPurchasedDateValid(purchaseDate) && isItemImageValid(image);
    }

    public static boolean isItemCodeValid(String itemCode) {
        if (itemCode.isEmpty()) {
            return false;
        } else
            try {
                File file = new File("Inventory.txt");
                Scanner inventoryFile = new Scanner(file);
                while (inventoryFile.hasNextLine()) {
                    String line = inventoryFile.nextLine();
                    String[] details = line.split(",");

                    String textFileItemCode = details[0].trim();
                    if (itemCode.equals(textFileItemCode)) {
                        return false;
                    }
                }
            } catch (FileNotFoundException fe) {
                System.out.println("File not found");
            }

        return true;
    }

    public static boolean isItemNameValid(String itemName) {
        if (itemName.isEmpty()) {
            return false;
        } else {
            for (char letter:itemName.toCharArray()) {
                if (Character.isDigit(letter)) {
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean isItemBrandValid(String itemBrand) {
        if (itemBrand.isEmpty()) {
            return false;
        }
        return true;
    }
    public static boolean isItemPriceValid(double price) {
        if (price < 1) {
            return false;
        }
        return true;
    }
    public static boolean isItemQuantityValid(int quantity) {
        return quantity > 0;
    }
    public static boolean isItemCategoryValid(String category) {
        if (category.isEmpty()) {
            return false;
        } else {
            for (char letter : category.toCharArray()) {
                if (Character.isDigit(letter)) {
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean isItemPurchasedDateValid(LocalDate purchaseDate) {
        if (purchaseDate == null) {
            return false;
        } else return !purchaseDate.isAfter(LocalDate.now());
    }
    public static boolean isItemImageValid(String image) {
        return !image.isEmpty();
    }

    public static void result(){
        if(!isItemCodeValid(itemCode)){
            displayAlert("Error in item code");
        }else if(!isItemNameValid(itemName)){
            displayAlert("Error in item name");
        } else if (!isItemBrandValid(itemBrand)) {
            displayAlert("Error in item brand");
        } else if (!isItemCategoryValid(category)){
            displayAlert("Error in category ");
        } else if (!isItemPurchasedDateValid(purchaseDate)) {
            displayAlert("Error in purchased date");
        }
    }
    private static void displayAlert(String message){
        Alert warning = new Alert(Alert.AlertType.ERROR);
        warning.setHeaderText(message);
        warning.getButtonTypes().setAll(ButtonType.OK);
        warning.show();
    }
}









