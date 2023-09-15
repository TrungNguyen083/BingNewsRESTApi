package org.example.repositories;

import org.example.ORM.CrudRepository;
import org.example.models.AdArticle;

import java.util.List;
import java.util.function.Predicate;

public class AdArticleRepository {
    private CrudRepository<AdArticle> repository;

    public AdArticleRepository(CrudRepository<AdArticle> repository) {
        this.repository = repository;
    }

    public List<AdArticle> getAllArticle() throws Exception {
        return repository.get(AdArticle.class);
    }

    public List<AdArticle> findArticle(Predicate<AdArticle> predicate) throws Exception {
        return repository.find(AdArticle.class, predicate);
    }

    public void insertArticle(AdArticle article) throws Exception {
        repository.insert(article);
    }

    public void updateArticle(AdArticle article, Predicate<AdArticle> predicate) throws Exception {
        repository.update(article, predicate);
    }

    public void deleteArticle(Predicate<AdArticle> predicate) throws Exception {
        repository.delete(AdArticle.class, predicate);
    }
}
