package com.miaosha.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;//from javax

    public ValidationResult validate(Object bean) {

        ValidationResult validationResult = new ValidationResult();
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);

        //有错误
        if (constraintViolationSet.size() > 0) {
            constraintViolationSet.forEach(constraintViolation ->{
                validationResult.setHasErrors(true);
                String errMsg = constraintViolation.getMessage();
                //获取错误信息的来源字段
                String propertyName = constraintViolation.getPropertyPath().toString();
                validationResult.getErrorMsgMap().put(propertyName, errMsg);
            });
        }

        return validationResult;

    }
    //初始化bean之后被调用
    @Override
    public void afterPropertiesSet() throws Exception {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();//from javax
    }
}

