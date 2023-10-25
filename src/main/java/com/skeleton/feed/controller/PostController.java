package com.skeleton.feed.controller;

import com.skeleton.feed.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedmoa")
public class PostController {
    private final PostService postService;

    @PatchMapping("/likes/{id}")
    private ResponseEntity<?> addLike(@PathVariable Long id) {
        return ResponseEntity.ok().body(postService.addLike(id));
    }
}
