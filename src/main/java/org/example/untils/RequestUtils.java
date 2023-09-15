package org.example.untils;

import com.sun.net.httpserver.HttpExchange;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class RequestUtils {
    public static JSONObject parseJsonRequest(HttpExchange exchange) throws IOException {
        try (InputStream is = exchange.getRequestBody()) {
            byte[] requestBodyBytes = is.readAllBytes();
            String requestBody = new String(requestBodyBytes);
            return new JSONObject(requestBody);
        }
    }
}
