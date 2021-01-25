package ru.itis.homework.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MultiClientServer {

    private ServerSocket serverSocket;
    private List<ServerConnection> connections;

    public MultiClientServer() {
        this.connections = new ArrayList<>();
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket accept = serverSocket.accept();
                ServerConnection client = new ServerConnection(accept, this::broadcastMessage);
                connections.add(client);
                client.start();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void broadcastMessage(String message) {
        for (ServerConnection connection : connections) {
            connection.send(message);
        }
    }

}
