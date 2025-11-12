package com.saka.gateway.excuter;

import com.saka.gateway.excuter.result.GatewayResult;
import com.saka.gateway.mapping.HttpStatement;

import java.util.Map;

public interface Executor {

    GatewayResult exec(HttpStatement httpStatement, Map<String, Object> params) throws Exception;

}
