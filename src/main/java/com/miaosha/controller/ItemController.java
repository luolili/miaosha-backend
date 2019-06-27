package com.miaosha.controller;

import com.miaosha.controller.viewObject.ItemVO;
import com.miaosha.error.BusinessException;
import com.miaosha.response.CommonReturnType;
import com.miaosha.service.ItemService;
import com.miaosha.service.model.ItemModel;
import com.miaosha.service.model.PromoModel;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller("item")
@RequestMapping("/item")
@CrossOrigin(origins = "*", allowCredentials = "true")//解决前后端分离后跨域问题
public class ItemController extends BaseController{

    @Autowired
    private ItemService itemService;

    //商品列表
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonReturnType getItems() {
        List<ItemModel> itemModels = itemService.listItem();
        List<ItemVO> itemVOList = itemModels.stream().map(itemModel -> {
            ItemVO itemVO = this.convertVOFromModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());

        return CommonReturnType.create(itemVOList);

    }

    //商品详情
   @RequestMapping(value = "/get", method = RequestMethod.GET)
   @ResponseBody
    public CommonReturnType getItem(@RequestParam(name = "id") Integer id) {
        ItemModel itemModel = itemService.getItemById(id);
       ItemVO itemVO = this.convertVOFromModel(itemModel);
       return CommonReturnType.create(itemVO);

   }


    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = BaseController.CONTENT_TYPE_FORMED)
    @ResponseBody
    public CommonReturnType createItem(@RequestParam(name = "title") String title,
                                       @RequestParam(name = "description") String description,
                                       @RequestParam(name = "price") BigDecimal price,
                                       @RequestParam(name = "stock") Integer stock,
                                       @RequestParam(name = "imgUrl") String imgUrl) throws BusinessException {

        //-1 封装要保存的对象
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);

        ItemModel itemModelForReturn = itemService.createModel(itemModel);
        ItemVO itemVO = this.convertVOFromModel(itemModelForReturn);

        return CommonReturnType.create(itemVO);
    }

    //增加秒杀活动
    private ItemVO convertVOFromModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel, itemVO);
        //如果有秒杀的商品
        PromoModel promoModel = itemModel.getPromoModel();
        if (promoModel != null) {
            //即将开始的秒杀或正在进行的秒杀
            itemVO.setPromoStatus(promoModel.getStatus());
            itemVO.setPromoId(promoModel.getId());
            itemVO.setStartTime(promoModel.getStartTime().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));

            itemVO.setPromoPrice(promoModel.getPromoItemPrice());
        }else {
            itemVO.setPromoStatus(0);//无秒杀活动
        }
        return itemVO;
    }
}
