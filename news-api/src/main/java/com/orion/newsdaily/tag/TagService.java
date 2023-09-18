package com.orion.newsdaily.tag;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepo tagRepository;

    @Autowired
    public TagService(TagRepo tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag getTagById(long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found with id: " + id));
    }

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public Tag updateTag(long id, Tag tagDetails) {
        Tag tag = getTagById(id);
        tag.setTagName(tagDetails.getTagName());
        // Update other fields as needed
        return tagRepository.save(tag);
    }

    public void deleteTag(long id) {
        Tag tag = getTagById(id);
        tagRepository.delete(tag);
    }
}

