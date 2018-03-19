package com.aguang.jinjuback.controllers.admin;

import com.aguang.jinjuback.controllers.base.BaseController;
import com.aguang.jinjuback.pojo.Result;
import com.aguang.jinjuback.pojo.admin.AdJinjuInfo;
import com.aguang.jinjuback.services.admin.AdJinjuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/jinju")
public class AdJinjuController extends BaseController {

    @Autowired
    private AdJinjuService jinjuService;

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
            List<AdJinjuInfo> list = jinjuService.getJinjuList(pageIndex, pageSize);

            result.setSuccess(list, "success");
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(e.getMessage());
        }

        return result;
    }

    /**
     * 审核后台金句
     * @param id
     * @return
     */
    @GetMapping("/audit")
    public Result audit(@RequestParam(value = "id") int id, @RequestParam(value = "type") int type) {
        Result result = new Result();

        try {
            jinjuService.auditJinju(id, type);

            result.setSuccess("审核成功!");
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(e.getMessage());
        }

        return result;
    }

}
