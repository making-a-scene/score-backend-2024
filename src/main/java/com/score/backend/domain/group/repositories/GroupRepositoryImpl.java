package com.score.backend.domain.group.repositories;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.score.backend.domain.group.GroupEntity;
import com.score.backend.domain.group.QGroupEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GroupRepositoryImpl implements GroupRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    QGroupEntity ge = QGroupEntity.groupEntity;

    @Override
    public List<GroupEntity> findGroupsByKeyword(Long schoolId, String keyword) {
        return queryFactory
                .selectFrom(ge)
                .where(ge.belongingSchool.id.eq(schoolId), ge.groupName.containsIgnoreCase(keyword))
                .fetch();

    }
}
