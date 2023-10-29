package com.skeleton.feed.dto;

import com.skeleton.feed.enums.Direction;
import com.skeleton.feed.enums.OrderBy;
import com.skeleton.feed.enums.SearchBy;
import com.skeleton.feed.enums.SnsType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostQueryRequest {
    private String hashtag;

    private SnsType type;

    private OrderBy orderBy = OrderBy.CREATED_AT;

    private Direction direction = Direction.DESC;

    private SearchBy searchBy = SearchBy.TITLE_CONTENT;

    private String search;

    private int pageCount = 10;

    private int page = 0;
}

