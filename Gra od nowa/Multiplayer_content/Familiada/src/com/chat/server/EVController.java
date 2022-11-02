package com.chat.server;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;




public class EVController implements Initializable {

String line;

    ArrayList<String> Jokeses = new ArrayList<String>();
    String question;
    String answerses;
    Set<String> list = new TreeSet<>();
    @FXML
    TextArea evlog;

    public EVController() {
        System.out.println("aLOL");

    }

    /*
    Tutaj jest metoda do wyświetlania tekstu
     */

    public void setEvlog(String str) {

        evlog.appendText(str + "\n");


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int low = 1;
        int high = 10;
        int attempts = 0;
        int ss = 0;
        String first = "Suchary/Jokes/Jokes.json";
        try {

            String contents = new String(Files.readAllBytes(Paths.get(first)));
            JSONObject jsonObject = new JSONObject(contents);
            JSONArray jsonArray = (JSONArray) jsonObject.get("ListOfJokes");

            if (!jsonArray.isEmpty()) {
                for (int i = 0; i < jsonArray.length(); i++) {

                    Jokeses.add(String.valueOf(jsonArray.get(i)));


                    //System.out.println(jsonArray.get(i));
                }
            }

            System.out.println(Jokeses);
            System.out.println("XDXDXDXDXDXD");


            // System.out.println(Jokeses);
            high = Jokeses.size();
            System.out.println(high);


        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            first = "Pytanie/question.json";
            String contents = new String(Files.readAllBytes(Paths.get(first)));
            JSONObject jsonObject = new JSONObject(contents);

            question = jsonObject.getString("Question");
            JSONObject max = new JSONObject(contents);
            attempts = jsonObject.getInt("tries");
            ss = attempts;
            System.out.println("Liczba prób dla gracza:" + attempts);


        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            first = "Odpowiedzi/answers.json";
            String contents = new String(Files.readAllBytes(Paths.get(first)));
            JSONObject jsonObject = new JSONObject(contents);
            JSONArray jsonArray = (JSONArray) jsonObject.get("Answers");


            if (!jsonArray.isEmpty()) {
                for (int i = 0; i < jsonArray.length(); i++) {

                    list.add(String.valueOf(jsonArray.get(i)));

                }
            }

            System.out.println(list);
            answerses = String.valueOf(list);


        } catch (IOException e) {
            e.printStackTrace();
        }

        evlog.setVisible(true);



        setEvlog(String.valueOf(Jokeses));
        setEvlog("Pytanie: " + question);
        setEvlog("Odpowiedzi:" + answerses);
        setEvlog("liczba prób: " + String.valueOf(ss));
        setEvlog("Aby monitorować przebieg rozgrywki użyj programu monitorującego log: <test.log>  ");




    }

    }


