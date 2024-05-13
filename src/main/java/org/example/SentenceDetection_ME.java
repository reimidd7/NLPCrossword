package org.example;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class SentenceDetection_ME {
    public static void main(String args[]) throws IOException {
        String sentence = "Hi. How are you? Welcome to Tutorialspoint. "
                + "We provide free tutorials on various technologies";

        //Loading sentence detector model
        //InputStream inputStream = new FileInputStream("C:/OpenNLP_models/en-sent.bin");
        //SentenceModel model = new SentenceModel(inputStream);
        InputStream is = SentenceDetection_ME.class.getResourceAsStream("/en-sent.bin");
        SentenceModel model = new SentenceModel(is);

        //Instantiating the SentenceDetectorME class
        SentenceDetectorME detector = new SentenceDetectorME(model);

        //Detecting the sentence
        String sentences[] = detector.sentDetect(sentence);

        //Printing the sentences
        for (String sent : sentences)
            System.out.println(sent);
    }


    }
