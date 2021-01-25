package ru.itis.homework.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

public class ServerConnection extends Thread {

    private final Socket clientSocket;
    private final PrintWriter writer;
    private final BufferedReader reader;
    private final Consumer<String> messageConsumer;

    public ServerConnection(Socket clientSocket, Consumer<String> messageConsumer) {
        this.clientSocket = clientSocket;
        this.messageConsumer = messageConsumer;

        try {
            this.writer = new PrintWriter(clientSocket.getOutputStream(), true);
            this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                messageConsumer.accept(message);
            }
            reader.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void send(String message) {
        this.writer.println(message);
    }

}
