package com.woowacourse.zzinbros.user.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class FriendRequestTest extends UserBaseTest {

    private User sender;

    private User receiver;

    @BeforeEach
    public void setUp() {
        sender = userSampleOf(SAMPLE_ONE);
        receiver = userSampleOf(SAMPLE_TWO);
    }

    @Test
    public void createFriendRequest() {
        FriendRequest friendRequest = FriendRequest.create(sender, receiver);
        assertThat(friendRequest.getSender()).isEqualTo(sender);
        assertThat(friendRequest.getReceiver()).isEqualTo(receiver);
    }
}
