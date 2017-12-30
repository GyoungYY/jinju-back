package com.aguang.jinjuback.controllers;

import com.aguang.jinjuback.model.Jinju;
import com.aguang.jinjuback.pojo.JinjuInfo;
import com.aguang.jinjuback.pojo.Result;
import com.aguang.jinjuback.services.JinjuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/jinju")
public class JinjuController extends BaseController {

    @Autowired
    private JinjuService jinjuService;

    /**
     * 查询金句列表
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public Result list(@RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return jinjuService.getJinjuList(pageIndex, pageSize, getUserId());
    }

    /**
     * 创建金句
     * @param jinju
     * @return
     */
    @PostMapping("/create")
    public Result create(@RequestBody  @Valid Jinju jinju, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            Result result = new Result();
            result.setError(null, bindingResult.getAllErrors().get(0).toString());
            return result;
        }
        return jinjuService.createJinju(jinju, getUserId());
    }

    /**
     * 获取金句信息
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public JinjuInfo getJinju(@PathVariable("id") Integer id) {
        return jinjuService.getJinju(id, getUserId());
    }

    /**
     * 赞
     * @return
     */
    @PostMapping("/upVote/{jijuId}")
    public Result upVote(@PathVariable("jijuId") Integer jijuId, @RequestParam("type") String type) {
        return jinjuService.upVote(jijuId, type, getUserId());
    }

    /**
     * 踩
     * @return
     */
    @PostMapping("/downVote/{jijuId}")
    public Result downVote(@PathVariable("jijuId") Integer jijuId, @RequestParam("type") String type) {
        return jinjuService.downVote(jijuId, type, getUserId());
    }

    /**
     * 收藏
     * @return
     */
    @PostMapping("/collect/{jijuId}")
    public Result collect(@PathVariable("jijuId") Integer jijuId) {
        return jinjuService.collect(jijuId, getUserId());
    }

    /**
     * 取消收藏
     * @return
     */
    @PostMapping("/cancelCollect/{jijuId}")
    public Result cancelCollect(@PathVariable("jijuId") Integer jijuId) {
        return jinjuService.cancelCollect(jijuId, getUserId());
    }
}
