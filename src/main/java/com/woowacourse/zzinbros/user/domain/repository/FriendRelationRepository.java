package com.woowacourse.zzinbros.user.domain.repository;

import com.woowacourse.zzinbros.user.domain.FriendRelation;
import com.woowacourse.zzinbros.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendRelationRepository extends JpaRepository<FriendRelation, Long> {
    boolean existsByOneAndOther(User sender, User receiver);

    boolean existsByOneAndOtherAndOwner(User one, User receiver, User owner);

    @Query("select f from FriendRelation f where f.one = :receiver and f.other = :sender and f.owner <> :sender")
    FriendRelation findOppositeFriendRelation(@Param("sender") User sender, @Param("receiver") User receiver);
}
