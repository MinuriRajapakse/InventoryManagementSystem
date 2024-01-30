package com.example.coursework;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SelectRandomDealers {
    public static void selectDealer() {
        List<String> readLines = readAllLines();

        if (readLines.size() < 4) {
            return;
        }

        Collections.shuffle(readLines, new Random());

        String[] selectedDealers = readLines.subList(0, 4).toArray(new String[4]);

        int locationIndex = 4;
        int minIndex = 0;
        for (int i = 0; i < selectedDealers.length - 1; i++) {
            minIndex = i;
            for (int j = i + 1; j < selectedDealers.length; j++) {
                String[] dealer1Info = selectedDealers[minIndex].split(",");
                String[] dealer2Info = selectedDealers[j].split(",");

                // Compare dealers based on their location
                if (dealer1Info.length > locationIndex && dealer2Info.length > locationIndex) {
                    String location1 = dealer1Info[locationIndex].trim();
                    String location2 = dealer2Info[locationIndex].trim();

                    if (location1.compareTo(location2) > 0) {
                        minIndex = j;
                    }
                }
            }

            if (minIndex != i) {
                String temp = selectedDealers[i];
                selectedDealers[i] = selectedDealers[minIndex];
                selectedDealers[minIndex] = temp;
            }
        }

        try {
            File selectedDealersFile = new File("SelectedDealers.txt");
            FileWriter writer = new FileWriter(selectedDealersFile);
            for (String selectedDealer : selectedDealers) {
                writer.write(selectedDealer + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> readAllLines() {
        List<String> lines = new ArrayList<>();
        try {
            File file = new File("Dealers.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }
}