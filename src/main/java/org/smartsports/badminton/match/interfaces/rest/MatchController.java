package org.smartsports.badminton.match.interfaces.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * 比赛资源控制器
 * 职责：
 * 1. 处理HTTP请求
 * 2. 调用应用服务
 * 3. 返回标准化响应
 */
@RestController
@RequestMapping("/api/v1/matches")
public class MatchController {

    private final MatchApplicationService matchService;

    /**
     * 创建新比赛
     *
     * @param request 比赛创建请求体
     * @return 201 Created + 比赛详情
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<MatchResponse> createMatch(
            @Valid @RequestBody CreateMatchRequest request
    ) {
        MatchResponse response = matchService.createMatch(request);
        return ResponseEntity
                .created(URI.create("/matches/" + response.id()))
                .body(response);
    }
}