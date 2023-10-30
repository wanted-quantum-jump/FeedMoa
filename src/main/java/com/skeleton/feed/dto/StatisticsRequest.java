package com.skeleton.feed.dto;

import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class StatisticsRequest {
    private String value;
    private String type;
    private String hashtag;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date start;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date end;
}
