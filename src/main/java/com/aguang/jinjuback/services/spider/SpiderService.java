package com.aguang.jinjuback.services.spider;

import com.aguang.jinjuback.dao.admin.AdJinjuDao;
import com.aguang.jinjuback.pojo.constants.AdJinjuSourceConstant;
import com.aguang.jinjuback.services.AreaInfoService;
import com.aguang.jinjuback.utils.DateUtils;
import com.aguang.jinjuback.utils.SpiderUtils;
import com.aguang.jinjuback.utils.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpiderService {

    public final static Logger LOG = LoggerFactory.getLogger(AreaInfoService.class);

    public static final String NEIHAN_URL = "http://www.neihanshequ.com/";

    public static final String BUDEJIE_URL = "http://www.budejie.com/text/";

    @Autowired
    private AdJinjuDao adJinjuDao;

    /**
     * 内涵段子
     */
    public void doNeihan() {
        // 抓取网页
        Document document = SpiderUtils.getHtmlTextByUrl(NEIHAN_URL, "UTF-8");

        // 解析标签
        Elements elements = document.select("h1[class='title']");
        //Elements elements = document.select("div[class='upload-txt  no-mb']");

        if(elements == null)
            return;

        LOG.info(String.format("内涵段子[%s]", elements.size()));

        // 遍历标签元素
        for (Element element : elements) {
            Elements children = element.children();
            String text = children.first().ownText();

            createAdJinju(text, AdJinjuSourceConstant.NEIHAN);
        }
    }

    /**
     * 百思不得姐
     */
    public void doBudejie() {

        for (int i = 1; i <= 10; i++) {
            // 抓取网页
            Document document = SpiderUtils.getHtmlTextByUrl(BUDEJIE_URL + i, "UTF-8");

            // 解析标签
            Elements elements = document.select("div[class='j-r-list-c-desc']");

            if(elements == null)
                return;

            LOG.info(String.format("百思不得姐[%s]", elements.size()));

            // 遍历标签元素
            for (Element element : elements) {
                Elements children = element.children();
                String text = children.first().ownText();

                createAdJinju(text, AdJinjuSourceConstant.BUDEJIE);
            }
        }
    }

    /**
     * 创建后台金句
     * @param content
     */
    public void createAdJinju(String content, Integer source) {
        if(StringUtils.isBlank(content))
            return;

        try {
            Integer count = adJinjuDao.getAdJinjuByContent(content);

            // 如果该数据已存在，则不入库
            if(count!=null && count==0) {
                adJinjuDao.createAdJinju(content, source, DateUtils.getCurrentTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
