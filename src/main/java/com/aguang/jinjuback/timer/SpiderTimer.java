package com.aguang.jinjuback.timer;

import com.aguang.jinjuback.services.spider.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpiderTimer {

    @Autowired
    private SpiderService spiderService;

//    @Scheduled(cron = "0 0 2-5 * * ? *")
//    public void timer() {
//
//        //获取当前时间
//        LocalDateTime localDateTime =LocalDateTime.now();
//        System.out.println("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//
////        spiderService.doNeihan();
//    }
}
