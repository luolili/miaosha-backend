package com.miaosha.error;

public enum EmBusinessError implements CommonError{
    //以20000开头的信息为用户信息错误
    USER_NOT_EXIST(10001, "用户不存在"),
    UNKNOWN_ERROR(10002, "未知错误"),
    
    //通用错误码
    PARAMETER_VALIDATION_ERROR(200001, "参数不合法"),
    USER_LOGIN_FAIL(200002, "用户手机号或密码不正确"),
    USER_NOT_LOGIN(200003, "用户还未登陆"),

    //3000开头是交易信息错误
    STOCK_NOT_ENOUGH(300001, "库存不足"),
    ;

    private int errCode;
    private String errMsg;

    EmBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;//定制错误信息
        return this;
    }
}
