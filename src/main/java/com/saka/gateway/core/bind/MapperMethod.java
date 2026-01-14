package com.saka.gateway.core.bind;


import com.saka.gateway.core.mapping.HttpCommandType;
import com.saka.gateway.core.session.Configuration;
import com.saka.gateway.core.session.GatewaySession;

import java.lang.reflect.Method;
import java.util.Map;

public class MapperMethod {

    private final String methodName;
    private final HttpCommandType command;

    public MapperMethod(String uri, Method method, Configuration configuration) {
        this.methodName = configuration.getHttpStatement(uri).getMethodName();
        this.command = configuration.getHttpStatement(uri).getHttpCommandType();
    }

    public Object execute(GatewaySession session, Map<String, Object> params) {
        Object result = null;
        switch (command) {
            case GET:
                result = session.get(methodName, params);
                break;
            case POST:
                result = session.post(methodName, params);
                break;
            case PUT:
                result = session.put(methodName, params);
                break;
            case DELETE:
                result = session.delete(methodName, params);
                break;
            default:
                throw new RuntimeException("Unknown execution method for: " + command);
        }
        return result;
    }




}
