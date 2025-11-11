package com.saka.gateway.session.defaults;

import com.saka.gateway.session.Configuration;
import com.saka.gateway.session.GatewaySession;
import com.saka.gateway.session.GatewaySessionFactory;

public class DefaultGatewaySessionFactory implements GatewaySessionFactory {

    private final Configuration configuration;

    public DefaultGatewaySessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public GatewaySession openSession() {
        return new DefaultGatewaySession(configuration);
    }

}
