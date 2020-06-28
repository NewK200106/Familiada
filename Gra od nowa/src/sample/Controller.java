package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
        Button Exit;
        @FXML
        Button Play;



        public void cancel ()
        {

            Exit.setOnAction((event) -> {
                // Button was clicked, do something...
                javafx.application.Platform.exit();
                Platform.exit();
                System.exit(0);
            });

        }

        int test = 0;
        public void closeprev()throws IOException
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("sample.fxml"));
            Scene scene= new Scene(fxmlLoader.load());
            Stage stage = (Stage) Play.getScene().getWindow();
            // do what you have to do
            stage.close();
        }

        public void graj ( ) throws IOException
        {
                // Button was clicked, do something..

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("gamemenu.fxml"));
                Scene scene= new Scene(fxmlLoader.load());
                closeprev();

                stage = new Stage();

                stage.setTitle("Gamemenu");
                stage.setScene(scene);
                stage.show();

                //This line gets the Stage information
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}


