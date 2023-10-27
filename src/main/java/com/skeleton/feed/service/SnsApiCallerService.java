package com.skeleton.feed.service;

import com.skeleton.common.exception.CustomException;
import com.skeleton.common.exception.ErrorCode;
import com.skeleton.feed.enums.SnsType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SnsApiCallerService {
    // 기능 개발을 위한 요소로, Endpoint에 저장된 URL은 실제 동작하는 API URL이 아님
    // FIXME : 외부 API URI가 확정되면 내부 Enum 수정 바람

    @Autowired
    private RestTemplate restTemplate;

    //기능 개발을 위한 요소로, Endpoint에 저장된 URL은 실제 동작하는 API URL이 아님
    public ResponseEntity<String> clickLikeOnSns(String contentId, SnsType snsType) {
        String url = Endpoint.getUrl(snsType) + contentId;
        RequestEntity<String> requestEntity = RequestEntity.post(url).body(null);
        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

        // TODO : 각 SNS API 응답 타입을 고려하여 수정
        if (response.getStatusCode() == HttpStatus.OK) {
            return response;
        }
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new CustomException(ErrorCode.SNS_POST_NOT_FOUND);
        }

        return response;
    }

    public ResponseEntity<String> clickShareOnSns(String contentId, SnsType snsType) {
        String url = ShareEndpoint.getUrl(snsType) + contentId;
        log.info(url);
        RequestEntity<String> requestEntity = RequestEntity.post(url).body(null);
        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response;
        }
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new CustomException(ErrorCode.SNS_POST_NOT_FOUND);
        }
        log.info(response.toString().substring(0,20));
        return response;
    }




    @Getter
    @AllArgsConstructor
    enum Endpoint {
        FACEBOOK(SnsType.FACEBOOK, "https://www.facebook.com/likes/"),
        TWITTER(SnsType.TWITTER, "https://www.twitter.com/likes/"),
        INSTAGRAM(SnsType.INSTAGRAM, "https://www.instagram.com/likes/"),
        THREADS(SnsType.THREADS, "https://www.threads.net/likes/");
        SnsType snsType;
        String url;

        public static String getUrl(SnsType snsType) {
            Optional<Endpoint> endpoint = Arrays.stream(values()).filter(value -> value.snsType.equals(snsType))
                    .findAny();
            if (endpoint.isPresent())
                return endpoint.get().url;
            else
                return null;
        }

    }

    @Getter
    @AllArgsConstructor
    enum ShareEndpoint {
        FACEBOOK(SnsType.FACEBOOK, "https://www.facebook.com/share/"),
        TWITTER(SnsType.TWITTER, "https://www.twitter.com/share/"),
        INSTAGRAM(SnsType.INSTAGRAM, "https://www.instagram.com/share/"),
        THREADS(SnsType.THREADS, "https://www.threads.net/share/");
        SnsType snsType;
        String url;

        public static String getUrl(SnsType snsType) {
            Optional<ShareEndpoint> endpoint = Arrays.stream(values()).filter(value -> value.snsType.equals(snsType))
                    .findAny();
            if (endpoint.isPresent())
                return endpoint.get().url;
            else
                return null;
        }
    }

}
