package sample.Tests;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Generator.GeneratorController;
import sample.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TestsController {

    @FXML
    AnchorPane testPane;
    @FXML
    TextField pathField;
    @FXML
    TextField singleBitsTest;
    @FXML
    TextField seriesTest;
    @FXML
    TextField longSeriesTest;
    @FXML
    TextField pokerTest;

    private File selectedDirectory;
    public static String stringToTest;

    @FXML
    public void choosePath() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        String userDirectoryString = System.getProperty("user.dir");
        File userDirectory = new File(userDirectoryString);
        fileChooser.setInitialDirectory(userDirectory);

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        Stage stage = (Stage) testPane.getScene().getWindow();
        selectedDirectory = fileChooser.showOpenDialog(stage);

        if (selectedDirectory == null) {
            System.out.println("");
        } else {
            pathField.setText(selectedDirectory.getAbsolutePath());
        }
        try {
            stringToTest = readFile();
        } catch (NullPointerException e) {

        }

    }

    public String readFile() {
        String data = "";
        try {
            File myObj = new File(selectedDirectory.getAbsolutePath());
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data;
    }

    @FXML
    private void runSingleBitsTest() {
        boolean result = Tests.singleBitsTest();
        if(result) {
            singleBitsTest.setText("Test passed! " + "The amount of 1 is " + Tests.onesCounter);
        } else {
            singleBitsTest.setText("Test failed! " + "The amount of 1 is " + Tests.onesCounter);
        }
    }

    @FXML
    private void runSeriesTest() {
        boolean result = Tests.seriesTest();
        if(result) {
            seriesTest.setText("Test passed! " + "The amount of 1 is " + Tests.onesCounter);
        } else {
            seriesTest.setText("Test failed! " + "The amount of 1 is " + Tests.onesCounter);
        }

    }

    @FXML
    private void runLongSeriesTest() {
        boolean result = Tests.longSeriesTest();
        if(result) {
            longSeriesTest.setText("Test passed! " + "Longest string of 1 or 0 is " + Tests.repeatCounter);
        } else {
            longSeriesTest.setText("Test failed! " + "Longest string of 1 or 0 is " + Tests.repeatCounter);
        }
    }


    @FXML
    private void runPokerTest() {
        boolean result = Tests.pokerTest();
        if(result) {
            pokerTest.setText("Test passed! " + "Longest string of 1 or 0 is " + Tests.repeatCounter);
        } else {
            pokerTest.setText("Test failed! " + "Longest string of 1 or 0 is " + Tests.repeatCounter);
        }
    }

    @FXML
    private void runAllTest() {
        runSingleBitsTest();
        runSeriesTest();
        runLongSeriesTest();
        runPokerTest();
    }

    @FXML
    private void loadMainStage() {
        loadMainScene();
    }

    private void loadMainScene() {
        try {
            Parent mainView = (Pane) FXMLLoader.load(getClass().getResource("../sample.fxml"));

            Scene mainScene = new Scene(mainView);

            Stage curStage = (Stage) testPane.getScene().getWindow();
            Main main = new Main();
            main.start(curStage);
            curStage.setScene(mainScene);

        } catch (IOException e) {
            Logger.getLogger(GeneratorController.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
