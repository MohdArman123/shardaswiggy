//Commit 2
package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class restro {
    public static void main(String[] args) throws IOException {

        int restro_id;
        String restro_name;

        BufferedReader restroReader = Files.newBufferedReader(Paths.get("C:\\Users\\arman\\IdeaProjects\\shardaswiggy\\data\\Hotel.csv"),
                StandardCharsets.UTF_8
        );

        String line;
        while ((line = restroReader.readLine()) != null) {
            String[] partsOfLine = line.split(",");

            if (partsOfLine.length >= 2) {
                String idString = partsOfLine[0].trim();
                restro_id = Integer.parseInt(idString);
                restro_name = partsOfLine[1];

                System.out.println("Restaurant ID: " + restro_id);
                System.out.println("Restaurant Name: " + restro_name);
            }
        }

        restroReader.close();
    }
}