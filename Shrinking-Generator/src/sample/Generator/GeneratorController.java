package sample.Generator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Main;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static sample.Generator.LFSR.xorTable;

public class GeneratorController implements Initializable {

    @FXML
    TextField stringLength;
    @FXML
    TextField LFSRALength;
    @FXML
    TextField LFSRSLength;
    @FXML
    TextField path;
    @FXML
    Button aboutButton;
    @FXML
    Button pathButton;
    @FXML
    Button generateFileButton;
    @FXML
    AnchorPane mainPane;
    @FXML
    TextField pathField;


    public static String outputString = "";
    public static int iterator = 0;
    public static ArrayList<Integer> LFSRAResult = new ArrayList<>();
    public static ArrayList<Integer> LFSRSResult = new ArrayList<>();
    private  Integer textLength = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void onGenerationAction() {
        outputString = "";
        xorTable.implementXorMap();
        if (LFSRALength.getText() == null || LFSRSLength.getText() == null) {
            LFSRALength.setText("21");
            LFSRSLength.setText("22");
        }
        try {
            String LFSRASeed = RandomClass.generateSample(Integer.parseInt(LFSRALength.getText()));
            String LFSRSSeed = RandomClass.generateSample(Integer.parseInt(LFSRSLength.getText()));

            LFSR lfsrA = new LFSR(LFSRASeed, Integer.parseInt(LFSRALength.getText()));
            LFSR lfsrS = new LFSR(LFSRSSeed, Integer.parseInt(LFSRSLength.getText()));

            generationRun(lfsrA, lfsrS);

            while (outputString.length() < Integer.parseInt(stringLength.getText())) {
                generationRun(lfsrA, lfsrS);
            }
            if (outputString.length() != Integer.parseInt(stringLength.getText())) {
                outputString = outputString.substring(0, Integer.parseInt(stringLength.getText()));
            }
            if (outputString.length() == Integer.parseInt(stringLength.getText())) {
                if (pathField.getText() != null) {
                    saveToFile();
                } else {
                    pathField.setText("Results/wynik.txt");
                }
            }
        } catch (NumberFormatException e) {
        }

    }

    public String forEncryptionGenerate (Integer textLength) {
        xorTable.implementXorMap();
        int random = (int)(Math.random() * 8 + 20);
        int random2 = (int)(Math.random() * 8 + 20);
        String LFSRASeed = RandomClass.generateSample(random);
        String LFSRSSeed = RandomClass.generateSample(random2);
        LFSR lfsrA = new LFSR(LFSRASeed, random);
        LFSR lfsrS = new LFSR(LFSRSSeed, random2);
        this.textLength = textLength;
        generationRunToEncryption(lfsrA,lfsrS);
        while (outputString.length() < textLength) {
            generationRunToEncryption(lfsrA,lfsrS);
        }
        if(outputString.length() != textLength) {
            outputString = outputString.substring(0,textLength);
        }
        return outputString;
    }

    public  String generationRun(LFSR lfsrA, LFSR lfsrS) {
        String generationResult = "";
        int bitA;
        int bitS;
        for (int i = 0; i < Integer.parseInt(stringLength.getText()); i++) {
            bitA = lfsrA.step();
            LFSRAResult.add(bitA);
            bitS = lfsrS.step();
            LFSRSResult.add(bitS);
        }
        for (; iterator < LFSRAResult.size(); iterator++) {
            if (LFSRSResult.get(iterator) == 1) {
                int result = LFSRAResult.get(iterator);
                generationResult = generationResult + result;
            }
        }
        outputString = outputString + generationResult;

        return outputString;
    }
    public  String generationRunToEncryption(LFSR lfsrA, LFSR lfsrS) {
        String generationResult = "";
        int bitA;
        int bitS;
        for (int i = 0; i < textLength; i++) {
            bitA = lfsrA.step();
            LFSRAResult.add(bitA);
            bitS = lfsrS.step();
            LFSRSResult.add(bitS);
        }
        for (; iterator < LFSRAResult.size(); iterator++) {
            if (LFSRSResult.get(iterator) == 1) {
                int result = LFSRAResult.get(iterator);
                generationResult = generationResult + result;
            }
        }
        outputString = outputString + generationResult;
        return outputString;
    }

    private void saveToFile() {
        if (outputString != null) {
            String path = pathField.getText();
            try {
                File file = new File(path);
                Desktop desktop = Desktop.getDesktop();
                desktop.open(file);
                try (PrintWriter outstream = new PrintWriter(path)) {
                    outstream.print(outputString);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void choosePath() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Path");
        String userDirectoryString = System.getProperty("user.dir");
        File userDirectory = new File(userDirectoryString);
        fileChooser.setInitialDirectory(userDirectory);

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        Stage stage = (Stage) mainPane.getScene().getWindow();
        File selectedDirectory = fileChooser.showOpenDialog(stage);

        if (selectedDirectory == null) {
            System.out.println("");
        } else {
            pathField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    private void handleAboutClick() {
        String placement = "About/about.fxml";
        loadNextScene(placement);
    }

    protected void loadNextScene(String placement) {
        try {
            Parent decryptionView;
            decryptionView = (Pane) FXMLLoader.load(getClass().getResource(placement));

            Scene decryptionScene = new Scene(decryptionView);

            Stage curStage = (Stage) mainPane.getScene().getWindow();

            curStage.setScene(decryptionScene);

        } catch (IOException e) {
            Logger.getLogger(GeneratorController.class.getName()).log(Level.SEVERE, null, e);
        }

    }
    @FXML
    private void loadMainStage() {
        loadMainScene();
    }

    private void loadMainScene() {
        try {
            Parent mainView = (Pane) FXMLLoader.load(getClass().getResource("../sample.fxml"));

            Scene mainScene = new Scene(mainView);

            Stage curStage = (Stage) mainPane.getScene().getWindow();
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
