package org.example.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.example.annotation.GetMapping;
import org.example.annotation.PathVariable;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import static org.example.untils.ResponseUtils.sendJsonResponse;

public class ControllerManager {
    private final HttpServer server;
    private final Map<String, Method> uriToMethodMap = new HashMap<>();
    private final Map<String, Class<?>> methodParameterMap = new HashMap<>(); // Declare methodParameterMap
    private final Object controllerInstance;

    public ControllerManager(int port) throws IOException {
        this.server = HttpServer.create();
        this.server.bind(new InetSocketAddress(port), 0);
        this.controllerInstance = new AdArticleController(); // Initialize your controller instance here
    }

    public void start() {
        server.setExecutor(null); // Use default executor
        server.start();
    }

    public void addController(Class<?> controller) {
        for (Method method : controller.getDeclaredMethods()) {
            GetMapping annotation = method.getAnnotation(GetMapping.class);
            if (annotation != null) {
                String value = annotation.value();
                uriToMethodMap.put(value, method);

                // Extract parameter type from @PathVariable annotation
                Class<?>[] parameterTypes = method.getParameterTypes();
                for (Annotation[] annotations : method.getParameterAnnotations()) {
                    for (Annotation annotationItem : annotations) {
                        if (annotationItem instanceof PathVariable) {
                            PathVariable pathVariable = (PathVariable) annotationItem;
                            String paramName = pathVariable.value();
                            if (paramName.equals("id") && parameterTypes.length == 1) {
                                methodParameterMap.put(value, parameterTypes[0]);
                            }
                        }
                    }
                }

                server.createContext(value, new CustomHttpHandler());
            }
        }
    }

    private class CustomHttpHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            String patternPath = transformToPattern(path);
            Method method = uriToMethodMap.get(patternPath);

            if (method != null) {
                try {
                    Class<?> parameterType = methodParameterMap.get(patternPath);
                    if (parameterType != null) {
                        String id = extractIdFromPath(path);
                        String response = (String) method.invoke(controllerInstance, id);
                        sendJsonResponse(exchange, 200, response);
                    } else {
                        String response = (String) method.invoke(controllerInstance);
                        sendJsonResponse(exchange, 200, response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    sendJsonResponse(exchange, 500, "Internal Server Error");
                }
            } else {
                sendJsonResponse(exchange, 404, "Method Not Found");
            }
        }
    }

    private static String transformToPattern(String inputPath) {
        String result = inputPath.replaceAll("/adArticles/([0-9a-f-]+|[0-9]+)", "/adArticles/{id}");
        return result;
    }

    private String extractIdFromPath(String path) {
        // Implement logic to extract the 'id' from the path
        // For example, you can split the path and return the last segment
        String[] segments = path.split("/");
        return segments[segments.length - 1];
    }

}
