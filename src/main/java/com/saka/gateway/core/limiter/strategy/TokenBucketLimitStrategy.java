package com.saka.gateway.core.limiter.strategy;

import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenBucketLimitStrategy extends AbstractLimitStrategy{

    private final Logger logger = LoggerFactory.getLogger(TokenBucketLimitStrategy.class);

    private final RateLimiter rateLimiter;

    public TokenBucketLimitStrategy(int qps) {
        super(qps);
        rateLimiter = RateLimiter.create(qps);
    }

    @Override
    public synchronized boolean doTryAcquire() {
        boolean isPass = rateLimiter.tryAcquire();
        if(isPass){
            logger.info("令牌桶算法-请求成功,当前qps is {}",qps);
        }
        else{
            logger.info("令牌桶算法-请求失败");
        }
        return isPass;
    }

}