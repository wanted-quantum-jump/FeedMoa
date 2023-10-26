package com.skeleton.feed.service;

import com.skeleton.feed.dto.PostQueryRequest;
import com.skeleton.feed.dto.PostResponse;
import com.skeleton.feed.enums.SearchBy;
import com.skeleton.feed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Page<PostResponse> getPostsByQuery(PostQueryRequest request, User user) {
        String hashtag = getHashtag(request, user);
        Pageable pageable = getPageable(request);
        String[] searchKeyword = getSearchKeyword(request);

        return postRepository.findByHashtagAndTypeAndTitleContainingOrContentContaining(
                        hashtag, request.getType(), searchKeyword[0], searchKeyword[1], pageable)
                .map(post -> PostResponse.fromEntity(post, limitContent(post.getContent())));
    }

    private String getHashtag(PostQueryRequest request, User user) {
        return StringUtils.hasText(request.getHashtag()) ? request.getHashtag() : user.getUsername();
    }

    private Pageable getPageable(PostQueryRequest request) {
        return PageRequest.of(request.getPage(), request.getPageCount(),
                Sort.by(request.getOrder().name(), request.getOrderBy().name()));
    }

    private String[] getSearchKeyword(PostQueryRequest request) {
        String title = null;
        String content = null;

        if (StringUtils.hasText(request.getSearch())) {
            if (request.getSearchBy() == SearchBy.TITLE_CONTENT) {
                title = request.getSearch();
                content = request.getSearch();
            } else if (request.getSearchBy() == SearchBy.TITLE) {
                title = request.getSearch();
            } else if (request.getSearchBy() == SearchBy.CONTENT) {
                content = request.getSearch();
            }
        }

        return new String[] { title, content };
    }

    private String limitContent(String content) {
        return content.substring(0, Math.min(content.length(), 20));
    }
}
