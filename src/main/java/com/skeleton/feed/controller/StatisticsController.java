package com.skeleton.feed.controller;

import com.skeleton.feed.dto.StatisticsRequest;
import com.skeleton.feed.dto.StatisticsResponse;
import com.skeleton.feed.service.StatisticsService;
import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService service;

    @GetMapping
    public ResponseEntity<?> getStatistics(@ModelAttribute StatisticsRequest request, Authentication authentication) {
        return ResponseEntity.ok().body(service.getStatistics(request, authentication));
    }
}