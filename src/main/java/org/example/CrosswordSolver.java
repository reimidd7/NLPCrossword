package org.example;

import opennlp.tools.doccat.*;
import opennlp.tools.namefind.TokenNameFinder;
import opennlp.tools.util.*;
import opennlp.tools.util.ObjectStreamUtils;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CrosswordSolver {
    static String dataFile;




    public static void trainModel(String trainingDataPath, String modelOutputPath, int cutoff, int iterations) throws IOException {
        dataFile = trainingDataPath;
        // Define training parameters
        DoccatFactory factory = new DoccatFactory();
        TrainingParameters params = new TrainingParameters();
        params.put(TrainingParameters.CUTOFF_PARAM, cutoff);
        params.put(TrainingParameters.ITERATIONS_PARAM, iterations);


        // Initialize a list to hold ObjectStreams for each file
        List<ObjectStream<DocumentSample>> sampleStreams = new ArrayList<>();

            InputStream dataIn = new FileInputStream(trainingDataPath);
            ObjectStream<String> lineStream = new PlainTextByLineStream(new InputStreamFactory() {
                public InputStream createInputStream() throws IOException {
                    return dataIn;
                }
            }, StandardCharsets.UTF_8);
            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
            sampleStreams.add(sampleStream);


        // Concatenate the ObjectStreams
        ObjectStream<DocumentSample> combinedSampleStream = ObjectStreamUtils.concatenateObjectStream(sampleStreams);

        // Train the model
        DoccatModel model = DocumentCategorizerME.train("en", combinedSampleStream, params, factory);

        // Save the model
        try (OutputStream modelOut = new FileOutputStream(modelOutputPath)) {
            model.serialize(modelOut);
        }

        // Close streams
        combinedSampleStream.close();
    }

    public static List<String> solveCrossword(String modelPath, List<String> clues) throws IOException {
        // Load the model
        InputStream modelIn = new FileInputStream(modelPath);
        DoccatModel model = new DoccatModel(modelIn);

        // Initialize the document categorizer
        DocumentCategorizerME categorizer = new DocumentCategorizerME(model);

        // Solve the crossword
        List<String> solutions = new ArrayList<>();
        for (String clue : clues) {
            double[] outcomes = categorizer.categorize(clue.split(" "));
            String category = categorizer.getBestCategory(outcomes);
            solutions.add(category);
            System.out.println(category);
        }

        // Close the model input stream
        modelIn.close();

        return solutions;
    }
}


