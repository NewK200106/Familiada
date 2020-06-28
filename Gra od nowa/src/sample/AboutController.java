package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {
    @FXML
    Button Back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void back() throws IOException {
        stage = (Stage) Back.getScene().getWindow();
        stage.close();

    }
}