package com.skeleton.feed.service;

import com.skeleton.feed.dto.AddLikeResponse;
import com.skeleton.feed.entity.Post;
import com.skeleton.feed.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final RestTemplateService restTemplateService;

    @Transactional
    public AddLikeResponse addLike(Long id) {
        Optional<Post> o = postRepository.findById(id);
        if (o.isPresent()) {
            Post post = o.get();
            //외부 SNS API 호출로직, 요구 사항이라서 구현하였으나 실제 동작하는 API URL이 아니므로 주석 처리해둠. 필요시 주석 해제하고 사용할 것.
            //restTemplateService.clickLikeOnSns(post.getContentId(), post.getType());
            post.addlike();
            return new AddLikeResponse(post);
        } else {
            throw new EntityNotFoundException("게시물을 찾을 수 없습니다. ID: " + id);
        }
    }
}
