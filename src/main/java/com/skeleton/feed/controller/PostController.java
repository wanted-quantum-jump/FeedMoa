package com.skeleton.feed.controller;

import com.skeleton.feed.dto.PostQueryRequest;
import com.skeleton.feed.dto.PostResponse;
import com.skeleton.feed.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    @RequestMapping("/posts")
    public ResponseEntity<Page<PostResponse>> getPostsByQuery(@ModelAttribute PostQueryRequest request, User User) {
        return ResponseEntity.ok(postService.getPostsByQuery(request, user));
    }
}
