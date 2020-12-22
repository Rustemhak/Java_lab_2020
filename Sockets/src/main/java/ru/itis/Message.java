package ru.itis;

import java.io.*;
import java.net.*;
import java.util.TreeMap;

class Message {
    Socket client;
    EchoServerSocket server;
    BufferedReader fromClient;
    PrintWriter toClient;

    public Message(Socket client, EchoServerSocket server) {
        this.client = client;
        this.server = server;
        try {
            this.fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.toClient = new PrintWriter(client.getOutputStream(), true);
            new Thread(message).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Runnable message = () -> {
        try {
            String message = fromClient.readLine();
            while (true) {
                server.messageForChat(message);
                System.out.println(message);
                message = fromClient.readLine();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    };

    public void sendMessage(String message) {
        toClient.println(message);
    }

}
