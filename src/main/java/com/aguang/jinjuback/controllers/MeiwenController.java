package com.aguang.jinjuback.controllers;

import com.aguang.jinjuback.controllers.base.BaseController;
import com.aguang.jinjuback.model.Meiwen;
import com.aguang.jinjuback.pojo.Result;
import com.aguang.jinjuback.services.MeiwenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/meiwen")
public class MeiwenController extends BaseController {

    @Autowired
    private MeiwenService meiwenService;

    /**
     * 查询美文列表
     * @param pageIndex
     * @param pageSize
     * @return
     */
//    @GetMapping("/list")
//    public Result list(@RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
//                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
//        return jinjuService.getJinjuList(pageIndex, pageSize, getUserId());
//    }

    /**
     * 创建美文
     * @param meiwen
     * @return
     */
    @PostMapping("/create")
    public Result create(@RequestBody  @Valid Meiwen meiwen, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            Result result = new Result();
            result.setError(null, bindingResult.getAllErrors().get(0).getDefaultMessage());
            return result;
        }
        return meiwenService.createMeiwen(meiwen, getUserId());
    }

}
