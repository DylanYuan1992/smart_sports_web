package org.smartsports.badminton.match.domain.model;

/**
 * 羽毛球比赛领域模型（聚合根）
 * 职责：
 * 1. 封装比赛核心业务规则
 * 2. 维护比赛状态一致性
 */
public class Match {
    private MatchId id;
    private LocalDateTime startTime;
    private Duration duration;
    private Set<PlayerId> players;
    private CourtId courtId;
    private MatchStatus status;

    /**
     * 初始化比赛
     *
     * @throws InvalidMatchException 当参赛人数不符合规则时抛出
     */
    public void initialize() {
        validatePlayers();
        this.status = MatchStatus.CREATED;
        registerEvent(new MatchCreatedEvent(this));
    }

    /**
     * 验证参赛人数有效性
     */
    private void validatePlayers() {
        if (players.size() < 2 || players.size() > 4) {
            throw new InvalidMatchException("参赛人数需2-4人");
        }
    }
}