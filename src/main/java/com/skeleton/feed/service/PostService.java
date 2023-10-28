package com.skeleton.feed.service;

import com.skeleton.feed.dto.PostQueryRequest;
import com.skeleton.feed.dto.PostResponse;
import com.skeleton.feed.enums.Direction;
import com.skeleton.feed.enums.SearchBy;
import com.skeleton.common.exception.CustomException;
import com.skeleton.common.exception.ErrorCode;
import com.skeleton.feed.dto.AddLikeResponse;
import com.skeleton.feed.dto.AddShareResponse;
import com.skeleton.feed.entity.Post;
import com.skeleton.feed.repository.PostRepository;
import com.skeleton.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public Page<PostResponse> getPostsByQuery(PostQueryRequest request, User user) {
        String hashtag = getHashtag(request, user);
        Pageable pageable = getPageable(request);
        String[] searchKeyword = getSearchKeyword(request);

        return postRepository.findPostsByConditions(
                hashtag, request.getType(), searchKeyword[0], searchKeyword[1], pageable)
                .map(post -> PostResponse.fromEntity(post, limitContent(post.getContent())));
    }

    private String getHashtag(PostQueryRequest request, User user) {
        return StringUtils.hasText(request.getHashtag()) ? request.getHashtag() : user.getAccount();
    }

    private Pageable getPageable(PostQueryRequest request) {
        String orderBy = request.getOrderBy().getValue();
        Sort.Direction direction = (request.getDirection() == Direction.DESC)? Sort.Direction.DESC : Sort.Direction.ASC;
        return PageRequest.of(request.getPage(), request.getPageCount(), Sort.by(direction, orderBy));
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

    // 외부 SNS API 호출 서비스, 요구 사항이라서 구현하였으나 실제 유효한 API URL을 사용하지 않으므로, 현재 호출시 오류시 오류 발생
    // private final SnsApiCallerService snsApiCallerService;

    @Transactional
    public AddLikeResponse addLike(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        //snsApiCallerService.clickLikeOnSns(post.getContentId(), post.getType());
        post.addLike();
        return new AddLikeResponse(post);
    }

    @Transactional
    public AddShareResponse addShare(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        //snsApiCallerService.clickShareOnSns(post.getContentId(), post.getType());
        post.addShare();
        return new AddShareResponse(post);
    }
}
