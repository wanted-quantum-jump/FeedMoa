package com.skeleton.feed.service;

import com.skeleton.common.exception.CustomException;
import com.skeleton.common.exception.ErrorCode;
import com.skeleton.common.util.DateStatisticsCalculator;
import com.skeleton.common.util.HourStatisticsCalculator;
import com.skeleton.common.util.StatisticsCalculator;
import com.skeleton.feed.dto.StatisticsRequest;
import com.skeleton.feed.dto.StatisticsResponse;
import com.skeleton.feed.entity.Statistics;
import com.skeleton.feed.repository.StatisticsRepository;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final StatisticsRepository repository;
    private final List<StatisticsCalculator> calculatorList;
    private final Map<String, StatisticsCalculator> calculators = new HashMap<>();

    @PostConstruct
    public void init() {
        calculators.putAll(calculatorList.stream()
                .collect(Collectors.toMap(this::getCalculatorType, Function.identity())));
    }

    private String getCalculatorType(StatisticsCalculator calculator) {
        if (calculator instanceof DateStatisticsCalculator) {
            return "date";
        }
        if (calculator instanceof HourStatisticsCalculator) {
            return "hour";
        }
        throw new CustomException(ErrorCode.TYPE_NOT_FOUND);
    }

    public StatisticsResponse getStatistics(StatisticsRequest request, Authentication authentication) {
        String userId = ((UserDetails) authentication.getPrincipal()).getUsername();
        String hashtag = request.getHashtag() != null ? request.getHashtag() : "";

        List<Statistics> statisticsList = repository.findByTimeRange(request.getStart(), request.getEnd())
                .stream()
                .filter(stat -> userId.equals(stat.getUserId()) && stat.getPost().getContent().contains(hashtag))
                .collect(Collectors.toList());

        StatisticsCalculator calculator = calculators.get(request.getType());
        return calculator.calculate(statisticsList, request.getValue());
    }
}