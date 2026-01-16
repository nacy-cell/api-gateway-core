package com.saka.gateway.core.limiter;

public enum LimiterEnum {

    /** 计数器限流 **/
    COUNT_LIMITER(1,"计数器限流"),

    /** 漏桶限流 **/
    LEAKY_BUCKET_LIMITER(2,"漏桶限流"),

    /** 令牌桶限流 **/
    TOKEN_BUCKET_LIMITER(3,"令牌桶限流");

    private Integer code;
    private String msg;

    LimiterEnum(Integer code, String desc) {
        this.code = code;
        this.msg = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String desc) {
        this.msg = desc;
    }

    public static LimiterEnum getByCode(Integer code){
        LimiterEnum[] values = LimiterEnum.values();
        for(LimiterEnum limiterEnum:values){
            if(limiterEnum.getCode().equals(code)){
                return limiterEnum;
            }
        }
        return LimiterEnum.TOKEN_BUCKET_LIMITER;
    }

}

