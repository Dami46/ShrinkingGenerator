package sample.Info;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Generator.GeneratorController;
import sample.Main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InfoController {

    @FXML
    AnchorPane infoPane;

    @FXML
    private void loadMainStage() {
        loadMainScene();
    }

    public void loadMainScene() {
        try {
            Parent mainView = (Pane) FXMLLoader.load(getClass().getResource("../sample.fxml"));

            Scene mainScene = new Scene(mainView);

            Stage curStage = (Stage) infoPane.getScene().getWindow();
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
