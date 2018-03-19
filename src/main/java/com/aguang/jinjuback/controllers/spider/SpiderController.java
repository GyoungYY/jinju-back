package com.aguang.jinjuback.controllers.spider;

import com.aguang.jinjuback.controllers.base.BaseController;
import com.aguang.jinjuback.pojo.Result;
import com.aguang.jinjuback.services.spider.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spider")
public class SpiderController extends BaseController {

    @Autowired
    private SpiderService spiderService;

    /**
     * 内涵段子
     * @return
     */
    @GetMapping("/neihan")
    public Result neihan() {
        Result result = new Result();

        try {
            spiderService.doNeihan();
            result.setSuccess("success");
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(e.getMessage());
        }

        return result;
    }

    /**
     * 百思不得姐
     * @return
     */
    @GetMapping("/budejie")
    public Result budejie() {
        Result result = new Result();

        try {
            spiderService.doBudejie();
            result.setSuccess("success");
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(e.getMessage());
        }

        return result;
    }
}
