package com.woowacourse.zzinbros.user.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FriendRelationshipTest extends UserBaseTest {

    @Test
    public void createFriendRequest() {
        User one = userSampleOf(SAMPLE_ONE);
        User other = userSampleOf(SAMPLE_TWO);
        FriendRelationship friendRelationship = FriendRelationship.createFriendRequest(one, other);
        assertThat(friendRelationship.getOne()).isEqualTo(one);
        assertThat(friendRelationship.getOther()).isEqualTo(other);
        assertThat(friendRelationship.getStatus()).isEqualTo(FriendStatus.REQUEST);
        assertThat(friendRelationship.getOwner()).isEqualTo(one);
    }

    @Test
    public void acceptFriendRequest() {
        User one = userSampleOf(SAMPLE_ONE);
        User other = userSampleOf(SAMPLE_TWO);
        FriendRelationship friendRelationship = FriendRelationship.createFriendRequest(one, other);
        friendRelationship.acceptFriendRequest();
        assertThat(friendRelationship.getStatus()).isEqualTo(FriendStatus.COMPLETE);
    }
}