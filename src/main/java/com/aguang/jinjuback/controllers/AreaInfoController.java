package com.aguang.jinjuback.controllers;

import com.aguang.jinjuback.model.AreaInfo;
import com.aguang.jinjuback.pojo.Result;
import com.aguang.jinjuback.services.AreaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 地区信息Controller
 * @author li.kai
 * @since 2018/01/03
 */
@RestController
@RequestMapping("/area")
public class AreaInfoController extends BaseController {

    @Autowired
    private AreaInfoService areaInfoService;

    /**
     * 根据地区编码查询其下级地区列表
     * @param parentCode
     * @return
     */
    @GetMapping("/list")
    public Result listByParentCode(@RequestParam(value = "parentCode", defaultValue = "0") String parentCode) {
        Result result = new Result();

        List<AreaInfo> list = areaInfoService.listByParentCode(parentCode);

        result.setSuccess(list, "query success");
        return result;
    }
}
