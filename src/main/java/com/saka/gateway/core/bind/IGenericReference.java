package com.saka.gateway.core.bind;

import com.saka.gateway.core.excuter.result.SessionResult;

import java.util.Map;

public interface IGenericReference {

    SessionResult $invoke(Map<String, Object> params);

}
