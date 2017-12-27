package ru.ifmo.example.server;

import ru.ifmo.example.server.controller.Index;
import ru.ifmo.server.*;

/**
 * Simple hello world example.
 */
public class HelloWorldExample {
    public static void main(String[] args) {
        ServerConfig config = new ServerConfig();
        config.addScanClass(Index.class);
        Server.start(config);
    }
}
