package com.saka.gateway.core.excuter;

import com.saka.gateway.core.datasource.Connection;
import com.saka.gateway.core.session.Configuration;

public class SimpleExecutor extends BaseExecutor {

    public SimpleExecutor(Configuration configuration, Connection connection) {
        super(configuration, connection);
    }

    @Override
    protected Object doExec(String methodName, String[] parameterTypes, Object[] args) {
        return connection.execute(methodName, parameterTypes, new String[]{"ignore"}, args);
    }

}
