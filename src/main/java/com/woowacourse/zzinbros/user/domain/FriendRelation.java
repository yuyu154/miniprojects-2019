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
                columnNames = {"ONE_USER_ID", "OTHER_USER_ID"},
                name = "uk_friend_relationship_owner_and_friend")
)
public class FriendRelation extends BaseEntity {

    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "ONE_USER_ID", foreignKey = @ForeignKey(name = "fk_friend_relationship_from_to_user"),
            nullable = false, updatable = false)
    private User one;

    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "OTHER_USER_ID", foreignKey = @ForeignKey(name = "fk_friend_relationship_friend_to_user"),
            nullable = false, updatable = false)
    private User other;

    @Enumerated
    private FriendStatus status;

    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "OWNER_USER_ID", foreignKey = @ForeignKey(name = "fk_friend_relationship_owner_to_user"))
    private User owner;

    private FriendRelation(User one, User other, FriendStatus status, User owner) {
        this.one = one;
        this.other = other;
        this.status = status;
        this.owner = owner;
    }

    public static FriendRelation createFriendRequest(User from, User to) {
        return new FriendRelation(from, to, FriendStatus.REQUEST, from);
    }

    public void acceptFriendRequest() {
        this.status = FriendStatus.COMPLETE;
    }
}
