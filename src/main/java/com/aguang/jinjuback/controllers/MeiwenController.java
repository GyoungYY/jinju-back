package com.aguang.jinjuback.controllers;

import com.aguang.jinjuback.controllers.base.BaseController;
import com.aguang.jinjuback.model.Meiwen;
import com.aguang.jinjuback.model.MeiwenComment;
import com.aguang.jinjuback.pojo.Result;
import com.aguang.jinjuback.services.MeiwenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/meiwen")
public class MeiwenController extends BaseController {

    @Autowired
    private MeiwenService meiwenService;

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

    /**
     * 查询美文列表
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public Result list(@RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return meiwenService.getMeiwenList(pageIndex, pageSize, getUserId());
    }

    /**
     * 获取美文信息
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public Result getMeiwen(@PathVariable("id") Integer id) {
        return meiwenService.getMeiwen(id, getUserId());
    }

    /**
     * 创建评论
     * @return
     */
    @PostMapping("/comment/create")
    public Result createComment(@RequestBody  @Valid MeiwenComment comment, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            Result result = new Result();
            result.setError(null, bindingResult.getAllErrors().get(0).getDefaultMessage());
            return result;
        }
        return meiwenService.createComment(comment, getUserId());
    }

    /**
     * 获取评论列表
     * @return
     */
    @GetMapping("/comment/list")
    public Result listComment(@RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
                              @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                              @RequestParam("meiwenId") Integer meiwenId,
                              @RequestParam(value = "parentId", required = false) Integer parentId) {

        return meiwenService.listComment(meiwenId, parentId, pageIndex, pageSize);
    }
}
