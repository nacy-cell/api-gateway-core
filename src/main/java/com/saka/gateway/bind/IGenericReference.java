package com.saka.gateway.bind;

import com.saka.gateway.excuter.result.SessionResult;

import java.util.Map;

public interface IGenericReference {

    SessionResult $invoke(Map<String, Object> params);

}
