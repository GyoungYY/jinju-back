package com.aguang.jinjuback.timer;

import com.aguang.jinjuback.services.AreaInfoService;
import com.aguang.jinjuback.services.spider.SpiderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 爬虫定时器
 */
@Component
public class SpiderTimer {

    public final static Logger logger = LoggerFactory.getLogger(AreaInfoService.class);

    @Autowired
    private SpiderService spiderService;

    /**
     * 定时每天18点
     */
    @Scheduled(cron = "0 27 18 * * ?")
    public void timer() {

        //获取当前时间
        LocalDateTime localDateTime =LocalDateTime.now();
        System.out.println("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        logger.info("抓取数据.....start..........");

        // 5次neihan
        for (int i = 0; i < 5; i++) {
            spiderService.doNeihan();
        }

        // 1次budejie
        spiderService.doBudejie();


        logger.info("抓取数据.....end..........");
    }
}
