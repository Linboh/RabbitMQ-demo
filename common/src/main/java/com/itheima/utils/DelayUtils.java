package com.itheima.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class DelayUtils {

    /**
     * 生成一组递增延迟时间集合，累加等于 totalMinutes（分钟）
     *
     * @param totalMinutes 总延迟时间（单位：分钟）
     * @param steps 拆分步数（建议 5~20）
     * @return 延迟时间集合（单位：毫秒）
     */
    public static List<Long> generateDelayStepsByMinutes(int totalMinutes, int steps) {
        List<Long> delays = new ArrayList<>();
        if (steps <= 0 || totalMinutes <= 0) return delays;

        long totalMillis = totalMinutes * 60L * 1000L; // 分钟转毫秒
        long base = totalMillis / (steps * (steps + 1) / 2); // 等差数列求首项

        for (int i = 1; i <= steps; i++) {
            delays.add(base * i);
        }

        return delays;
    }
}
