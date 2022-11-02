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

public class Controllermenu implements Initializable {

    Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    Button EXIT;
    @FXML
    Button MP;
    @FXML
    Button About;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
public void leave()
{
    EXIT.setOnAction(actionEvent ->
            {
                Platform.exit();
            }
            );
}
public void  showinfo () throws IOException
{
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(getClass().getResource("Info.fxml"));
    Scene scene= new Scene(fxmlLoader.load());
    stage = new Stage();

    stage.setTitle("Info");
    stage.setScene(scene);
    stage.show();
}
public void multidecide() throws IOException
{
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(getClass().getResource("Choose_player.fxml"));
    Scene scene= new Scene(fxmlLoader.load());
    stage = new Stage();

    stage.setTitle("Wyberz swoją rolę!");
    stage.setScene(scene);
    stage.show();
}

}
