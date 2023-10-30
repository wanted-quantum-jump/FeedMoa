package com.skeleton.common.util;

import com.skeleton.feed.dto.StatisticsResponse;
import com.skeleton.feed.entity.Statistics;
import java.util.List;

public interface StatisticsCalculator {
    StatisticsResponse calculate(List<Statistics> statisticsList, String value);
}
