package com.nikola.diamondcircle.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.util.List;
import java.util.Objects;
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
        System.out.println(selectedFileName);
        // Make GameHistoryController and pass the file

    }
}
