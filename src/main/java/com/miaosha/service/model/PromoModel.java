package com.miaosha.service.model;

import org.joda.time.DateTime;

import java.math.BigDecimal;

public class PromoModel {

    private Integer id;

    //秒杀活动状态:1:未开始 2.正在进行 3 已经结束
    private Integer status;
    //秒杀活动名称
    private String promoName;

    //活动开始时间
    private DateTime startTime;
    private DateTime endTime;
    //描述活动对于的商品id
    private Integer itemId;

    private BigDecimal promoItemPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getPromoItemPrice() {
        return promoItemPrice;
    }

    public void setPromoItemPrice(BigDecimal promoItemPrice) {
        this.promoItemPrice = promoItemPrice;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }
}
