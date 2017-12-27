package ru.ifmo.example.server.controller;

import ru.ifmo.server.Http;
import ru.ifmo.server.HttpMethod;
import ru.ifmo.server.Request;
import ru.ifmo.server.Response;
import ru.ifmo.server.annotation.Url;

import java.util.Map;

public class InfoHandler {
    @Url(url = "/info.html", method = HttpMethod.GET)
    public void handle(Request request, Response response) throws Exception {
        // Set correct header
        StringBuilder sb = new StringBuilder(Http.OK_HEADER);

        // Set doctype
        sb.append("<!DOCTYPE html>");

        // Write some HTML
        sb.append("<html><body>");

        sb.append("<p>Requested address: ").append(request.getPath()).append("<br>");
        sb.append("<p>Request method: ").append(request.getMethod()).append("<br>");

        Map<String, String> args = request.getArguments();

        if (!args.isEmpty()) {
            sb.append("<p><strong>Passed arguments:</strong><br>");

            for (Map.Entry<String, String> entry : args.entrySet()) {
                sb.append("Key: ").append(entry.getKey());
                sb.append(", Value: ").append(entry.getValue());
                sb.append("<br>");
            }

            sb.append("</p>");
        }

        Map<String, String> headers = request.getHeaders();

        if (!headers.isEmpty()) {
            sb.append("<p><strong>Passed headers:</strong><br>");

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                sb.append("Key: ").append(entry.getKey());
                sb.append(", Value: ").append(entry.getValue());
                sb.append("<br>");
            }

            sb.append("</p>");
        }

        sb.append(" <iframe width=\"420\" height=\"315\"\n" +
                "src=\"https://www.youtube.com/embed/dQw4w9WgXcQ\">\n" +
                "</iframe> ");

        sb.append("</body></html>");

        // Write everything to output
        response.getOutputStream().write(sb.toString().getBytes());
        response.getOutputStream().flush();
    }
}
