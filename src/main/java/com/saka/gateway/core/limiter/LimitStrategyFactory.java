package com.saka.gateway.core.limiter;

import com.saka.gateway.core.limiter.strategy.LimitStrategy;
import com.saka.gateway.core.limiter.strategy.TokenBucketLimitStrategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LimitStrategyFactory {

    private final static Map<String, LimitStrategy> limitStrategyMap = new ConcurrentHashMap<>();

    public static LimitStrategy createLimitStrategy(String methodId, int type, int qps) {
        String key = methodId + "-" + type + "-" +qps;
        return limitStrategyMap.computeIfAbsent(key, k -> {
            LimiterEnum limiterEnum = LimiterEnum.getByCode(type);
            switch (limiterEnum) {
                case TOKEN_BUCKET_LIMITER:
                    return new TokenBucketLimitStrategy(qps);
                default:
                    return new TokenBucketLimitStrategy(qps);
            }
        });
    }

    public static LimitStrategy createSimpleLimitStrategy(int type, int qps) {
        LimiterEnum limiterEnum = LimiterEnum.getByCode(type);
        switch (limiterEnum) {
            case TOKEN_BUCKET_LIMITER:
                return new TokenBucketLimitStrategy(qps);
        }
        return new TokenBucketLimitStrategy(qps);
    }
}
