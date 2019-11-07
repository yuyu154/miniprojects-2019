package com.woowacourse.zzinbros.user.domain.repository;

import com.woowacourse.zzinbros.user.domain.FriendRelation;
import com.woowacourse.zzinbros.user.domain.User;
import com.woowacourse.zzinbros.user.domain.UserBaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FriendRelationRepositoryTest extends UserBaseTest {

    @Autowired
    FriendRelationRepository friendRelationRepository;

    @Autowired
    UserRepository userRepository;

    private User sender;
    private User receiver;

    @BeforeEach
    void setUp() {
        sender = userRepository.save(userSampleOf(SAMPLE_ONE));
        receiver = userRepository.save(userSampleOf(SAMPLE_TWO));
    }

    @Test
    void existsByOneAndOther() {
        friendRelationRepository.save(FriendRelation.createFriendRequest(sender, receiver));
        assertTrue(friendRelationRepository.existsBySenderAndReceiver(sender, receiver));
    }
}