package com.woowacourse.zzinbros.user.domain;

import com.woowacourse.zzinbros.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"SENDER_USER_ID", "RECEIVER_USER_ID"},
                name = "uk_friend_relationship_owner_and_friend")
)
public class FriendRelation extends BaseEntity {

    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "SENDER_USER_ID", foreignKey = @ForeignKey(name = "fk_friend_relationship_sender_to_user"),
            nullable = false, updatable = false)
    private User sender;

    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "RECEIVER_USER_ID", foreignKey = @ForeignKey(name = "fk_friend_relationship_receiver_to_user"),
            nullable = false, updatable = false)
    private User receiver;

    @Enumerated
    private FriendStatus status;

    private FriendRelation(User sender, User receiver, FriendStatus status) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
    }

    public static FriendRelation createFriendRequest(User from, User to) {
        return new FriendRelation(from, to, FriendStatus.REQUEST);
    }

    public void acceptFriendRequest() {
        this.status = FriendStatus.COMPLETE;
    }
}
