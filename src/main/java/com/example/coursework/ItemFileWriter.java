package com.example.coursework;

import java.io.*;

public class ItemFileWriter {
    public static void writeItemToFile(Items item, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            String itemDetails = item.getItemCode() + "," + Items.getItemName() + "," +
                    item.getItemBrand() + "," +
                    item.getPrice() + "," +
                    item.getQuantity() + "," +
                    item.getCategory() + "," +
                    item.getPurchaseDate()  + "\n";
            fileWriter.write(itemDetails);
            System.out.println("Item details saved to file");
        } catch (IOException e) {
            System.out.println("Error saving item details to file: " + e.getMessage());
        }

    }
    public static void deleteItemFromFile(String itemCode) {
        File file = new File("Inventory.txt");
        try(RandomAccessFile deletedItemFile = new RandomAccessFile(file,"rw")){
            StringBuilder sb =new StringBuilder();
            String line;

            while ((line=deletedItemFile.readLine())!=null){
                String [] details = line.split(",");
                if(!details[0].equals(itemCode)) {
                    sb.append(line).append(System.lineSeparator());
                }
            }
            deletedItemFile.setLength(0);
            deletedItemFile.writeBytes(sb.toString());
            System.out.println("Item with code "+itemCode+" deleted successfully");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error deleting item: " + e.getMessage());
        }
    }

    public static void updateItemInFile(String itemCode, NetCafeDeleteUpdateItemsController.viewItems updatedItem) {
        File file = new File("Inventory.txt");
        // Overwrite the file
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            long fileLength = raf.length();
            long currentPosition = 0;

            while (currentPosition < fileLength) {
                String line = raf.readLine();
                if (line != null) {
                    String[] details = line.split(",");
                    if (details[0].equals(itemCode)) {
                        raf.seek(currentPosition);
                        String updatedLine =updatedItem.getViewCode() + "," + updatedItem.getViewName() + "," +updatedItem.getViewBrand() + ","
                                +updatedItem.getViewPrice() + "," +
                                        updatedItem.getViewQuantity() + "," +
                                        updatedItem.getViewCategory() + "," +
                                        updatedItem.getViewDate() +System.lineSeparator();

                        raf.writeBytes(updatedLine);
                        break;
                    }
                    currentPosition = raf.getFilePointer();
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error updating item: " + e.getMessage());
        }
    }
}

