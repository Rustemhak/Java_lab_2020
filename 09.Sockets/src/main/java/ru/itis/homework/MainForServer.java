package ru.itis.homework;

import com.beust.jcommander.JCommander;
import ru.itis.homework.jcommander.*;
public class MainForServer {
    public static void main(String[] args) {
        Args argv = new Args();
        JCommander.newBuilder()
                .addObject(argv)
                .build()
                .parse(args);

        EchoServerSocket serverSocket = new EchoServerSocket();
        serverSocket.start(argv.port);
    }
}
