package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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

}
