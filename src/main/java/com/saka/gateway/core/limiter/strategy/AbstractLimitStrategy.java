package com.saka.gateway.core.limiter.strategy;

public abstract class AbstractLimitStrategy implements LimitStrategy {

    protected final int qps;

    AbstractLimitStrategy(int qps) {
        this.qps = qps;
    }

    @Override
    public boolean tryAcquire() {
        return doTryAcquire();
    }

    protected abstract boolean doTryAcquire();

}
