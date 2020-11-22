package sample.Decryption;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Generator.GeneratorController;
import sample.Encryption.EncryptionController;
import sample.Main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import static sample.Encryption.EncryptionController.*;

public class DecryptionController {

    @FXML
    private Pane decryptionPane;
    @FXML
    public  TextArea encryptedTextArea;
    @FXML
    private TextArea decryptedTextArea;
    @FXML
    private TextField keyTextField;


    @FXML
    private void loadMainStage() {
        loadMainScene();
    }

    public void loadFromEncryption(){
        String textToDecryption = EncryptionController.getEncryptedText();
        encryptedTextArea.setText(textToDecryption);
    }

    @FXML
    private void saveToFile() {
        if(decryptedTextArea != null) {
            String encryptedText = decryptedTextArea.getText();
            try (PrintWriter outstream = new PrintWriter("decryptionResult.txt")) {
                outstream.println(encryptedText + "\n");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void loadFromFile() throws IOException {
        String path ="D:\\Program Files (x86)\\Projekty\\Shrinking-Generator-master\\Shrinking-Generator\\src\\sample\\Encryption\\result.txt";
        String content = String.valueOf(Files.readAllLines(Paths.get(path)));
        encryptedTextArea.setText(content);
    }

    @FXML
    private void decryption() {
        if(!keyTextField.getText().equals("")) {
            generatedBinary = keyTextField.getText();
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < xorResult.length(); i++) {
            sb.append(xorResult.charAt(i)^generatedBinary.charAt(i));
        }
        String result = binaryToText(sb.toString());
        decryptedTextArea.setText(result);
    }

    public void loadMainScene() {
        try {
            Parent mainView = (Pane) FXMLLoader.load(getClass().getResource("../sample.fxml"));

            Scene mainScene = new Scene(mainView);

            Stage curStage = (Stage) decryptionPane.getScene().getWindow();
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
}
