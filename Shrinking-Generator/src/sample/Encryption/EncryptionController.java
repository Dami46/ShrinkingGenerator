package sample.Encryption;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Generator.GeneratorController;
import sample.Main;


import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EncryptionController {

    @FXML
    private Pane encryptionPane;
    @FXML
    public TextArea encryptedTextArea;
    @FXML
    public TextArea yourTextArea;

    private static String encryptedText;
    public static String generatedBinary = "";
    public static String xorResult;

    @FXML
    private void encryption() {
        if (!yourTextArea.getText().equals("") ) {
            StringBuilder binaryText = textToBin();
            String binaryTextString = binaryText.toString();
            GeneratorController generatorController = new GeneratorController();
            if (GeneratorController.outputString.equals("") || binaryTextString.length() > GeneratorController.outputString.length()) {
                generatedBinary = generatorController.forEncryptionGenerate(binaryTextString.length());
            } else {
                generatedBinary = GeneratorController.outputString;
            }
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < binaryTextString.length(); i++) {
                sb.append(binaryTextString.charAt(i) ^ generatedBinary.charAt(i));
            }
            xorResult = sb.toString();
            String result = binaryToText(xorResult);

            encryptedText = result;
            encryptedTextArea.setText(result);
        }

    }

    @FXML
    private void clearTextArea() {
        yourTextArea.clear();
    }

    @FXML
    private void saveToFile() {
        if (encryptedTextArea != null) {
            String encryptedText = encryptedTextArea.getText();
            String path = "D:\\Program Files (x86)\\Projekty\\Shrinking-Generator-master\\Shrinking-Generator\\src\\sample\\Encryption";
            try {
                File file = new File(path);
                Desktop desktop = Desktop.getDesktop();
                desktop.open(file);
                try (PrintWriter outstream = new PrintWriter("result.txt")) {
                    outstream.println(encryptedText + "\n");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void loadFromFile() throws IOException {
        String path = "D:\\Program Files (x86)\\Projekty\\Shrinking-Generator-master\\Shrinking-Generator\\src\\sample\\Encryption\\text.txt";
        String content = String.valueOf(Files.readAllLines(Paths.get(path)));
        yourTextArea.setText(content);
    }

    @FXML
    private void loadMainStage() {
        loadMainScene();
    }

    private void loadMainScene() {
        try {
            Parent mainView = (Pane) FXMLLoader.load(getClass().getResource("../sample.fxml"));

            Scene mainScene = new Scene(mainView);

            Stage curStage = (Stage) encryptionPane.getScene().getWindow();
            Main main = new Main();
            main.start(curStage);
            curStage.setScene(mainScene);

        } catch (IOException e) {
            Logger.getLogger(GeneratorController.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TextArea getEncryptedTextArea() {
        return encryptedTextArea;
    }

    public static String getEncryptedText() {
        return encryptedText;
    }

    public void setEncryptedText(String encryptedText) {
        this.encryptedText = encryptedText;
    }

    public StringBuilder textToBin() {
        Charset UTF_8 = StandardCharsets.UTF_8;
        String text = yourTextArea.getText();
        byte[] bytes = text.getBytes(UTF_8);
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary;
    }

    public static String binaryToText(String binary) {
        return Arrays.stream(binary.split("(?<=\\G.{8})"))/* regex to split the bits array by 8*/
                .parallel()
                .map(eightBits -> (char) Integer.parseInt(eightBits, 2))
                .collect(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append
                ).toString();
    }
}
