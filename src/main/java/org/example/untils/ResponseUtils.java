package org.example.untils;

import com.sun.net.httpserver.HttpExchange;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ResponseUtils {
    public static void sendJsonResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            int chunkSize = 4096; // Adjust the chunk size as needed
            int offset = 0;

            while (offset < responseBytes.length) {
                int bytesToWrite = Math.min(chunkSize, responseBytes.length - offset);
                os.write(responseBytes, offset, bytesToWrite);
                offset += bytesToWrite;
            }
        }
    }

    public static void sendErrorResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        exchange.sendResponseHeaders(statusCode, 0);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(message.getBytes());
        }
    }
}
