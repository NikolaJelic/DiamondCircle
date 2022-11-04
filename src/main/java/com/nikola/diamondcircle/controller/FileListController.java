package com.nikola.diamondcircle.controller;

import com.nikola.diamondcircle.DiamondCircle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileListController {
    private final String destinationFolder = "data" + File.separator + "games" + File.separator;
    @FXML
    public ListView<Label> fileList;
    private String selectedFileName;
    private List<String> fileNames;

    public FileListController() {
        File destFolder = new File(destinationFolder);
        if (destFolder.isDirectory()) {
            fileNames = Stream.of(Objects.requireNonNull(destFolder.listFiles())).filter(File::isFile).map(File::getName).collect(Collectors.toList());
        }
    }

    @FXML
    public void initialize() {
        for (String name : fileNames) {
            fileList.getItems().add(new Label(name));
        }
    }

    @FXML
    public void openFile(MouseEvent mouseEvent) {
        selectedFileName = fileList.getSelectionModel().getSelectedItem().getText();
        try {
            //Init controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nikola/diamondcircle/views/gameHistory.fxml"));
            System.out.println(destinationFolder + selectedFileName);
            GameHistoryController gameHistoryController = new GameHistoryController(destinationFolder + selectedFileName);
            loader.setController(gameHistoryController);
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Diamond Circle");
            stage.setScene(new Scene(root, 1200, 600));

            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            DiamondCircle.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
        }

        // Make GameHistoryController and pass the file

    }
}
