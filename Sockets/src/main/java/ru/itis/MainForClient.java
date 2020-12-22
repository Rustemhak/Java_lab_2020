package ru.itis;

import com.beust.jcommander.JCommander;
import ru.itis.jcommander.Args;
import java.util.Scanner;


public class MainForClient {
    public static void main(String[] args) {
        Args argv = new Args();
        JCommander.newBuilder()
                .addObject(argv)
                .build()
                .parse(args);

        SocketClient client = new SocketClient(argv.host, argv.port);
        Scanner sc = new Scanner(System.in);
        while (true) {
            String message = sc.nextLine();
            client.sendMessage(message);
        }

    }
}
