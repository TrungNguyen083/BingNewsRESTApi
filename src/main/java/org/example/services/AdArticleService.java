package org.example.services;

import org.example.models.AdArticle;
import org.example.repositories.AdArticleRepository;

import java.util.List;
import java.util.function.Predicate;

public class AdArticleService {
    private final AdArticleRepository adArticleRepository;

    public AdArticleService(AdArticleRepository adArticleRepository) {
        this.adArticleRepository = adArticleRepository;
    }

    public List<AdArticle> getAllAds() throws Exception {
        return adArticleRepository.getAllArticle();
    }

    public List<AdArticle> findAd(Predicate<AdArticle> predicate) throws Exception {
        return adArticleRepository.findArticle(predicate);
    }

    public void insertAd(AdArticle adArticle) throws Exception {
        // Perform any additional logic or validation here
        adArticleRepository.insertArticle(adArticle);
    }

    public void updateUser(AdArticle adArticle, Predicate<AdArticle> predicate) throws Exception {
        // Perform any additional logic or validation here
        adArticleRepository.updateArticle(adArticle, predicate);
    }

    public void deleteUser(Predicate<AdArticle> predicate) throws Exception {
        // Perform any additional logic or validation here
        adArticleRepository.deleteArticle(predicate);
    }
}
