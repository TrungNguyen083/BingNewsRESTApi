package org.example.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpHandler;
import org.example.config.AppConfig;
import org.example.models.AdArticle;
import org.example.services.AdArticleService;
import org.example.untils.ResponseUtils;

import java.util.List;

public class AdArticleController {
    private final AdArticleService adArticleService;
    private final AppConfig appConfig;

    public AdArticleController(AdArticleService adArticleService, AppConfig appConfig) {
        this.adArticleService = adArticleService;
        this.appConfig = appConfig;
    }

    public HttpHandler getAllAdArticle() {
        return exchange -> {
            // Fetch all users from the service
            try {
                List<AdArticle> adArticles = adArticleService.getAllAds();
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(adArticles);
                ResponseUtils.sendJsonResponse(exchange, 200, json);
            } catch (Exception e) {
                ResponseUtils.sendErrorResponse(exchange, 500, "Internal Server Error");
            }
        };
    }

    public HttpHandler getAdArticleById() {
        return exchange -> {
            // Extract user ID from the request
            String adArticleID = String.valueOf(exchange.getRequestURI().getPath().split("/")[3]);
            // Fetch user by ID from the service
            try {
                List<AdArticle> adArticleList = adArticleService.findAd(x -> x.getGuid().equals(adArticleID));
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(adArticleList);
                if (adArticleList != null) {
                    ResponseUtils.sendJsonResponse(exchange, 200, json);
                } else {
                    ResponseUtils.sendErrorResponse(exchange, 404, "AdArticle not found");
                }
            } catch (Exception e) {
                ResponseUtils.sendErrorResponse(exchange, 500, "Internal Server Error");
            }
        };
    }
}
