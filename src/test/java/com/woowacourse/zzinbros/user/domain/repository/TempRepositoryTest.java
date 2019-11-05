package com.woowacourse.zzinbros.user.domain.repository;

import com.woowacourse.zzinbros.user.domain.FriendRequest;
import com.woowacourse.zzinbros.user.domain.User;
import com.woowacourse.zzinbros.user.domain.UserBaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class TempRepositoryTest extends UserBaseTest {

    private User sender;

    private User receiver;

    @Autowired
    FriendRequestRepository friendRequestRepository;

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        sender = userRepository.save(userSampleOf(SAMPLE_ONE));
        receiver = userRepository.save(userSampleOf(SAMPLE_TWO));
    }

    @Test
    @DisplayName("친구 요청 성공")
    public void requestFriend() {
        receiver.receiveFriendRequest(sender);
        userRepository.save(receiver);

        FriendRequest friendRequest = friendRequestRepository
                .findById(1L)
                .get();
        assertThat(friendRequest.getSender()).isEqualTo(sender);
        assertThat(friendRequest.getReceiver()).isEqualTo(receiver);
    }
}
