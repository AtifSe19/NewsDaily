package com.orion.newsdaily.newsTag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsTagService {

    @Autowired
    public NewsTagRepo newsTagRepo;

    public List<Long> findTagsByNewsArticleId(long newsId) {
        return newsTagRepo.findAllByNewsArticleId(newsId);
    }

    public void addTag(Long newsId, String tagIdList) {

        List<Long> idList = new ArrayList<>();
        String[] idStrings = tagIdList.split(",");

        for (String idString : idStrings) {
            try {
                Long id = Long.parseLong(idString.trim());
                idList.add(id);
            } catch (NumberFormatException e) {

                System.err.println("Skipping non-numeric value: " + idString);
            }
        }
        List<NewsTag> newsTags = new ArrayList<>();
        for (Long id : idList) {
            NewsTag newsTag = new NewsTag();
            newsTag.setNewsArticleId(newsId);
            newsTag.setTagId(id);
            newsTagRepo.save(newsTag);
            newsTags.add(newsTag);
        }
    }

    public int deleteAllByNewsArticleId(Long id){
        return newsTagRepo.deleteAllByNewsArticleId(id);
    }

}