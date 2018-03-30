package com.aguang.jinjuback.controllers.shop;

import com.aguang.jinjuback.controllers.base.BaseController;
import com.aguang.jinjuback.model.Product;
import com.aguang.jinjuback.pojo.Result;
import com.aguang.jinjuback.pojo.common.PageInfo;
import com.aguang.jinjuback.services.shop.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 商品Controller
 */
@RestController
@RequestMapping("/product")
public class ProductController extends BaseController {

    public final static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    /**
     * 列表
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public Result list(@RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Result result = new Result();

        try {
            PageInfo<Product> list = productService.list(pageIndex, pageSize, getUserId());
            result.setSuccess(list, "数据获取成功!");
        } catch (Exception ex) {
            logger.error("错误", ex);
            result.setError("操作失败");
        }
        return result;
    }

    /**
     * 创建
     * @param product
     * @param bindingResult
     * @return
     */
    @PostMapping("/create")
    public Result create(@RequestBody @Valid Product product, BindingResult bindingResult) {
        Result result = new Result();

        try {
            if(bindingResult.hasErrors()) {
                result.setError(bindingResult.getAllErrors().get(0).getDefaultMessage());
                return result;
            }

            productService.create(product, getUserId());
            result.setSuccess("操作成功");
        } catch (Exception ex) {
            logger.error("错误", ex);
            result.setError("操作失败");
        }
        return result;
    }

    /**
     * 审核
     * @param productId
     * @return
     */
    @PostMapping("/audit")
    public Result audit(@RequestParam(value = "productId") Integer productId) {
        Result result = new Result();

        try {
            productService.audit(productId, getUserId());
            result.setSuccess("操作成功");
        } catch (Exception ex) {
            logger.error("错误", ex);
            result.setError("操作失败");
        }
        return result;
    }

    /**
     * 删除
     * @param productId
     * @return
     */
    @PostMapping("/delete")
    public Result delete(@RequestParam(value = "productId") Integer productId) {
        Result result = new Result();

        try {
            productService.delete(productId, getUserId());
            result.setSuccess("操作成功");
        } catch (Exception ex) {
            logger.error("错误", ex);
            result.setError("操作失败");
        }
        return result;
    }

}
