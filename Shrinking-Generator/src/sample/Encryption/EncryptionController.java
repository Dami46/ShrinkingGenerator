package sample.Encryption;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Generator.GeneratorController;
import sample.Main;


import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EncryptionController {
    @FXML
    private Pane encryptionPane;
    @FXML
    public  TextArea encryptedTextArea;
    @FXML
    public TextArea yourTextArea;
    @FXML
    private TextField keyArea;

    private static String encryptedText;


    @FXML
    private void encryption() {
        String key = keyArea.getText();

    }
    @FXML
    private void clearTextArea() {
        yourTextArea.clear();
    }
    @FXML
    private void saveToFile() {
        if(encryptedTextArea != null) {
            String encryptedText = encryptedTextArea.getText();
            String path = "D:\\Program Files (x86)\\Projekty\\Shrinking-Generator-master\\Shrinking-Generator\\src\\sample\\Encryption";
            try {
                File file = new File (path);
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
        String path ="D:\\Program Files (x86)\\Projekty\\Shrinking-Generator-master\\Shrinking-Generator\\src\\sample\\Encryption\\text.txt";
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

    public  TextArea getEncryptedTextArea() {
        return encryptedTextArea;
    }

    public static String getEncryptedText() {
        return encryptedText;
    }

    public void setEncryptedText(String encryptedText) {
        this.encryptedText = encryptedText;
    }

}
