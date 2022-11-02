package com.chat.server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ServerApplication extends Application {




	public static ArrayList<Thread> threads;
	public static void main(String[] args) throws IOException {



		launch();

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		threads = new ArrayList<Thread>();
		primaryStage.setTitle("Familiada Multiplayer");
		primaryStage.setScene(makePortUI(primaryStage));
		primaryStage.show();


	}
	public void events() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("event.fxml"));
		Scene scene= new Scene(fxmlLoader.load());
		Stage stage = new Stage();
		stage.setTitle("Gamemenu");
		stage.setScene(scene);

		stage.show();
	}

	public Scene makePortUI(Stage primaryStage) throws IOException {
		/* Make the root and set properties */



		GridPane rootPane = new GridPane();
		rootPane.setPadding(new Insets(20));
		rootPane.setVgap(10);
		rootPane.setHgap(10);
		rootPane.setAlignment(Pos.CENTER);

		/* Text label and field for port Number */
		Text portText = new Text("Numer portu");
		Label errorLabel = new Label();
		errorLabel.setTextFill(Color.RED);
		TextField portTextField = new TextField();
		portText.setFont(Font.font("Tahoma"));
		/*
		 * "Done" button and its click handler When clicked, another method is
		 * called
		 */
		Button portApprovalButton = new Button("OK");
		portApprovalButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				/* Make the server and it's thread, and run it */
				try {
					Server server = new Server(Integer.parseInt(portTextField
							.getText()));

					Thread serverThread = (new Thread(server));
					serverThread.setName("Server Thread");
					serverThread.setDaemon(true);
					serverThread.start();
					threads.add(serverThread);
					/* Change the view of the primary stage */
					primaryStage.hide();
					primaryStage.setScene(makeServerUI(server));
					primaryStage.show();
				}catch(IllegalArgumentException e){
					errorLabel.setText("Nieprawidłowy numer portu");
				}
				catch (IOException e) {
					// TODO Auto-generated catch block

				}

			}
		});

		/* Add the views to the pane */
		rootPane.add(portText, 0, 0);
		rootPane.add(portTextField, 0, 1);
		rootPane.add(portApprovalButton, 0, 2);
		rootPane.add(errorLabel, 0, 3);
		/*
		 * Make the Scene and return it Scene has constructor (Parent, Width,
		 * Height)
		 */
		return new Scene(rootPane, 400, 300);
	}
	public Scene makeServerUI(Server server){
		/* Make the root pane and set properties */
		Button show_events = new Button("Pokaż konfigurację serwera");
		GridPane rootPane = new GridPane();
		rootPane.setAlignment(Pos.CENTER);
		rootPane.setPadding(new Insets(20));
		rootPane.setHgap(10);
		rootPane.setVgap(10);
		rootPane.add(show_events,0,4);

		/* Make the server log ListView */
		Label logLabel = new Label("Server Log");
		ListView<String> logView = new ListView<String>();
		ObservableList<String> logList = server.serverLog;
		logView.setItems(logList);

		show_events.setOnAction(actionEvent -> {

			try {

				events();




				// Hide this current window (if this is what you want)

			}
			catch (IOException e) {
				e.printStackTrace();
			}
		});
		/* Make the client list ListView */
		Label clientLabel = new Label("Obecni gracze");
		ListView<String> clientView = new ListView<String>();
		ObservableList<String> clientList = server.clientNames;
		clientView.setItems(clientList);

		/* Add the view to the pane */
		rootPane.add(logLabel, 0, 0);
		rootPane.add(logView, 0, 1);
		rootPane.add(clientLabel, 0, 2);
		rootPane.add(clientView, 0, 3);

		/* Make scene and return it,
		 * Scene has constructor (Parent, Width, Height)
		 *  */
		return new Scene(rootPane, 400, 600);
	}

}
