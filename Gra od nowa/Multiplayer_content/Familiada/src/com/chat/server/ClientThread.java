package com.chat.server;

import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collection;
import java.util.Random;
import java.util.logging.Level;


import javafx.application.Platform;
/*
Liczba prób/Number of attempts (default value is 3)
 */
/* Thread Class for each incoming client */
public class ClientThread implements Runnable {
    private Random random = new Random();
    private int tempres=0;
    Collection<Integer> winner;
    /* The socket of the client */
    private Socket clientSocket;
    /* Server class from which thread was called */
    private Server baseServer;
    private BufferedReader incomingMessageReader;
    private PrintWriter outgoingMessageWriter;
    /* The name of the client */
    private String clientName;
    boolean isFin=false;

    Log log = new Log("test.log");

    public ClientThread(Socket clientSocket, Server baseServer) throws IOException {
        this.setClientSocket(clientSocket);
        this.baseServer = baseServer;
        try {
            /*
             * Reader to get all incoming messages that the client passes to the
             * server
             */
            incomingMessageReader = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));
            /* Writer to write outgoing messages from the server to the client */
            outgoingMessageWriter = new PrintWriter(
                    clientSocket.getOutputStream(), true);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void run() {

        try {

            //baseServer.list.add(String.valueOf(clientSocket.getRemoteSocketAddress()));
            System.out.println(baseServer.list);
            this.clientName = getClientNameFromNetwork();
            Platform.runLater(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    baseServer.clientNames.add(clientName + " - "
                            + clientSocket.getRemoteSocketAddress());
                }

            });

            String inputToServer;
            log.logger.setLevel(Level.INFO);
            writeToServer(String.valueOf(baseServer.Jokeses.get(baseServer.result)));
            log.logger.info("Wybrany żart: "+ String.valueOf(baseServer.Jokeses.get(baseServer.result)));

            writeToServer(String.valueOf(baseServer.question));
            log.logger.info(String.valueOf(baseServer.question));
            int attemptss=0;

            attemptss += baseServer.attempts;
            while (true) {
                int score=random.nextInt(baseServer.highpossible-baseServer.low)+baseServer.low;

                inputToServer = incomingMessageReader.readLine();
                baseServer.writeToAllSockets(inputToServer);
                log.logger.info(inputToServer);

                if (baseServer.list.contains(inputToServer)&&isFin==false)
                {
                    baseServer.list.remove(inputToServer);
                    System.out.println(baseServer.list);
                    writeToServer("Brawo "+"`"+clientName.toString()+"`"+" zdobywasz "+score+" pktów");
                    log.logger.info("Brawo "+"`"+clientName.toString()+"`"+" zdobywasz "+score+" pktów");
                    tempres+=score;
                    baseServer.user1.put(clientName,tempres);
                    baseServer.opponent=clientName.toString();
                    writeToServer(clientName.toString()+" Masz prób: "+ attemptss);
                    log.logger.info(clientName.toString()+" Masz prób: "+ attemptss);
                }
                else {
                    System.out.println("Nie można na to odpowiedzieć, tak samo");
                    writeToServer("Nie no to było już albo to jest zła odpowiedź");
                    log.logger.info("Nie no to było już albo to jest zła odpowiedź");
                    attemptss-=1;
                    writeToServer(clientName.toString()+" Masz prób: "+ attemptss);
                    log.logger.info(clientName.toString()+" Masz prób: "+ attemptss);
                }
                if(baseServer.list.isEmpty())
                {
                    System.out.println(baseServer.user1);
                    writeToServer(String.valueOf(baseServer.user1));
                    log.logger.info(String.valueOf(baseServer.user1));
                }
                if(attemptss<0)
                {
                    writeToServer("Koniec gry"+clientName);
                    log.logger.info("Koniec gry"+clientName);
                    isFin=true;

                    writeToServer("Brawo "+"`"+clientName.toString()+"`"+" zdobywasz "+tempres+" pktów");
                    log.logger.info("Brawo "+"`"+clientName.toString()+"`"+" zdobywasz "+tempres+" pktów");
                    log.logger.warning("W rzeczywistości gracz, jest teraz tylko obserwującym. ");
                    baseServer.user1.put(clientName,tempres);
                    baseServer.isfinal=true;
                    incomingMessageReader.reset();
                    outgoingMessageWriter.flush();
                    outgoingMessageWriter.wait(300000);



                }


            }
        } catch (SocketException e) {
            baseServer.clientDisconnected(this);

        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            System.out.println("Koniec przesyłu danych, gracz "+clientName + "nie udziela odpowiedzi, \n jeżeli nie ma innych graczy w zasadzie koniec gry");
            log.logger.warning("Koniec przesyłu danych, gracz "+clientName + "nie udziela odpowiedzi, \n jeżeli nie ma innych graczy w zasadzie koniec gry");
            //e.printStackTrace();

        }
        catch (NullPointerException nullPointerException)
        {
            System.out.println("Blokada przesyłu danych!");
            log.logger.warning("Blokada przesyłu danych!");
        }
    }

    public void writeToServer(String input) {
        outgoingMessageWriter.println(baseServer.opponent+": "+input);
        ;
    }

    public String getClientNameFromNetwork() throws IOException {
        /*
         * Get the name of the client, which is the first data transaction the
         * server-client make
         */
        return incomingMessageReader.readLine();
    }

    public String getClientName() {
        return this.clientName;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
}
