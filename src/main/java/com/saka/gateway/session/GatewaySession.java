package com.saka.gateway.session;

import com.saka.gateway.bind.IGenericReference;

public interface GatewaySession {

    Object get(String methodName, Object parameter);

    IGenericReference getMapper();

    Configuration getConfiguration();


}
