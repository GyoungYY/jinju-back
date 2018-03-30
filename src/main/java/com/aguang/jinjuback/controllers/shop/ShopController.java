package com.aguang.jinjuback.controllers.shop;

import com.aguang.jinjuback.controllers.base.BaseController;
import com.aguang.jinjuback.model.Shop;
import com.aguang.jinjuback.pojo.Result;
import com.aguang.jinjuback.pojo.common.PageInfo;
import com.aguang.jinjuback.services.shop.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 店铺Controller
 */
@RestController
@RequestMapping("/shop")
public class ShopController extends BaseController {

    public final static Logger logger = LoggerFactory.getLogger(ShopController.class);

    @Autowired
    private ShopService shopService;

    /**
     * 查询列表
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public Result list(@RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Result result = new Result();

        try {
            PageInfo<Shop> list = shopService.list(pageIndex, pageSize, getUserId());
            result.setSuccess(list, "数据获取成功!");
        } catch (Exception ex) {
            logger.error("错误", ex);
            result.setError("操作失败");
        }
        return result;
    }

    /**
     * 创建店铺
     * @param shop
     * @param bindingResult
     * @return
     */
    @PostMapping("/create")
    public Result create(@RequestBody  @Valid Shop shop, BindingResult bindingResult) {
        Result result = new Result();

        try {
            if(bindingResult.hasErrors()) {
                result.setError(bindingResult.getAllErrors().get(0).getDefaultMessage());
                return result;
            }

            shopService.create(shop, getUserId());
            result.setSuccess("操作成功");
        } catch (Exception ex) {
            logger.error("错误", ex);
            result.setError("操作失败");
        }
        return result;
    }

    /**
     * 删除
     * @param shopId
     * @return
     */
    @PostMapping("/delete")
    public Result delete(@RequestParam(value = "shopId") Integer shopId) {
        Result result = new Result();

        try {
            shopService.delete(shopId, getUserId());
            result.setSuccess("操作成功");
        } catch (Exception ex) {
            logger.error("错误", ex);
            result.setError("操作失败");
        }
        return result;
    }
}
