package com.woowacourse.zzinbros.user.domain.repository;

import com.woowacourse.zzinbros.user.domain.FriendRelation;
import com.woowacourse.zzinbros.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface FriendRelationRepository extends JpaRepository<FriendRelation, Long> {
    boolean existsBySenderAndReceiver(User sender, User receiver);

//    @Query("select from FriendRelation f")
//    Set<User> findAllFriends(User user);
}
