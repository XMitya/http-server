package ru.ifmo.server;

import ru.ifmo.server.annotation.Url;

/**
 * Throws exception on handle method.
 */
public class FailHandler implements Handler {
    @Url(url = ServerTest.SERVER_ERROR_URL)
    public void handle(Request request, Response response) throws Exception {
        throw new Exception("Test exception");
    }
}
