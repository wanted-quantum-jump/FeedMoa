package com.skeleton.feed.controller;

import com.skeleton.feed.dto.PostQueryRequest;
import com.skeleton.feed.dto.PostResponse;
import com.skeleton.feed.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<Page<PostResponse>> getPostsByQuery(@ModelAttribute PostQueryRequest request,
                                                              Authentication authentication) {
        return ResponseEntity.ok().body(postService.getPostsByQuery(request, authentication));
    }

    @PatchMapping("/{id}/likes")
    private ResponseEntity<?> addLike(@PathVariable Long id) {
        return ResponseEntity.ok().body(postService.addLike(id));
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getPost(@PathVariable Long id) {
        return ResponseEntity.ok().body(postService.getPostDetail(id));
    }

    @PatchMapping("/{id}/share")
    private ResponseEntity<?> addShare(@PathVariable Long id) {
        return ResponseEntity.ok().body(postService.addShare(id));

    }
}
