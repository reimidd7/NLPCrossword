//package org.example;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class TrainCrosswordSolver {
//
//    public static void main(String[] args) throws IOException {
//      //  checkData("training_Data6.txt");
//
////        try {
////            // Specify the path to your input file and output file
////            String inputFilePath = "trainingData15.txt";
////            String outputFilePath = "trainingData18.txt";
////
////            // Split the file
////            splitAndModifyFile(inputFilePath, outputFilePath);
////
////            System.out.println("Second half of the file saved to: " + outputFilePath);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
////        try {
////            // Specify the paths to your training data
////            List<String> trainingDataPaths = Arrays.asList("training_Data11.txt");
////
////            // Specify the desired output path for the trained model
////            String modelOutputPath = "trainedModel.bin";
////
////            // Train the model
////            CrosswordSolver.trainModel(trainingDataPaths, modelOutputPath);
////
////            System.out.println("Model trained and saved to: " + modelOutputPath);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }
//    public static void checkData(String filePath) throws IOException {
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                // Trim leading and trailing whitespace
//                line = line.trim();
//
//                // Skip empty lines
//                if (line.isEmpty()) {
//                    System.out.println("Empty line found.");
//                    continue;
//                }
//
//                // Check if the line contains a space (indicating a category and text)
//                if (!line.contains(" ")) {
//                    System.out.println("Line with only category: " + line);
//                }
//            }
//        }
//    }
//
//    public static void splitAndModifyFile(String inputFilePath, String outputFilePath) throws IOException {
//        List<String> lines = new ArrayList<>();
//
//        // Read all lines from the file
//        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                lines.add(line);
//            }
//        }
//
//        // Calculate the start line of the second half
//        int startLine = lines.size() / 2;
//
//        // Write the second half to the output file
//        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath))) {
//            for (int i = startLine; i < lines.size(); i++) {
//                writer.println(lines.get(i));
//            }
//        }
//
//        // Delete the second half from the original file
//        try (PrintWriter writer = new PrintWriter(new FileWriter(inputFilePath))) {
//            for (int i = 0; i < startLine; i++) {
//                writer.println(lines.get(i));
//            }
//        }
//    }
//}
