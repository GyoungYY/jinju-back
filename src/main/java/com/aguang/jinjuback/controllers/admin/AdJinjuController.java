package com.aguang.jinjuback.controllers.admin;

import com.aguang.jinjuback.controllers.base.BaseController;
import com.aguang.jinjuback.pojo.Result;
import com.aguang.jinjuback.pojo.admin.AdJinjuInfo;
import com.aguang.jinjuback.pojo.common.PageInfo;
import com.aguang.jinjuback.services.admin.AdJinjuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/admin/jinju")
public class AdJinjuController extends BaseController {

    @Autowired
    private AdJinjuService jinjuService;

    @Autowired
    private StringRedisTemplate redisTemplate;

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
            Object admin_jinju_source = redisTemplate.boundHashOps("ADMIN_JINJU_SOURCE").get("1");

            Object admin_jinju_source1 = redisTemplate.opsForHash().get("ADMIN_JINJU_SOURCE", "1");

            redisTemplate.opsForValue().set("key","value");

            Object name = redisTemplate.opsForValue().get("key");

            Set keys = redisTemplate.keys("*");

            for (Object a : keys ) {
                if(a.toString().contains("key"))

                    redisTemplate.delete(a.toString());
            }


            PageInfo<AdJinjuInfo> list = jinjuService.getJinjuList(pageIndex, pageSize);

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
    @PostMapping("/audit")
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

    /**
     * 驳回后台金句
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public Result delete(@RequestParam(value = "id") int id) {
        Result result = new Result();

        try {
            jinjuService.delete(id);

            result.setSuccess("删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(e.getMessage());
        }

        return result;
    }

}
