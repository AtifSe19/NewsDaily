package com.orion.newsdaily.tag;

import com.orion.newsdaily.basic.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Tag>>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        return new ResponseEntity<>(ApiResponse.of(tags), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Tag>> getTagById(@PathVariable long id) {
        Tag tag = tagService.getTagById(id);
        return new ResponseEntity<>(ApiResponse.of(tag), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Tag>> createTag(@RequestBody Tag tag) {
        Tag createdTag = tagService.createTag(tag);
        return new ResponseEntity<>(ApiResponse.of(createdTag), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Tag>> updateTag(@PathVariable long id, @RequestBody Tag tag) {
        Tag updatedTag = tagService.updateTag(id, tag);
        return new ResponseEntity<>(ApiResponse.of(updatedTag), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable long id) {
        tagService.deleteTag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

