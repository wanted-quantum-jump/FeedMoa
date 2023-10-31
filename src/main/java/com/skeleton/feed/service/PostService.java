package com.skeleton.feed.service;

import com.skeleton.common.exception.CustomException;
import com.skeleton.common.exception.ErrorCode;
import com.skeleton.feed.dto.AddLikeResponse;
import com.skeleton.feed.dto.PostDetailResponse;
import com.skeleton.feed.dto.AddShareResponse;
import com.skeleton.feed.dto.PostQueryRequest;
import com.skeleton.feed.dto.PostResponse;
import com.skeleton.feed.entity.Post;
import com.skeleton.feed.enums.Direction;
import com.skeleton.feed.enums.SearchBy;
import com.skeleton.feed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CountService countService;

    @Transactional(readOnly = true)
    public Page<PostResponse> getPostsByQuery(PostQueryRequest request, Authentication authentication) {
        String hashtag = getHashtag(request, authentication);
        Pageable pageable = getPageable(request);
        String keyword = request.getSearch();
        boolean searchInTitle = shouldSearchInTitle(request.getSearchBy());
        boolean searchInContent = shouldSearchInContent(request.getSearchBy());

        return postRepository.findPostsByConditions(
                        hashtag, request.getType(), keyword, searchInTitle, searchInContent, pageable)
                .map(post -> PostResponse.fromEntity(post, limitContent(post.getContent())));
    }

    private String getHashtag(PostQueryRequest request, Authentication authentication) {
        String userAccount = authentication.getPrincipal().toString();
        return StringUtils.hasText(request.getHashtag()) ? request.getHashtag() : userAccount;
    }

    private Pageable getPageable(PostQueryRequest request) {
        String orderBy = request.getOrderBy().getValue();
        Sort.Direction direction =
                (request.getDirection() == Direction.DESC) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return PageRequest.of(request.getPage(), request.getPageCount(), Sort.by(direction, orderBy));
    }

    private boolean shouldSearchInTitle(SearchBy searchBy) {
        return searchBy == SearchBy.TITLE || searchBy == SearchBy.TITLE_CONTENT;
    }

    private boolean shouldSearchInContent(SearchBy searchBy) {
        return searchBy == SearchBy.CONTENT || searchBy == SearchBy.TITLE_CONTENT;
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
    @Transactional(readOnly = true)
    public PostDetailResponse getPostDetail(Long id) {
        Post post = getPost(id);
        countService.incrementViewCount(id);
        return new PostDetailResponse(post);
    }

    private Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    @Transactional
    public AddShareResponse addShare(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        //snsApiCallerService.clickShareOnSns(post.getContentId(), post.getType());
        post.addShare();
        return new AddShareResponse(post);

    }
}
