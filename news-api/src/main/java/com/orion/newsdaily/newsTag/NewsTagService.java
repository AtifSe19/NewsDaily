package com.orion.newsdaily.newsTag;

import com.orion.newsdaily.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsTagService {

    @Autowired
    public NewsTagRepo newsTagRepo;
    @Autowired
    public TagService tagService;
    public List<Long> findTagsByNewsArticleId(long newsId) {
        return newsTagRepo.findAllByNewsArticleId(newsId);
    }

    public List<NewsTag> addTag(Long newsId, String tagIdList) {
        System.out.print(tagIdList+"haha2");

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
       System.out.print(idList+"haha3");
        List<NewsTag> newsTags = new ArrayList<>();
        for (Long id : idList) {
            NewsTag newsTag = new NewsTag();
            newsTag.setNewsArticleId(newsId);
            newsTag.setTagId(id);
            newsTagRepo.save(newsTag);
            newsTags.add(newsTag);
        }
        return newsTags;
    }
    public List<String> getTagNamesByTagIds(List<Long> tagNames) {
        return tagNames.stream()
                .map(tagId -> tagService.getTagNameById(tagId))
                .collect(Collectors.toList());
    }
//    public List<String> getTagNamesByTagIds(List<Long> newsTagIds) {
//    }
}