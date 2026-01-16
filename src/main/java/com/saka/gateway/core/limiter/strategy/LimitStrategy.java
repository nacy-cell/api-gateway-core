package com.saka.gateway.core.limiter.strategy;

public interface LimitStrategy {

    boolean tryAcquire();

}
