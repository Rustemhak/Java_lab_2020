package ru.itis.homework.jcommander;

import com.beust.jcommander.*;

@Parameters(separators = "=")
public class Args {

    @Parameter(names = {"--server-ip"})
    public String host;

    @Parameter(names = {"--server-port", "--port"})
    public int port;
}

// java -jar chat-server.jar --port=4321
// java -jar chat-client.jar --server-ip=127.0.0.1 --server-port=4321