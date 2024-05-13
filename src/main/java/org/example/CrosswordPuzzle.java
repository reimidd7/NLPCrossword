package org.example;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CrosswordPuzzle {

    private static long trainStartTime;
    private static long trainEndTime;
    private static long trainDuration;

    private static long solveStartTime;
    private static long solveEndTime;
    private static long solveDuration;

    private static int count = 0;

    private static List<String> correctAnswers = new ArrayList<>(FileParser.getAcrossAnswers());


    public static boolean checkAnswers() {
        List<String> clues = new ArrayList<>(FileParser.getAcrossClues());
        clues.addAll(FileParser.getDownClues());

        try {
            solveStartTime = System.currentTimeMillis();
            List<String> aiSolutions = CrosswordSolver.solveCrossword("trainedModel.bin", clues);

            solveEndTime = System.currentTimeMillis();
            solveDuration = (solveEndTime - solveStartTime);


            correctAnswers.addAll(FileParser.getDownAnswers());

            boolean result = true;
            // Compare AI solutions with correct answers
            for (int i = 0; i < correctAnswers.size(); i++) {
                String aiSolution = aiSolutions.get(i);
                String correctAnswer = correctAnswers.get(i);
                if (!aiSolution.equalsIgnoreCase(correctAnswer)) {
                    count++;
                    System.out.println("    Wrong " + aiSolutions.get(i) + " real: " + correctAnswers.get(i) + "  " + clues.get(i));
                    result = false;
                } else {
                    System.out.println("Correct " + aiSolutions.get(i) + " real: " + correctAnswers.get(i) + "  " + clues.get(i));

                }
            }

            writeEvalToFile();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while checking AI solutions.");
            return false;
        }
    }

    public static void trainingTheModel(LocalDate theDate, int theCutoff, int theIterations) throws IOException {
        trainStartTime = System.currentTimeMillis();
        if (theDate.isBefore(LocalDate.of(1980, 12, 7))) {
            CrosswordSolver.trainModel("training_Data1.txt", "trainedModel.bin", theCutoff, theIterations);
            System.out.println("Training model with: training_Data1.txt");
        } else if (theDate.isBefore(LocalDate.of(1984, 8, 26))) {
            CrosswordSolver.trainModel("training_Data2.txt", "trainedModel.bin", theCutoff, theIterations);
            System.out.println("Training model with: training_Data2.txt");
        } else if (theDate.isBefore(LocalDate.of(1988, 5, 25))) {
            CrosswordSolver.trainModel("training_Data3.txt", "trainedModel.bin", theCutoff, theIterations);
            System.out.println("Training model with: training_Data3.txt");
        } else if (theDate.isBefore(LocalDate.of(1992, 2, 9))) {
            CrosswordSolver.trainModel("training_Data4.txt", "trainedModel.bin", theCutoff, theIterations);
            System.out.println("Training model with: training_Data4.txt");
        } else if (theDate.isBefore(LocalDate.of(1995, 10, 14))) {
            CrosswordSolver.trainModel("training_Data5.txt", "trainedModel.bin", theCutoff, theIterations);
            System.out.println("Training model with: training_Data5.txt");
        } else if (theDate.isBefore(LocalDate.of(1999, 5, 15))) {
            CrosswordSolver.trainModel("training_Data6.txt", "trainedModel.bin", theCutoff, theIterations);
            System.out.println("Training model with: training_Data6.txt");
        } else if (theDate.isBefore(LocalDate.of(2002, 11, 17))) {
            CrosswordSolver.trainModel("training_Data7.txt", "trainedModel.bin", theCutoff, theIterations);
            System.out.println("Training model with: training_Data7.txt");
        } else if (theDate.isBefore(LocalDate.of(2006, 4, 16))) {
            CrosswordSolver.trainModel("training_Data8.txt", "trainedModel.bin", theCutoff, theIterations);
            System.out.println("Training model with: training_Data8.txt");
        } else if (theDate.isBefore(LocalDate.of(2009, 6, 29))) {
            CrosswordSolver.trainModel("training_Data9.txt", "trainedModel.bin", theCutoff, theIterations);
            System.out.println("Training model with: training_Data9.txt");
        } else if (theDate.isBefore(LocalDate.of(2012, 7, 25))) {
            CrosswordSolver.trainModel("training_Data10.txt", "trainedModel.bin", theCutoff, theIterations);
            System.out.println("Training model with: training_Data10.txt");
        } else {
            CrosswordSolver.trainModel("training_Data11.txt", "trainedModel.bin", theCutoff, theIterations);
            System.out.println("Training model with: training_Data11.txt");
        }

        trainEndTime = System.currentTimeMillis();
        trainDuration = (trainEndTime - trainStartTime);
        System.out.println("\n\n\n\n\n" + trainDuration);
        //writeEvalToFile();
    }

    public static void writeEvalToFile() {
        try {
            FileWriter fileWriter = new FileWriter("EvalData.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("Crossword Date: " + FileParser.getDateOf() + "\nTraining Data File: " + CrosswordSolver.dataFile);
            bufferedWriter.newLine();
            bufferedWriter.write("Training Time: " + trainDuration + " milliseconds");
            bufferedWriter.newLine();
            bufferedWriter.write("Solving Time: " + solveDuration + " milliseconds");
            bufferedWriter.newLine();
            System.out.println();
            int percent = count/correctAnswers.size() * 100;
            bufferedWriter.write(count + "/" + correctAnswers.size() + " --- " + percent);
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.close();

            System.out.println("Content has been written to the file successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}
