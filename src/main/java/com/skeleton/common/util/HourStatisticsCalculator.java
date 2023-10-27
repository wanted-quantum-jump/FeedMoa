package com.skeleton.common.util;

import com.skeleton.feed.dto.StatisticsResponse;
import com.skeleton.feed.entity.Statistics;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class HourStatisticsCalculator implements StatisticsCalculator {

    @Override
    public StatisticsResponse calculate(List<Statistics> statisticsList, String value) {
        StatisticsResponse response = new StatisticsResponse();
        Map<String, Long> statisticsMap = response.getStatistics();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        for (Statistics stat : statisticsList) {
            String hourKey = format.format(stat.getPostTime());
            statisticsMap.putIfAbsent(hourKey, 0L);
            updateStatisticsMap(statisticsMap, stat, value, hourKey);
        }

        return response;
    }

    private void updateStatisticsMap(Map<String, Long> statisticsMap, Statistics stat, String value, String key) {
        switch (value) {
            case "count" -> statisticsMap.put(key, statisticsMap.get(key) + 1);
            case "view_count" -> statisticsMap.put(key, statisticsMap.get(key) + stat.getViewCount());
            case "like_count" -> statisticsMap.put(key, statisticsMap.get(key) + stat.getLikeCount());
            case "share_count" -> statisticsMap.put(key, statisticsMap.get(key) + stat.getShareCount());
            default -> throw new IllegalArgumentException("Unsupported value: " + value);
        }
    }
}