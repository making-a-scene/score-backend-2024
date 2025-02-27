package com.score.backend.domain.exercise.emotion;

import com.score.backend.config.BaseEntity;
import com.score.backend.domain.user.User;
import com.score.backend.domain.exercise.Exercise;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
public class Emotion extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emotion_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User agent; // 이 감정 표현을 추가한 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise feed; // 이 감정 표현이 추가된 피드

    @Column(columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private EmotionType emotionType; // 어떤 감정 표현인지?

    public void setEmotion(User agent, Exercise feed, EmotionType emotionType) {
        this.agent = agent;
        this.feed = feed;
        this.emotionType = emotionType;
        feed.getEmotions().add(this);
    }
}
