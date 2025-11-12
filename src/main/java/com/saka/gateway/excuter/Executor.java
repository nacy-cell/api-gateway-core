package com.saka.gateway.excuter;

import com.saka.gateway.excuter.result.SessionResult;
import com.saka.gateway.mapping.HttpStatement;

import java.util.Map;

public interface Executor {

    SessionResult exec(HttpStatement httpStatement, Map<String, Object> params) throws Exception;

}
