package com.miaosha.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ValidationResult {

    //校验结果是否有错,给默认值，防止空指针
    private boolean hasErrors=false;
    
    //存放错误信息的map
    Map<String, String> errorMsgMap=new HashMap<>();

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }

    //获取所以的错误信息
    public String getErrorMsg() {
        errorMsgMap.values().toArray();
        String result = StringUtils.join(errorMsgMap.values().toArray(), ", ");//from apache
        return result;

    }
}
