package com.saka.gateway.core.excuter.result;

import com.alibaba.fastjson2.JSON;

public class SessionResult {

    private String code;
    private String info;
    private String bizCode;
    private String bizInfo;
    private Object data;

    protected SessionResult(String code, String info, String bizCode,String bizInfo,Object data) {
        this.code = code;
        this.info = info;
        this.data = data;
        this.bizCode = bizCode;
        this.bizInfo = bizInfo;
    }

    public static SessionResult buildSuccess(Object data){
        Response targetDTO = JSON.parseObject(JSON.toJSONString(data), Response.class);
        return new SessionResult("0000","调用成功", targetDTO.getCode(),targetDTO.getInfo(),targetDTO.getData());
    }

    public static SessionResult buildError(Object data){
        Response targetDTO = JSON.parseObject(JSON.toJSONString(data), Response.class);
        return new SessionResult("0001","调用失败", targetDTO.getCode(),targetDTO.getInfo(),targetDTO.getData());
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


    public String getBizInfo() {
        return bizInfo;
    }

    public void setBizInfo(String bizInfo) {
        this.bizInfo = bizInfo;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }
}
