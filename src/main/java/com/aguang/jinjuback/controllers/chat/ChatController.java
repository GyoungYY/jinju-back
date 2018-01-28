package com.aguang.jinjuback.controllers.chat;

import com.aguang.jinjuback.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    /* 游客id */
    public static Integer visitorId = 10000;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 获取下一个id
     * @return
     */
    private Integer nextVisitorId() {

        if(visitorId == 99999) {
            visitorId = 10000;
        } else {
            visitorId++;
        }
        return visitorId;
    }

    /**
     * 获取游客Id
     * @return
     */
    @GetMapping("/getVisitorId")
    public Result getVisitorId() {
        Result result = new Result();

        result.setSuccess(nextVisitorId(), "获取成功");

        return result;
    }

    /**
     * 获取历史消息
     * @return
     */
    @GetMapping("/getHistoryMessage")
    public Result getHistoryMessage(@RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        Result result = new Result();

        Jedis jedis = null;
        List<String> chatMessages = null;

        try {
            jedis = jedisPool.getResource();
            chatMessages = jedis.lrange("chat", 0, limit);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (jedis != null) {
                //关闭连接
                jedis.close();
            }
        }

        result.setSuccess(chatMessages, "获取成功");
        return result;
    }
}
