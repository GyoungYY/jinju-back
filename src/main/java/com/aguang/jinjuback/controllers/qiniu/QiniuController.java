package com.aguang.jinjuback.controllers.qiniu;

import com.aguang.jinjuback.controllers.base.BaseController;
import com.aguang.jinjuback.pojo.Result;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 七牛Controller
 * @author li.kai
 * @since 2018/01/14
 */
@RestController
@RequestMapping("/qiniu")
public class QiniuController extends BaseController {

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    /**
     * 获取七牛云的上传token
     * @return
     */
    @GetMapping("/uploadToken")
    public Result uploadToken() {
        Result result = new Result();

        Auth auth = Auth.create(accessKey, secretKey);
        String uploadToken = auth.uploadToken(bucket, null);

        result.setSuccess(uploadToken, "query success");

        return result;
    }
}
