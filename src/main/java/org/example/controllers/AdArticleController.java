package org.example.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ORM.CrudRepositoryImp;
import org.example.annotation.Controller;
import org.example.annotation.GetMapping;
import org.example.annotation.PathVariable;
import org.example.models.AdArticle;
import org.example.repositories.AdArticleRepository;

import java.util.List;

@Controller
public class AdArticleController {
    AdArticleRepository adArticleRepository;
    ObjectMapper objectMapper;
    public AdArticleController() {
        adArticleRepository = new AdArticleRepository(new CrudRepositoryImp<>());
        objectMapper = new ObjectMapper();
    }

    @GetMapping(value = "/adArticles")
    public String getAdArticles() throws Exception {
        System.out.println("Method 1");
        List<AdArticle> adArticleList = adArticleRepository.getAllArticle();
        return objectMapper.writeValueAsString(adArticleList);
    }

    @GetMapping(value = "/adArticles/{id}")
    public String findAdArticle(@PathVariable("id") String id) throws Exception {
        System.out.println("Method 2");
        List<AdArticle> adArticleList = adArticleRepository.findArticle(x -> x.getGuid().equals(id));
        return objectMapper.writeValueAsString(adArticleList);
    }


}
