package com.orion.newsdaily.newsTag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsTagService {

    @Autowired
    public NewsTagRepo newsTagRepo;




    public List<Long> findTagsByNewsArticleId(long newsId) {
        return newsTagRepo.findAllByNewsArticleId(newsId);
    }
}
