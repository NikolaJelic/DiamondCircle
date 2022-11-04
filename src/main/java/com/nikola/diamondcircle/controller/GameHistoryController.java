package com.nikola.diamondcircle.controller;

import com.nikola.diamondcircle.DiamondCircle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.*;
import java.util.logging.Level;

public class GameHistoryController {
    private String text;
    @FXML
    private Label textField;
    private final String filePath;

    public GameHistoryController(String filePath){
        this.filePath =  filePath;
    }

    @FXML
    public void initialize(){
        File file = new File(filePath);
       // System.out.println(file.isFile() + "????");
        try(BufferedReader  reader =  new BufferedReader(new FileReader(filePath))){
            var text = reader.lines();
            StringBuilder gameLog = new StringBuilder();
           text.forEach(x -> gameLog.append(x).append('\n'));
            System.out.print(text);
            textField.setText(gameLog.toString());
        }catch (IOException e){
            DiamondCircle.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
        }
    }

}
