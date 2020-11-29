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
        Tests tests = new Tests();
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
            System.out.println("");
            e.printStackTrace();
        }
        return data.substring(0,20000);
    }

    @FXML
    private void runSingleBitsTest() {
        boolean result = false;
        try {
            result = Tests.singleBitsTest();
        } catch (NullPointerException e) {
            System.out.println("");
        }
        if (result) {
            singleBitsTest.setText("Test passed! " + "The amount of 1 is " + Tests.onesCounter);
        } else {
            singleBitsTest.setText("Test failed! " + "The amount of 1 is " + Tests.onesCounter);
        }
    }

    @FXML
    private void runSeriesTest() {
        boolean result = false;
        Integer properZeros = 0;
        Integer properOnes = 0;
        try {
            properZeros = Tests.seriesTest("0+");
            properOnes = Tests.seriesTest("1+");
            result = Tests.seriesTestBool(properZeros, properOnes);
        } catch (NullPointerException e) {
            System.out.println("");
        }

        if (result) {
            seriesTest.setText("Test passed! " + "Amount of 0 in range = " + properZeros + " and amount of 1 in range = " + properOnes);
        } else {
            seriesTest.setText("Test failed! " + "Amount of 0 in range =  " + properZeros + " and amount of 1 in range = " + properOnes);
        }

    }

    @FXML
    private void runLongSeriesTest() {
        boolean result = false;
        try {
            result = Tests.longSeriesTest();
        } catch (NullPointerException e) {
            System.out.println("");
        }
        if (result) {
            longSeriesTest.setText("Test passed! " + "Longest string of 1 or 0 is " + Tests.repeatCounter);
        } else {
            longSeriesTest.setText("Test failed! " + "Longest string of 1 or 0 is " + Tests.repeatCounter);
        }
    }


    @FXML
    private void runPokerTest() {
        double pokerResult = 0.0;
        boolean result = false;
        try {
            pokerResult = Tests.pokerTest();
            result = Tests.pokerTestBool(pokerResult);
        } catch (NullPointerException e) {
            System.out.println("");
        }
        if (result) {
            pokerTest.setText("Test passed! " + "Poker test result is " + pokerResult);
        } else {
            pokerTest.setText("Test failed! " + "Poker test result is " + pokerResult);
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
