package com.miaosha.service.model;

import java.math.BigDecimal;

public class OrderModel {

    //账单号：由一定的规则生成的string
    private String id;

    //购买用户的id
    private Integer userId;
    //购买的商品id
    private Integer itemId;

    //购买的数量
    private Integer amount;
    //单价
    private BigDecimal itemPrice;
    //总价
    private BigDecimal orderPrice;

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }
}
