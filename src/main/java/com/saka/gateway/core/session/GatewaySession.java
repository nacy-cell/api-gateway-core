package com.saka.gateway.core.session;

import com.saka.gateway.core.bind.IGenericReference;

import java.util.Map;

public interface GatewaySession {

    Object get(String methodName, Map<String, Object> params);

    Object post(String methodName, Map<String, Object> params);

    IGenericReference getMapper();

    Configuration getConfiguration();

    Object put(String methodName, Map<String, Object> params);

    Object delete(String methodName, Map<String, Object> params);
}

