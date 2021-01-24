package ru.itis.homework.jcommander;

import com.beust.jcommander.*;

@Parameters(separators = "=")
public class Args {

    @Parameter(names = {"--server-ip"})
    public String host;

    @Parameter(names = {"--server-port", "--port"})
    public int port;
}
