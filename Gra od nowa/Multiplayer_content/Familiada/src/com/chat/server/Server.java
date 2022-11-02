package com.chat.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.ManagerFactoryParameters;

public class Server implements Runnable {
boolean isfinal=false;

int winner=0;
    public String lol = new String();
   ArrayList winlist=new ArrayList<String>();
    int highpossible=50;
    String joke;
    Set<String> list=new TreeSet<>();
    HashMap<String, Integer>user1=new HashMap<String, Integer>();
    ArrayList<String> Jokeses=new ArrayList<String>();
     Random r = new Random();
    int low = 1;
    int high=10;
    int attempts;
    public void joke() {
        String first = "Suchary/Jokes/Jokes.json";
        try {

            String contents = new String(Files.readAllBytes(Paths.get(first)));
            JSONObject jsonObject= new JSONObject(contents);
            JSONArray jsonArray = (JSONArray) jsonObject.get("ListOfJokes");

            if (!jsonArray.isEmpty()) {
                for (int i = 0; i < jsonArray.length(); i++) {

                    Jokeses.add(String.valueOf(jsonArray.get(i)));


                    //System.out.println(jsonArray.get(i));
                }
            }
            setLol(String.valueOf(Jokeses));
            System.out.println(getLol());
            System.out.println("XDXDXDXDXDXD");





           // System.out.println(Jokeses);
            high=Jokeses.size();
            System.out.println(high);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    int result = r.nextInt(high-low) + low;
   int a=0;
    String question;
   String opponent;
    private int portNumber;
	private ServerSocket socket;
	private ArrayList<Socket> clients;
	private ArrayList<ClientThread> clientThreads;
	public ObservableList<String> serverLog;
	public ObservableList<String> clientNames;
	public Server(int portNumber) throws IOException {
		this.portNumber = portNumber;
		serverLog = FXCollections.observableArrayList();
		clientNames = FXCollections.observableArrayList();
		clients = new ArrayList<Socket>();
		clientThreads = new ArrayList<ClientThread>();
		socket = new ServerSocket(portNumber);
	Tab tab =new Tab();
	}
	public void question()
    {
        String first="Pytanie/question.json";
        try {

            String contents= new String(Files.readAllBytes(Paths.get(first)));
            JSONObject jsonObject= new JSONObject(contents);

           question=jsonObject.getString("Question");
            JSONObject max =new JSONObject(contents);
            attempts = jsonObject.getInt("tries");
            System.out.println("Liczba prób dla gracza:"+ attempts);



        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public void readans()
    {
       String first="Odpowiedzi/answers.json";

        try {

            String contents = new String(Files.readAllBytes(Paths.get(first)));
            JSONObject jsonObject= new JSONObject(contents);
            JSONArray jsonArray = (JSONArray) jsonObject.get("Answers");


            if (!jsonArray.isEmpty()) {
                for (int i = 0; i < jsonArray.length(); i++) {

                    list.add(String.valueOf(jsonArray.get(i)));

                }
            }

            System.out.println(list);




        } catch (IOException e) {
            e.printStackTrace();
        }


    }

	public void run() {
        joke();
	    Connection connection = null;
        question();
        System.out.println("Serwer wystartował");
        readans();


		try {
			/* Infinite loop to accept any incoming connection requests */
			while (true) {
				/* Add to log that the server's listening */

				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						serverLog.add("Oczekiwanie na gracza");

					}
				});

				final Socket clientSocket = socket.accept();

				/* Add the incoming socket connection to the list of clients */
				clients.add(clientSocket);
				/* Add to log that a client connected */
				Platform.runLater(new Runnable() {

					@Override
					public void run() {

						// TODO Auto-generated method stub
						serverLog.add("Gracz "
								+ clientSocket.getRemoteSocketAddress()
								+ " dołączył");
					a++;
					}
				});

                ClientThread clientThreadHolderClass = new ClientThread(
                        clientSocket, this);
				Thread clientThread = new Thread(clientThreadHolderClass);
				clientThreads.add(clientThreadHolderClass);
				clientThread.setDaemon(true);
				clientThread.start();
				clientThread.setName("Gracz "+a);
				ServerApplication.threads.add(clientThread);

				//System.out.println(clientSocket.getRemoteSocketAddress());


			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void clientDisconnected(ClientThread client) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
                if(list.isEmpty() || isfinal==true ) {
                    System.out.println("Tablica pktów graczy: ");
                    System.out.println(user1);
                }
                    // TODO Auto-generated method stub
				serverLog.add("Gracz "
						+ client.getClientSocket().getRemoteSocketAddress()
						+ " się rozłączył");
				clients.remove(clientThreads.indexOf(client));
				clientNames.remove(clientThreads.indexOf(client));
				clientThreads.remove(clientThreads.indexOf(client));

			}
		});
		
		
	}

	public void writeToAllSockets(String input) {

	    for (ClientThread clientThread : clientThreads)
	    {
			clientThread.writeToServer(input);
			    }
	}

    public String getLol() {
        return lol;
    }

    public void setLol(String lol) {
        this.lol = lol;
    }
}
