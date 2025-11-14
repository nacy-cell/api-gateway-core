package com.saka.gateway.core.authorization;

public interface IAuth {

    boolean validate(String id, String token);

}
