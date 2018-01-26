package com.aguang.jinjuback.controllers.chat;

import com.aguang.jinjuback.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

    /**
     * 获取所有在线用户信息
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public Result list(@RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return null;
    }

}
