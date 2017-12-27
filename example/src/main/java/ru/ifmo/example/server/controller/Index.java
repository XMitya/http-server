package ru.ifmo.example.server.controller;

import ru.ifmo.server.Http;
import ru.ifmo.server.Request;
import ru.ifmo.server.Response;
import ru.ifmo.server.annotation.Url;

import java.io.OutputStreamWriter;
import java.io.Writer;

public class Index {
    @Url(url = "/index")
    public void index(Request request, Response response) throws Exception {
        Writer writer = new OutputStreamWriter(response.getOutputStream());
        writer.write(Http.OK_HEADER + "Hello World!");
        writer.flush();
    }
}
