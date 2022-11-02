package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Multiplayer implements Initializable {
    @FXML
            Button Exit;
    @FXML
            Button player2;

    @FXML
            Button serverprovider;


    Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setExit () throws IOException
    {
        stage = (Stage) Exit.getScene().getWindow();
        stage.close();
    }

    public void run_player() throws  IOException
    {

player2.setOnAction(actionEvent ->
        {

            Runtime rt = Runtime.getRuntime();
            try {
                rt.exec("Multiplayer_content/Familiada/src/com/chat/client/Client.exe");
            } catch (IOException e) {
                System.out.println("Coś nie chce działać");
            }

        }



        );


    }


    public void run_server() throws IOException
    {
serverprovider.setOnAction(actionEvent ->

        {
            Runtime rt = Runtime.getRuntime();
            try {


                rt.exec(new String[]{"cmd.exe","/c","start server.exe.lnk"});




            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        );
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
