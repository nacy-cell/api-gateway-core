package com.saka.gateway.authorization;

public interface IAuth {

    boolean validate(String id, String token);

}
