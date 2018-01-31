package com.aguang.jinjuback.controllers.chat;

import com.aguang.jinjuback.pojo.Result;
import com.aguang.jinjuback.pojo.chat.ChatMessage;
import com.aguang.jinjuback.services.chat.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    /**
     * 获取游客Id
     * @return
     */
    @GetMapping("/getVisitorId")
    public Result getVisitorId() {
        Result result = new Result();

        try {
            result.setSuccess(chatService.nextVisitorId(), "获取成功");
        } catch (Exception e) {
            result.setError("获取失败");
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取历史消息
     * @return
     */
    @GetMapping("/getHistoryMessage")
    public Result getHistoryMessage(@RequestParam(value = "id", required = false) Integer id,
                                    @RequestParam(value = "limit", defaultValue = "20") Integer limit) {

        Result result = new Result();

        if(limit <1) {
            result.setError("limit必须大于0");
            return result;
        }

        try {
            List<ChatMessage>  chatMessages = chatService.getHistoryMessage(id, limit);
            result.setSuccess(chatMessages, "获取成功");
        } catch (Exception ex) {
            result.setError("获取失败");
            ex.printStackTrace();
        }

        return result;
    }
}
