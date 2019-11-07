package com.woowacourse.zzinbros.user.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FriendRelationTest extends UserBaseTest {

    @Test
    public void createFriendRequest() {
        User one = userSampleOf(SAMPLE_ONE);
        User other = userSampleOf(SAMPLE_TWO);
        FriendRelation friendRelation = FriendRelation.createFriendRequest(one, other);
        assertThat(friendRelation.getSender()).isEqualTo(one);
        assertThat(friendRelation.getReceiver()).isEqualTo(other);
        assertThat(friendRelation.getStatus()).isEqualTo(FriendStatus.REQUEST);
    }

    @Test
    public void acceptFriendRequest() {
        User one = userSampleOf(SAMPLE_ONE);
        User other = userSampleOf(SAMPLE_TWO);
        FriendRelation friendRelation = FriendRelation.createFriendRequest(one, other);
        friendRelation.acceptFriendRequest();
        assertThat(friendRelation.getStatus()).isEqualTo(FriendStatus.COMPLETE);
    }
}