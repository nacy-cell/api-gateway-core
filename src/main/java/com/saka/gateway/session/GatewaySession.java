package com.saka.gateway.session;

import com.saka.gateway.bind.IGenericReference;

public interface GatewaySession {

    Object get(String uri, Object parameter);

    IGenericReference getMapper(String uri);

    Configuration getConfiguration();

}
