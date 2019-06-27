package com.miaosha.controller;

import com.miaosha.error.BusinessException;
import com.miaosha.error.EmBusinessError;
import com.miaosha.response.CommonReturnType;
import com.miaosha.service.OrderService;
import com.miaosha.service.model.OrderModel;
import com.miaosha.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/order")
@CrossOrigin(origins = "*",allowCredentials = "true")
public class OrderController extends BaseController{


    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpServletRequest servletRequest;

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = BaseController.CONTENT_TYPE_FORMED)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CommonReturnType create(@RequestParam(name="itemId") Integer itemId,
                                   @RequestParam(name="amount") Integer amount ) throws BusinessException {


        Boolean isLogin = (Boolean) servletRequest.getSession().getAttribute("IS_LOGIN");

        if (isLogin==null ||!isLogin.booleanValue()) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN, "用户还未登陆");

        }

        UserModel userModel =(UserModel) servletRequest.getSession().getAttribute("LOGIN_USER");
        orderService.createOrder(userModel.getId(), itemId, amount);

        return CommonReturnType.create(userModel);
    }
}
