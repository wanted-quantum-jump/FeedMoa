package com.skeleton.feed;

import com.skeleton.feed.entity.Post;
import com.skeleton.feed.enums.SnsType;
import com.skeleton.feed.repository.PostRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.BufferedReader;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.util.Random;
import java.util.UUID;

@Disabled //DB에 더미데이터 넣을때만 주석 처리해서 사용
@SpringBootTest
public class PostDummyMaker {
    @Autowired
    private PostRepository postRepository;

    static SnsType[] snsTypes;
    static Random random;
    static BufferedReader br;

    // Post 10개 분량으로 설정된 문자열
    static String s = """
            국회나 그 위원회의
            대한민국은 국제평화의 유지에 노력하고 침략적 전쟁을 부인한다.
            중앙선거관리위원회는
            대법원장과 대법관이 아닌 법관의 임기는 10년으로 하며, 법률이 정하는 바에 의하여 연임할 수 있다.
            대통령의 국법상 행위
            국회는 국무총리 또는 국무위원의 해임을 대통령에게 건의할 수 있다.
            언론·출판은 타인의
            나는 헌법을 준수하고 국가를 보위하며 조국의 평화적 통일과 국민의 자유와 복리의 증진 및 민족문화의 창달에 노력하여 대통령으로서의 직책을 성실히 수행할 것을 국민 앞에 엄숙히 선서합니다.
            국무총리 또는 행정각
            국정감사 및 조사에 관한 절차 기타 필요한 사항은 법률로 정한다.
            형사피의자 또는 형사
            광물 기타 중요한 지하자원·수산자원·수력과 경제상 이용할 수 있는 자연력은 법률이 정하는 바에 의하여 일정한 기간 그 채취·개발 또는 이용을 특허할 수 있다.
            비상계엄이 선포된 때
            국회에 제출된 법률안 기타의 의안은 회기중에 의결되지 못한 이유로 폐기되지 아니한다. 다만, 국회의원의 임기가 만료된 때에는 그러하지 아니하다.
            언론·출판에 대한 허
            정부는 예산에 변경을 가할 필요가 있을 때에는 추가경정예산안을 편성하여 국회에 제출할 수 있다.
            대통령의 임기는 5년
            농업생산성의 제고와 농지의 합리적인 이용을 위하거나 불가피한 사정으로 발생하는 농지의 임대차와 위탁경영은 법률이 정하는 바에 의하여 인정된다.
            모든 국민은 통신의
            모든 국민은 신체의 자유를 가진다. 누구든지 법률에 의하지 아니하고는 체포·구속·압수·수색 또는 심문을 받지 아니하며, 법률과 적법한 절차에 의하지 아니하고는 처벌·보안처분 또는 강제노역을 받지 아니한다.""";


    @BeforeAll
    static void init() {
        snsTypes = SnsType.values();
        random = new Random();
        br = new BufferedReader(new StringReader(s));

    }


    @DisplayName("Post 더미데이터 10개 생성, 해시태그 X")
    @RepeatedTest(10)
    void addDummy() throws Exception {

        // 생성자 접근제어자 변경
        Constructor<?>[] constructor = Post.class.getDeclaredConstructors();
        constructor[0].setAccessible(true);
        Post post = (Post) constructor[0].newInstance();

        //필드값 주입
        ReflectionTestUtils.setField(post, "contentId", UUID.randomUUID().toString());
        ReflectionTestUtils.setField(post, "title", br.readLine());
        ReflectionTestUtils.setField(post, "content", br.readLine());
        ReflectionTestUtils.setField(post, "type", snsTypes[random.nextInt(snsTypes.length)]); // SnsType 랜덤

        postRepository.save(post);

    }
}
