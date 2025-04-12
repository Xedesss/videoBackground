package com.videoBackground.health.controller;

import com.videoBackground.health.common.PageResult;
import com.videoBackground.health.common.Result;
import com.videoBackground.health.service.WatchHistoryService;
import com.videoBackground.health.vo.WatchHistoryVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {
    
    /**
     * 测试用户ID（实际应该从用户会话中获取）
     */
    private static final Long TEST_USER_ID = 1L;
    
    private final WatchHistoryService watchHistoryService;
    
    public UserController(WatchHistoryService watchHistoryService) {
        this.watchHistoryService = watchHistoryService;
    }
    
    /**
     * 获取用户观看记录
     *
     * @param page 页码
     * @param pageSize 每页数量
     * @return 观看记录分页结果
     */
    @GetMapping("/watch-history")
    public Result<PageResult<WatchHistoryVO>> getWatchHistory(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        
        // 在实际环境中，userId应该从用户会话中获取
        PageResult<WatchHistoryVO> result = watchHistoryService.getUserWatchHistory(TEST_USER_ID, page, pageSize);
        return Result.success(result);
    }
} 