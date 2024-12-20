package com.score.backend.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import com.score.backend.domain.group.GroupEntity;
import com.score.backend.domain.group.rank.GroupRanking;
import com.score.backend.domain.user.User;


/**
 * QGroupEntity is a Querydsl query type for GroupEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroupEntity extends EntityPathBase<GroupEntity> {

    private static final long serialVersionUID = 656954081L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroupEntity groupEntity = new QGroupEntity("groupEntity");

    public final com.score.backend.config.QBaseEntity _super = new com.score.backend.config.QBaseEntity(this);

    public final QUser admin;

    public final QSchool belongingSchool;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Double> cumulativeTime = createNumber("cumulativeTime", Double.class);

    public final DateTimePath<java.time.LocalDateTime> groupCreatedAt = createDateTime("groupCreatedAt", java.time.LocalDateTime.class);

    public final StringPath groupDescription = createString("groupDescription");

    public final NumberPath<Long> groupId = createNumber("groupId", Long.class);

    public final StringPath groupImg = createString("groupImg");

    public final StringPath groupName = createString("groupName");

    public final StringPath groupPassword = createString("groupPassword");

    public final ListPath<GroupRanking, com.score.backend.models.grouprank.QGroupRanking> groupRankings = this.<GroupRanking, com.score.backend.models.grouprank.QGroupRanking>createList("groupRankings", GroupRanking.class, com.score.backend.models.grouprank.QGroupRanking.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> groupUpdatedAt = createDateTime("groupUpdatedAt", java.time.LocalDateTime.class);

    public final BooleanPath isPrivate = createBoolean("isPrivate");

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    public final SetPath<User, QUser> members = this.<User, QUser>createSet("members", User.class, QUser.class, PathInits.DIRECT2);

    public final NumberPath<Integer> todayExercisedCount = createNumber("todayExercisedCount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Integer> userLimit = createNumber("userLimit", Integer.class);

    public QGroupEntity(String variable) {
        this(GroupEntity.class, forVariable(variable), INITS);
    }

    public QGroupEntity(Path<? extends GroupEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGroupEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGroupEntity(PathMetadata metadata, PathInits inits) {
        this(GroupEntity.class, metadata, inits);
    }

    public QGroupEntity(Class<? extends GroupEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.admin = inits.isInitialized("admin") ? new QUser(forProperty("admin"), inits.get("admin")) : null;
        this.belongingSchool = inits.isInitialized("belongingSchool") ? new QSchool(forProperty("belongingSchool")) : null;
    }

}

