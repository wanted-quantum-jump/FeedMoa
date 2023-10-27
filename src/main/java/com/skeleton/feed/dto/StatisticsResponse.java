package com.skeleton.feed.dto;

import com.skeleton.feed.entity.PostHashtag;
import java.util.Map;
import java.util.TreeMap;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticsResponse {
    private Map<String, Long> statistics;

    public StatisticsResponse() {
        this.statistics = new TreeMap<>();
    }

    public Map<String, Long> getStatistics() {
        return statistics;
    }

    public void setStatistics(Map<String, Long> statistics) {
        this.statistics = statistics;
    }
}
