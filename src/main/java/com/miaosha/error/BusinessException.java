package com.miaosha.error;

/**
 * 包装器
 */
public class BusinessException extends Exception implements CommonError{

    private CommonError commonError;

    public BusinessException(CommonError commonError) {
        super();
        this.commonError = commonError;
    }

     public BusinessException(CommonError commonError, String erMsg) {
        super();
        this.commonError = commonError;
         this.commonError.setErrMsg(erMsg);
    }


    @Override
    public int getErrCode() {
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.commonError.setErrMsg(errMsg);
        return this;
    }
}
