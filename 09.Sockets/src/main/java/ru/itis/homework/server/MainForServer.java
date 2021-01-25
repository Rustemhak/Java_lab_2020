package ru.itis.homework.server;

import com.beust.jcommander.JCommander;
import ru.itis.homework.jcommander.Args;

public class MainForServer {

    public static void main(String[] args) {
        Args argv = new Args();
        JCommander.newBuilder()
                .addObject(argv)
                .build()
                .parse(args);

        MultiClientServer serverSocket = new MultiClientServer();
        serverSocket.start(argv.port);
    }

}
