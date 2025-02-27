package com.score.backend.domain.friend;

import com.score.backend.config.BaseEntity;
import com.score.backend.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.coyote.BadRequestException;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id", nullable = false)
    private User friend;

    public Friend(User user, User friend) throws BadRequestException {
        if (user.getId() == friend.getId()) {
            throw new BadRequestException("자기 자신을 친구로 추가할 수 없습니다.");
        }
        this.user = user;
        this.friend = friend;
    }
}
