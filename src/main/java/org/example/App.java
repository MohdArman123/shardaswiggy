package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) throws IOException {

        Map<Integer, String> restaurantMap = readRestaurants();


        System.out.println("Available Restaurants:");
        for (Map.Entry<Integer, String> entry : restaurantMap.entrySet()) {
            int restroId = entry.getKey();
            String restroName = entry.getValue();
            System.out.println("Restaurant ID: " + restroId + ", Name: " + restroName);
        }


        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter the Restaurant ID: ");
        int selectedRestroId = Integer.parseInt(reader.readLine());

        Map<Integer, Dish> dishMap = readDishes();

        Dish selectedDish = dishMap.get(selectedRestroId);
        if (selectedDish != null) {
            System.out.println("Selected Restaurant: " + restaurantMap.get(selectedRestroId));
            System.out.println("Menu:");
            for (Map.Entry<Double, Dish.DishDetails> entry : selectedDish.getDishMap().entrySet()) {
                double dishId = entry.getKey();
                Dish.DishDetails dishDetails = entry.getValue();
                System.out.println("Dish ID: " + dishId + ", Dish Name: " + dishDetails.getDishName() +
                        ", Dish Price: " + dishDetails.getDishPrice());
            }
        } else {
            System.out.println("Invalid Restaurant ID.");
        }

    }

    private static Map<Integer, String> readRestaurants() throws IOException {
        Map<Integer, String> restaurantMap = new HashMap<>();

        BufferedReader restroReader = Files.newBufferedReader(Paths.get("C:\\Users\\arman\\IdeaProjects\\shardaswiggy\\data\\Hotel.csv"),
                StandardCharsets.UTF_8
        );

        String line;
        while ((line = restroReader.readLine()) != null) {
            String[] partsOfLine = line.split(",");

            if (partsOfLine.length >= 2) {
                int restroId = Integer.parseInt(partsOfLine[0].trim());
                String restroName = partsOfLine[1].trim();
                restaurantMap.put(restroId, restroName);
            }
        }

        restroReader.close();

        return restaurantMap;
    }

    private static Map<Integer, Dish> readDishes() throws IOException {
        Map<Integer, Dish> dishMap = new HashMap<>();

        BufferedReader dishReader = Files.newBufferedReader(Paths.get("C:\\Users\\ADITYA\\IdeaProjects\\Sharda_Swiggy\\Sharda_Swiggy\\data\\Dish.csv"),
                StandardCharsets.UTF_8
        );

        String line;
        while ((line = dishReader.readLine()) != null) {
            String[] partsOfLine = line.split(",");

            if (partsOfLine.length >= 4) {
                int restroId = Integer.parseInt(partsOfLine[0].trim());
                double dishId = Double.parseDouble(partsOfLine[1].trim());
                String dishName = partsOfLine[2].trim();
                double dishPrice = Double.parseDouble(partsOfLine[3].trim());

                if (dishMap.containsKey(restroId)) {
                    Dish existingDish = dishMap.get(restroId);
                    existingDish.getDishMap().put(dishId, new Dish.DishDetails(dishName, dishPrice));
                } else {
                    Map<Double, Dish.DishDetails> dishDetailsMap = new HashMap<>();
                    dishDetailsMap.put(dishId, new Dish.DishDetails(dishName, dishPrice));
                    Dish newDish = new Dish(restroId, dishDetailsMap);
                    dishMap.put(restroId, newDish);
                }
            }
        }

        dishReader.close();

        return dishMap;
    }
}
