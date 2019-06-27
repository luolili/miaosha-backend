package com.miaosha.service;

import com.miaosha.error.BusinessException;
import com.miaosha.service.model.UserModel;

public interface UserService {

      UserModel getUserById(Integer id);

      //注册用户
      void register(UserModel userModel) throws BusinessException;

      //验证登陆信息
      UserModel validateLogin(String telphone, String encrptPassword) throws BusinessException;

}
