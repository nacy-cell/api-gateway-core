package com.saka.gateway.core.excuter;

import com.saka.gateway.core.excuter.result.SessionResult;
import com.saka.gateway.core.mapping.HttpStatement;

import java.util.Map;

public interface Executor {

    SessionResult exec(HttpStatement httpStatement, Map<String, Object> params) throws Exception;

}
