package org.example;


import org.example.ORM.CrudRepositoryImp;
import org.example.models.AdArticle;
import org.example.repositories.AdArticleRepository;
import org.junit.Test;

import java.util.List;

public class MainTest {
    @Test
    public void testFindAd() throws Exception {
        AdArticleRepository adArticleRepository = new AdArticleRepository(new CrudRepositoryImp<>());
        List<AdArticle> adArticleList = adArticleRepository.findArticle(x -> x.getGuid().equals("21355621548461231"));
        adArticleList.forEach(AdArticle::printInfo);
    }
}