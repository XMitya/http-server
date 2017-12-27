package ru.ifmo.example.server;

import ru.ifmo.example.server.controller.InfoHandler;
import ru.ifmo.server.Server;
import ru.ifmo.server.ServerConfig;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Simple example that shows basic usage.
 */
public class SimpleExample {
    public static void main(String[] args) throws URISyntaxException, IOException {
        // Define config with request handlers
        ServerConfig config = new ServerConfig();
        config.addScanClass(InfoHandler.class);

        // Start server
        @SuppressWarnings("unused")
        Server server = Server.start(config);

        // And open it!
        String infoPage = "http://localhost:" + ServerConfig.DFLT_PORT + "/info.html";

        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI(infoPage));
        }
        else
            System.out.println(">>> Open " + infoPage);

    }

}
