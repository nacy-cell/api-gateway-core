package com.saka.gateway.core.socket.aggrement;

import com.saka.gateway.core.excuter.result.SessionResult;

public class GatewayResultMessage {

    private String code;
    private String info;
    String bizCode;
    String bizInfo;
    private Object data;

    protected GatewayResultMessage(String code, String info, String bizCode,String bizInfo,Object data) {
        this.code = code;
        this.info = info;
        this.bizCode = bizCode;
        this.bizInfo = bizInfo;
        this.data = data;
    }

    public static GatewayResultMessage buildSuccess(SessionResult data) {
        return new GatewayResultMessage(AgreementConstants.ResponseCode._200.getCode(),
                AgreementConstants.ResponseCode._200.getInfo(),
                data.getBizCode(),
                data.getBizInfo(),
                data.getData());
    }

    public static GatewayResultMessage buildError(String code, String info) {
        return new GatewayResultMessage(code, info, null, null, null);
    }

    public static GatewayResultMessage buildError(SessionResult data) {
        return new GatewayResultMessage(AgreementConstants.ResponseCode._405.getCode(),
                AgreementConstants.ResponseCode._405.getInfo(),
                data.getBizCode(),
                data.getBizInfo(),
                data.getData());
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public Object getData() {
        return data;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getBizInfo() {
        return bizInfo;
    }

    public void setBizInfo(String bizInfo) {
        this.bizInfo = bizInfo;
    }
}
