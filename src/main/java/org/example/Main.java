package org.example;

import org.example.controllers.AdArticleController;
import org.example.controllers.ControllerManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        int PORT = 8080;

        ControllerManager customHttpServer = new ControllerManager(PORT);
        customHttpServer.addController(AdArticleController.class);
        customHttpServer.start();

        System.out.println("Server is listening on port " + PORT);
    }
}