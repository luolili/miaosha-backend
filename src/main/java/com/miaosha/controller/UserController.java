package com.miaosha.controller;

import com.alibaba.druid.util.StringUtils;
import com.miaosha.controller.viewObject.UserVO;
import com.miaosha.error.BusinessException;
import com.miaosha.error.EmBusinessError;
import com.miaosha.response.CommonReturnType;
import com.miaosha.service.UserService;
import com.miaosha.service.model.UserModel;
import com.sun.xml.internal.rngom.parse.host.Base;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller("user")
@RequestMapping("/user")
//@CrossOrigin//springboot解决跨域，无法session共享
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    //用户登录
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = BaseController.CONTENT_TYPE_FORMED)
    @ResponseBody
    public CommonReturnType login(@RequestParam(name = "telphone") String telphone,
                                    @RequestParam(name = "password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //-1 pre check
        if (org.apache.commons.lang3.StringUtils.isEmpty(telphone) ||
                org.apache.commons.lang3.StringUtils.isEmpty(password)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //-2 用户登陆  服务
        UserModel userModel=userService.validateLogin(telphone,this.encodeByMD5(password));
        //-3 将登陆凭证加入到用户登录成功的session
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);

        return CommonReturnType.create(null);
    }
    //用户注册接口
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = BaseController.CONTENT_TYPE_FORMED)
    @ResponseBody
    public CommonReturnType register(@RequestParam(name="telphone") String telphone,
                                     @RequestParam(name="otpCode") String otpCode,
                                     @RequestParam(name="name") String name,
                                     @RequestParam(name="gender") Integer gender,
                                     @RequestParam(name="age") Integer age,
                                     @RequestParam(name="password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {

        //-1 验证otpCode是否对应该用户的号码
        //取出session里面的otpCode
        String inSessionOtpCode = (String)httpServletRequest.getSession().getAttribute(telphone);
        //对用户输入的otpCode和session中的进行比较
        if (!StringUtils.equals(inSessionOtpCode, otpCode)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "短信验证码错误");

        }

        UserModel userModel = new UserModel();
        userModel.setName(name);

        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
        userModel.setTelphone(telphone);
        userModel.setAge(age);
        userModel.setRegisterMode("byphone");
        userModel.setEncrptPassword(this.encodeByMD5(password));
        userService.register(userModel);

        return CommonReturnType.create(null);

    }

    public String encodeByMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //-1 确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String newStr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return newStr;
    }
    //用户提供电话号码 获取otp短信接口
    @RequestMapping(value = "/getotp", method = RequestMethod.POST, consumes = BaseController.CONTENT_TYPE_FORMED)
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name="telphone") String telphone) {
        //-1 按照一定的规则生产验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);

        randomInt+=10000;
        String otpCode = String.valueOf(randomInt);

        //-2 将验证码和号码关联:session
        httpServletRequest.getSession().setAttribute(telphone,otpCode);
        System.out.println(telphone+"---"+otpCode);

        return CommonReturnType.create(null);
    }


    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name="id") Integer id)  {
        UserModel userModel = userService.getUserById(id);
        //不能把userModel传给前台，创建viewObject
        //return userModel;
        if (userModel == null) {
            userModel.setEncrptPassword("22");//模拟空指针
            //throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        UserVO userVO = convertFromUserModel(userModel);
        CommonReturnType returnType = CommonReturnType.create(userVO);
        return returnType;//return vo
    }

    //swagger
    @ApiOperation(value = "根据用户id获取用户", notes = "根据用户id获取用户详情")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Integer")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonReturnType getUserWithSwagger(@PathVariable Integer id) {
        UserModel userModel = userService.getUserById(id);
        //不能把userModel传给前台，创建viewObject
        //return userModel;
        if (userModel == null) {
            userModel.setEncrptPassword("22");//模拟空指针
            //throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        UserVO userVO = convertFromUserModel(userModel);
        CommonReturnType returnType = CommonReturnType.create(userVO);
        return returnType;//return vo
    }
    /**
     * 领域模型转为视图模型
     * @param userModel
     * @return
     */
    private UserVO convertFromUserModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }


}
