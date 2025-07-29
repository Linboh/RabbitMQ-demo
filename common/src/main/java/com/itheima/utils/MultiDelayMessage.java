package com.itheima.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data                   // 生成 getter/setter、toString、equals 等
@NoArgsConstructor      // 无参构造
@AllArgsConstructor     // 全参构造
public class MultiDelayMessage<T> {

    //消息体
    private T data;

    //记录延迟时间的集合
    private List<Long> delayMillis;

    /**
     * 获取并移除下一个延迟时间
     * @return 队列中的第一个延迟时间
     * delayMillis = [5000L, 10000L, 20000L];
     * Long delay = removeNextDelay();
     * delay = 5000L
     * delayMillis 变成 [10000L, 20000L]
     */
    public Long removeNextDelay(){
        return delayMillis.remove(0);
    }
 
    /**
     * 是否还有下一个延迟时间
     */
    public boolean hasNextDelay(){
        return !delayMillis.isEmpty();
    }
}