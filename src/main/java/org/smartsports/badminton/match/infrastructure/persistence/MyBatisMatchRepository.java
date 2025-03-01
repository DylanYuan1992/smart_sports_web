package org.smartsports.badminton.match.infrastructure.persistence;
// 文件位置：infrastructure/persistence/MyBatisMatchRepository.java

import lombok.RequiredArgsConstructor;
import org.smartsports.badminton.match.domain.model.Match;
import org.springframework.stereotype.Repository;package com.badminton.infrastructure.persistence;

/**
 * MyBatis实现的比赛仓储
 * 功能：
 * 1. 实现领域层定义的仓储接口
 * 2. 处理领域对象与数据库记录的转换
 */
@Repository
@RequiredArgsConstructor
public class MyBatisMatchRepository implements MatchRepository {

    private final MatchMapper matchMapper;
    private final MatchConverter converter;

    @Override
    public Match findById(MatchId matchId) {
        MatchRecord record = matchMapper.findById(matchId.getValue());
        return converter.toDomain(record);
    }

    @Override
    public void save(Match match) {
        MatchRecord record = converter.toRecord(match);
        if (match.isNew()) {
            matchMapper.insert(record);
            match.setId(new MatchId(record.getId()));
        } else {
            matchMapper.update(record);
        }
        publishDomainEvents(match);
    }

    /**
     * 发布领域事件
     */
    private void publishDomainEvents(Match match) {
        match.getDomainEvents().forEach(event ->
                applicationEventPublisher.publishEvent(event)
        );
        match.clearDomainEvents();
    }
}