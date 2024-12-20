package com.score.backend.domain.user.repositories;

import com.score.backend.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepositoryCustom {
    Page<User> findFriendsPage(long userId, Pageable pageable);
    List<User> findFriendsByNicknameContaining(Long userId, String nickname);
    List<User> findGroupMatesWhoDidNotExerciseToday(Long groupId);
}
