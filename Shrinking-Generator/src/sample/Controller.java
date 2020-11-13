package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static sample.LFSR.xorTable;

public class Controller implements Initializable {

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
            if (outputString.length() == Integer.parseInt(stringLength.getText()) ) {
                if( pathField.getText() != null) {
                    saveToFile();
                } else {
                    System.out.println("Brak podanej ściezki!");
                    pathField.setText("D:\\Program Files (x86)\\Projekty\\Shrinking-Generator-master\\result.txt");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Brak danych!");
        }

        System.out.println("Output " + outputString);

    }

    public String generationRun(LFSR lfsrA, LFSR lfsrS) {
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

    public static String generationRunTest(LFSR lfsrA, LFSR lfsrS) {
        String generationResult = "";
        int bitA;
        int bitS;
        for (int i = 0; i < 1000000; i++) {
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
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        Stage stage = (Stage) mainPane.getScene().getWindow();
        File selectedDirectory = fileChooser.showOpenDialog(stage);

        if (selectedDirectory == null) {
            System.out.println("No path selected");
        } else {
            pathField.setText(selectedDirectory.getAbsolutePath());
        }
    }

}
