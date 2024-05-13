package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CrosswordDisplay extends JFrame {
    private JPanel puzzlePanel;
    private JPanel cluesPanel;
    private JPanel[][] gridPanels;
    private JTextField[][] gridFields;
    private JButton checkButton;
    private JPanel paramsPanel; // Panel for training parameters
    private JTextField cutoffField; // Text field for cutoff parameter
    private JTextField iterationsField; // Text field for iterations parameter
    private JButton submitButton; // Button to submit parameters
    private JButton solveButton; // Button to solve crossword

    private static long trainStartTime;
    private static long trainEndTime;
    private static long trainDuration;

    private LocalDate date;

    public CrosswordDisplay() {
        setDateAndParseJSON();
        createPuzzlePanel();
        createCluesPanel();
        createParamsPanel();
        createTrainAIButton();
        createCheckAnswersButton();

        // Add the scroll panes
        add(new JScrollPane(puzzlePanel), BorderLayout.CENTER);
        add(new JScrollPane(cluesPanel), BorderLayout.EAST);

        // Add the paramsPanel to the frame
        add(paramsPanel, BorderLayout.SOUTH);

        setSize(1200, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


    }

    private void setDateAndParseJSON() {
        String dateInput = JOptionPane.showInputDialog("Choose Date of NYT Crossword Puzzle!\nEnter a date between 1979-01-01 and 2014-12-31 \nin the format YYYY-MM-DD:");
        date = LocalDate.parse(dateInput);

        while (!checkUserDate(date)) {
            dateInput = JOptionPane.showInputDialog("Date not valid try again!\nEnter a date in the format YYYY-MM-DD:");
            date = LocalDate.parse(dateInput);
        }

        FileParser.parseJSON(date);
    }


    private void createCheckAnswersButton() {
        JButton checkAnswersButton = new JButton("Check Answers");
        checkAnswersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CrosswordPuzzle.checkAnswers()) {
                    JOptionPane.showMessageDialog(null, "Congratulations! AI's answers are correct.");
                } else {
                    JOptionPane.showMessageDialog(null, "Sorry, AI's answers are incorrect. Try again!");
                }
            }
        });
        paramsPanel.add(checkAnswersButton);
    }

    private void createTrainAIButton() {
        JButton trainButton = new JButton("Train the AI");
        trainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cutoff = Integer.parseInt(cutoffField.getText());
                int iterations = Integer.parseInt(iterationsField.getText());
                try {
                    CrosswordPuzzle.trainingTheModel(date, cutoff, iterations);
                    System.out.print("passed train");
                    JOptionPane.showMessageDialog(null, "AI Training completed successfully!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred during AI Training!");
                }
            }
        });
        paramsPanel.add(trainButton);
    }

    private boolean checkUserDate(LocalDate date) {
        LocalDate startDate = LocalDate.of(1979, 1, 1);
        LocalDate endDate = LocalDate.of(2014, 12, 31);
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    private void createPuzzlePanel() {
        ArrayList<String> grid = new ArrayList<>(FileParser.getGrid());
        ArrayList<String> gridNums = new ArrayList<>(FileParser.getGridNums());
        int rows = (int) FileParser.getRows();
        int cols = (int) FileParser.getCols();

        // Create the puzzle panel with a fixed size and make it scrollable
        puzzlePanel = new JPanel(new GridLayout(rows, cols));
        JScrollPane puzzleScrollPane = new JScrollPane(puzzlePanel);
        gridFields = new JTextField[rows][cols];
        gridPanels = new JPanel[rows][cols];

        int gridIndex = 0; // Index to keep track of the position in the grid list

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gridFields[i][j] = new JTextField();
                gridFields[i][j].setEditable(false);

                // Get the character at the current index in the grid list
                String cell = String.valueOf(grid.get(gridIndex));
                String cellNum = String.valueOf(gridNums.get(gridIndex++));

                gridPanels[i][j] = new JPanel(new BorderLayout());
                if (cell.equals(".")) {
                    gridFields[i][j].setBackground(Color.BLACK);
                } else {
                    if (!cellNum.equals("0")) {
                        JLabel clueNum = new JLabel(cellNum);
                        clueNum.setHorizontalAlignment(JLabel.LEFT);
                        gridPanels[i][j].setBackground(Color.white);
                        gridPanels[i][j].add(clueNum, BorderLayout.PAGE_START);
                    }
                    gridFields[i][j].setBackground(Color.WHITE);
                }
                gridPanels[i][j].add(gridFields[i][j], BorderLayout.CENTER);
                puzzlePanel.add(gridPanels[i][j]);
            }
        }
        puzzlePanel.setSize(700, 850);

    }

    private void createCluesPanel() {
        // Create the clues panel and make it scrollable
        cluesPanel = new JPanel();
        cluesPanel.setLayout(new BoxLayout(cluesPanel, BoxLayout.Y_AXIS));
        JScrollPane cluesScrollPane = new JScrollPane(cluesPanel);
        cluesPanel.setSize(500, 400);
        cluesPanel.add(new JLabel("Across:"));
        for (String clue : FileParser.getAcrossClues()) {
            cluesPanel.add(new JLabel(clue));
        }
        cluesPanel.add(new JLabel("Down:"));
        for (String clue : FileParser.getDownClues()) {
            cluesPanel.add(new JLabel(clue));
        }

        cluesPanel.setSize(500, 850);

    }

    private void createParamsPanel() {
        // Create panel for training parameters
        paramsPanel = new JPanel();
        paramsPanel.setLayout(new FlowLayout());
        cutoffField = new JTextField(5); // Set preferred size for cutoff field
        iterationsField = new JTextField(5); // Set preferred size for iterations field
        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            // Get values from text fields
            int cutoff = Integer.parseInt(cutoffField.getText());
            int iterations = Integer.parseInt(iterationsField.getText());
            // Now you can use these values for training parameters
            System.out.println("Cutoff: " + cutoff + ", Iterations: " + iterations);
            // You can call your training method with these values here
        });

        paramsPanel.add(new JLabel("Cutoff: "));
        paramsPanel.add(cutoffField);
        paramsPanel.add(new JLabel("Iterations: "));
        paramsPanel.add(iterationsField);
        paramsPanel.add(submitButton);

    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(CrosswordDisplay::new);

    }
}
