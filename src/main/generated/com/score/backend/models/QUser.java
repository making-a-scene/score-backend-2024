package com.score.backend.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import com.score.backend.domain.exercise.Exercise;
import com.score.backend.domain.group.GroupEntity;
import com.score.backend.domain.notification.Notification;
import com.score.backend.domain.user.Gender;
import com.score.backend.domain.user.User;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1344599796L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final com.score.backend.config.QBaseEntity _super = new com.score.backend.config.QBaseEntity(this);

    public final ListPath<User, QUser> blockedUsers = this.<User, QUser>createList("blockedUsers", User.class, QUser.class, PathInits.DIRECT2);

    public final NumberPath<Integer> consecutiveDate = createNumber("consecutiveDate", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Double> cumulativeDistance = createNumber("cumulativeDistance", Double.class);

    public final BooleanPath exercisingTime = createBoolean("exercisingTime");

    public final StringPath fcmToken = createString("fcmToken");

    public final ListPath<Exercise, com.score.backend.models.exercise.QExercise> feeds = this.<Exercise, com.score.backend.models.exercise.QExercise>createList("feeds", Exercise.class, com.score.backend.models.exercise.QExercise.class, PathInits.DIRECT2);

    public final ListPath<User, QUser> friends = this.<User, QUser>createList("friends", User.class, QUser.class, PathInits.DIRECT2);

    public final EnumPath<Gender> gender = createEnum("gender", Gender.class);

    public final TimePath<java.time.LocalTime> goal = createTime("goal", java.time.LocalTime.class);

    public final NumberPath<Integer> grade = createNumber("grade", Integer.class);

    public final ListPath<GroupEntity, QGroupEntity> groups = this.<GroupEntity, QGroupEntity>createList("groups", GroupEntity.class, QGroupEntity.class, PathInits.DIRECT2);

    public final NumberPath<Integer> height = createNumber("height", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> joinedAt = createDateTime("joinedAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastExerciseDateTime = createDateTime("lastExerciseDateTime", java.time.LocalDateTime.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final StringPath loginKey = createString("loginKey");

    public final BooleanPath marketing = createBoolean("marketing");

    public final ListPath<User, QUser> mates = this.<User, QUser>createList("mates", User.class, QUser.class, PathInits.DIRECT2);

    public final StringPath nickname = createString("nickname");

    public final ListPath<Notification, QNotification> notifications = this.<Notification, QNotification>createList("notifications", Notification.class, QNotification.class, PathInits.DIRECT2);

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final StringPath profileImg = createString("profileImg");

    public final StringPath refreshToken = createString("refreshToken");

    public final QSchool school;

    public final DateTimePath<java.time.LocalDateTime> schoolUpdatedAt = createDateTime("schoolUpdatedAt", java.time.LocalDateTime.class);

    public final BooleanPath tag = createBoolean("tag");

    public final NumberPath<Double> totalCumulativeTime = createNumber("totalCumulativeTime", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Double> weeklyCumulativeTime = createNumber("weeklyCumulativeTime", Double.class);

    public final NumberPath<Integer> weeklyExerciseCount = createNumber("weeklyExerciseCount", Integer.class);

    public final NumberPath<Integer> weeklyLevelIncrement = createNumber("weeklyLevelIncrement", Integer.class);

    public final NumberPath<Integer> weight = createNumber("weight", Integer.class);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.school = inits.isInitialized("school") ? new QSchool(forProperty("school")) : null;
    }

}

