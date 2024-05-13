//package org.example;
//
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//
//import java.io.*;
//import java.net.URL;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//public class CreateTrainingData {
//
//    public static List<String> acrossAnswers = new ArrayList<>();
//    public static List<String> downAnswers = new ArrayList<>();
//    public static String author = "";
//    public static List<String> acrossClues = new ArrayList<>();
//    public static List<String> downClues = new ArrayList<>();
//    public static String copyright = "";
//    public static String date = "";
//    public static List<String> grid = new ArrayList<>();
//    public static List<String> gridNums = new ArrayList<>();
//    public static long cols = 0;
//    public static long rows = 0;
//
//    public static void main(String[] args) {
//        getFile();
//    }
//
//    public static void parseJSON(String fileName) {
//        JSONParser parser = new JSONParser();
//
//        try {
//            // Parse the JSON file
//            Object obj = parser.parse(new FileReader(fileName));
//
//            // Convert the parsed object into a JSONObject
//            JSONObject jsonObject = (JSONObject) obj;
//
//            // answers across and down
//            JSONObject answers = (JSONObject) jsonObject.get("answers");
//            acrossAnswers = (List<String>) answers.get("across");
//            downAnswers = (List<String>) answers.get("down");
//
//            // author
//            author = (String) jsonObject.get("author");
//
//            // clues
//            JSONObject clues = (JSONObject) jsonObject.get("clues");
//            acrossClues = (List<String>) clues.get("across");
//            downClues = (List<String>) clues.get("down");
//
//            // copyright
//            copyright = (String) jsonObject.get("copyright");
//
//            // Date
//            date = (String) jsonObject.get("date");
//
//            // grid with letters
//            grid = (List<String>) jsonObject.get("grid");
//
//            // grid nums
//            gridNums = (List<String>) jsonObject.get("gridnums");
//
//            // size
//            JSONObject size = (JSONObject) jsonObject.get("size");
//            cols = (long) size.get("cols");
//            rows = (long) size.get("rows");
//
//            // Write clue-answer pairs to trainingData1.txt
//            writeTrainingData();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private static int fileCount = 1;
//    private static long currentFileSize = 0;
//
//    public static void writeTrainingData() {
//        try {
//            for (int i = 0; i < acrossClues.size(); i++) {
//                String answer = acrossAnswers.get(i);
//                String clue = acrossClues.get(i).replaceAll("^\\d+\\.\\s*", ""); // Remove number and period from clue
//                String data = answer.replaceAll("\\.$", "") + " " + clue + "\n"; // Remove period from answer
//
//                // Check if adding this data will exceed the file size limit
//                if (currentFileSize + data.getBytes().length > 2500 * 1024) {
//                    // If it does, increment the file count and reset the current file size
//                    fileCount++;
//                    currentFileSize = 0;
//                    System.out.print("Done----------");
//                }
//
//                // Write the data to the file
//                try (FileWriter writer = new FileWriter("training_Data" + fileCount + ".txt", true)) {
//                    writer.write(data);
//                    currentFileSize += data.getBytes().length;
//                }
//            }
//            for (int i = 0; i < downClues.size(); i++) {
//                String answer = downAnswers.get(i);
//                String clue = downClues.get(i).replaceAll("^\\d+\\.\\s*", ""); // Remove number and period from clue
//                String data = answer.replaceAll("\\.$", "") + " " + clue + "\n"; // Remove period from answer
//
//                // Check if adding this data will exceed the file size limit
//                if (currentFileSize + data.getBytes().length > 2500 * 1024) {
//                    // If it does, increment the file count and reset the current file size
//                    fileCount++;
//                    currentFileSize = 0;
//                    System.out.print("Done----------" );
//                }
//
//                // Write the data to the file
//                try (FileWriter writer = new FileWriter("training_Data" + fileCount + ".txt", true)) {
//                    writer.write(data);
//                    currentFileSize += data.getBytes().length;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static void getFile() {
//        LocalDate startDate = LocalDate.of(1977, 1, 1);
//        LocalDate endDate = LocalDate.of(2014, 12, 31);
//
//        LocalDate date = startDate;
//        while (!date.isAfter(endDate)) {
//            if (!(date.isAfter(LocalDate.of(1978, 8, 10)) && date.isBefore(LocalDate.of(1978, 11, 6)))) {
//                downloadFile(date);
//            }
//            date = date.plusDays(1);
//        }
//    }
//
//    private static void downloadFile(LocalDate date) {
//        int year = date.getYear();
//        int month = date.getMonthValue();
//        int day = date.getDayOfMonth();
//        String repositoryUrl;
//        System.out.println(date);
//
//        if (day < 10) {
//            repositoryUrl = String.format("https://raw.githubusercontent.com/doshea/nyt_crosswords/master/%04d/%02d/0%d.json", year, month, day);
//        } else {
//            repositoryUrl = String.format("https://raw.githubusercontent.com/doshea/nyt_crosswords/master/%04d/%02d/%d.json", year, month, day);
//        }
//
//        try {
//            URL url = new URL(repositoryUrl);
//            java.net.URLConnection conn = url.openConnection();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                FileWriter fileWriter = new FileWriter("data.json");
//                fileWriter.write(line);
//                fileWriter.close();
//                parseJSON("data.json"); // Parse and write to trainingData1.txt
//            }
//
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
