package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


// SHOULD I MAKE THE CLUES-ANSWERS A MAP?????


public class FileParser {
    public static List<String> acrossAnswers = new ArrayList<>();
    public static List<String> downAnswers = new ArrayList<>();
    public static String author = "";
    public static List<String> acrossClues = new ArrayList<>();
    public static List<String> downClues = new ArrayList<>();
    public static String copyright = "";
    public static String fileDate = "";
    public static List<String> grid = new ArrayList<>();
    public static List<String> gridNums = new ArrayList<>();
    public static long cols = 0;
    public static long rows = 0;

    public static void parseJSON(LocalDate date) {
        getFile(date);
        JSONParser parser = new JSONParser();

        try {
            // Parse the JSON file
            Object obj = parser.parse(new FileReader("clueFile.json"));

            // Convert the parsed object into a JSONObject
            JSONObject jsonObject = (JSONObject) obj;

            // answers across and down
            JSONObject answers = (JSONObject) jsonObject.get("answers");
            acrossAnswers = (List<String>) answers.get("across");
            downAnswers = (List<String>) answers.get("down");

            // author
            author = (String) jsonObject.get("author");

            // clues
            JSONObject clues = (JSONObject) jsonObject.get("clues");
            acrossClues = (List<String>) clues.get("across");
            downClues = (List<String>) clues.get("down");

            // copyright
            copyright = (String) jsonObject.get("copyright");

            // Date
            fileDate = (String) jsonObject.get("date");

            // grid with letters
            grid = (List<String>) jsonObject.get("grid");

            // grid nums
            gridNums = (List<String>) jsonObject.get("gridnums");

            // size
            JSONObject size = (JSONObject) jsonObject.get("size");
            cols = (long) size.get("cols");
            rows = (long) size.get("rows");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static List<String> getAcrossAnswers() {
        return acrossAnswers;
    }

    public static List<String> getDownAnswers() {
        return downAnswers;
    }

    public static String getAuthor() {
        return author;
    }

    public static List<String> getAcrossClues() {
        return acrossClues;
    }

    public static List<String> getDownClues() {
        return downClues;
    }

    public static String getCopyright() {
        return copyright;
    }

    public static String getDateOf() {
        return fileDate;
    }

    public static List<String> getGrid() {
        return grid;
    }

    public static List<String> getGridNums() {
        return gridNums;
    }

    public static long getCols() {
        return cols;
    }

    public static long getRows() {
        return rows;
    }


    public static List<String> getAllClues() {
        List<String> allClues = new ArrayList<>(acrossClues);
        allClues.addAll(downClues);
        return allClues;
    }

    private static LocalDate getDate() {
        LocalDate startDate = LocalDate.of(1979, 1, 1);
        LocalDate endDate = LocalDate.of(2014, 12, 31);

        // Get the range in days
        long startDays = startDate.toEpochDay();
        long endDays = endDate.toEpochDay();
        long randomDay = startDays + (long) (Math.random() * (endDays - startDays));

        // Convert the random day back to a LocalDate
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

        // Print the random date

        System.out.println("Random date between " + startDate + " and " + endDate + ": " + randomDate);

        return randomDate;
    }


    private static void getFile(LocalDate date) {
        //LocalDate date = getDate();
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        String repositoryUrl = "";


        if (day < 10 && month < 10) {
            repositoryUrl = "https://raw.githubusercontent.com/doshea/nyt_crosswords/master/" + year + "/0" + month + "/0" + day + ".json";

        } else if (day < 10 ) {
            repositoryUrl = "https://raw.githubusercontent.com/doshea/nyt_crosswords/master/" + year + "/" + month + "/0" + day + ".json";

        } else if (month < 10) {
            repositoryUrl = "https://raw.githubusercontent.com/doshea/nyt_crosswords/master/" + year + "/0" + month + "/" + day + ".json";

        } else {
            repositoryUrl = "https://raw.githubusercontent.com/doshea/nyt_crosswords/master/" + year + "/" + month + "/" + day + ".json";

        }

        try {
            URL url = new URL(repositoryUrl);
            java.net.URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                FileWriter fileWriter = new FileWriter("clueFile.json");
                fileWriter.write(line.toString());
                fileWriter.close();

            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
