package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Dish {
    private int restro_id;
    private Map<Double, DishDetails> dishMap;

    public Dish(int restro_id, Map<Double, DishDetails> dishMap) {
        this.restro_id = restro_id;
        this.dishMap = dishMap;
    }

    public int getRestroId() {
        return restro_id;
    }

    public Map<Double, DishDetails> getDishMap() {
        return dishMap;
    }

    public void setRestroId(int restro_id) {
        this.restro_id = restro_id;
    }

    public void setDishMap(Map<Double, DishDetails> dishMap) {
        this.dishMap = dishMap;
    }

    public static void main(String[] args) throws IOException {
        Map<Integer, Dish> dishMap = new HashMap<>();

        BufferedReader dishReader = Files.newBufferedReader(Paths.get("C:\\Users\\arman\\IdeaProjects\\shardaswiggy\\data\\Dish.csv"),
                StandardCharsets.UTF_8
        );

        String line;
        while ((line = dishReader.readLine()) != null) {
            String[] partsOfLine = line.split(",");

            if (partsOfLine.length >= 4) {
                int restro_id = Integer.parseInt(partsOfLine[0].trim());
                double dishId = Double.parseDouble(partsOfLine[1].trim());
                String dishName = partsOfLine[2].trim();
                double dishPrice = Double.parseDouble(partsOfLine[3].trim());

                if (dishMap.containsKey(restro_id)) {
                    Dish existingDish = dishMap.get(restro_id);
                    existingDish.getDishMap().put(dishId, new DishDetails(dishName, dishPrice));
                } else {
                    Map<Double, DishDetails> dishDetailsMap = new HashMap<>();
                    dishDetailsMap.put(dishId, new DishDetails(dishName, dishPrice));
                    Dish newDish = new Dish(restro_id, dishDetailsMap);
                    dishMap.put(restro_id, newDish);
                }
            }
        }

        dishReader.close();

        int targetRestroId = 3;
        Dish targetDish = dishMap.get(targetRestroId);
        if (targetDish != null) {
            System.out.println("Restaurant ID: " + targetDish.getRestroId());
            System.out.println("Dishes:");
            for (Map.Entry<Double, DishDetails> entry : targetDish.getDishMap().entrySet()) {
                double dishId = entry.getKey();
                DishDetails dishDetails = entry.getValue();
                System.out.println("Dish ID: " + dishId + ", Dish Name: " + dishDetails.getDishName() +
                        ", Dish Price: " + dishDetails.getDishPrice());
            }
        } else {
            System.out.println("Restaurant ID not found.");
        }
    }

    public static class DishDetails {
        public String dishName;
        public double dishPrice;

        public DishDetails(String dishName, double dishPrice) {
            this.dishName = dishName;
            this.dishPrice = dishPrice;
        }

        public String getDishName() {
            return dishName;
        }

        public double getDishPrice() {
            return dishPrice;
        }
    }
}


