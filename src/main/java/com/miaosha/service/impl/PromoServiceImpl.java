package com.miaosha.service.impl;

import com.miaosha.dao.PromoDOMapper;
import com.miaosha.dataObject.PromoDO;
import com.miaosha.service.PromoService;
import com.miaosha.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoDOMapper promoDOMapper;
    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        //获取即将进行的秒杀和正在进行的秒杀
        PromoDO promoDO = promoDOMapper.selectByItemId(itemId);

        PromoModel promoModel = convertFromDataObject(promoDO);

        if (promoModel == null) {
            return null;
        }
        if (promoModel.getStartTime().isAfterNow()) {
            promoModel.setStatus(1);
        } else if (promoModel.getStartTime().isBeforeNow()) {
            promoModel.setStatus(3);
        }else {
            promoModel.setStatus(2);
        }



        return promoModel;
    }

    private PromoModel convertFromDataObject(PromoDO promoDO) {
        if (promoDO == null) {
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO,promoModel);
        promoModel.setPromoItemPrice(new BigDecimal(promoDO.getPromoItemPrice()));
        promoModel.setStartTime(new DateTime(promoDO.getStartTime()));
        promoModel.setEndTime(new DateTime(promoDO.getEndTime()));
        return promoModel;



    }
}
