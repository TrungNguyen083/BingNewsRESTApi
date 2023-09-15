package org.example;

import com.sun.net.httpserver.HttpServer;
import org.example.ORM.CrudRepository;
import org.example.ORM.CrudRepositoryImp;
import org.example.config.AppConfig;
import org.example.controllers.AdArticleController;
import org.example.models.AdArticle;
import org.example.repositories.AdArticleRepository;
import org.example.services.AdArticleService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String apiKey = "mycustomapikey";
        int maxUsersPerPage = 50;
        // Create an instance of AppConfig with the loaded configuration
        AppConfig appConfig = new AppConfig(apiKey, maxUsersPerPage);
        // Initialize the crudRepository with the configuration
        CrudRepository<AdArticle> crudRepository = new CrudRepositoryImp<>();
        // Initialize repositories with the configuration
        AdArticleRepository adArticleRepository = new AdArticleRepository(crudRepository);
        // Initialize services with the configuration
        AdArticleService adArticleService = new AdArticleService(adArticleRepository);
        // Initialize controllers
        AdArticleController adArticleController = new AdArticleController(adArticleService, appConfig);
        // Set up HTTP server
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Define routes
        server.createContext("/api/adArticles", adArticleController.getAllAdArticle());
        server.createContext("/api/adArticles/", adArticleController.getAdArticleById());
        // Add more routes for creating, updating, and deleting users

        // Start the server
        server.start();
        System.out.println("Server is listening on port " + 8080);
    }
}