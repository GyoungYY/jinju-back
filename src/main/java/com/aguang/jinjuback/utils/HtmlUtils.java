package com.aguang.jinjuback.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

public class HtmlUtils {

    /**
     * 根据url从网络获取网页文本
     * @param url
     * @param charsetName 字符集编码
     * @return
     */
    public static Document getHtmlTextByUrl(String url, String charsetName) {
        Document doc = null;
        try {
            //doc = Jsoup.connect(url).timeout(5000000).get();
            int i = (int)(Math.random() * 1000); //做一个随机延时，防止网站屏蔽
//            while (i != 0) {
//                i--;
//            }
            try {
                Thread.sleep(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            doc = Jsoup.connect(url).data("query", "Java").userAgent("Mozilla").cookie("auth", "token").timeout(300000).post();

            doc = Jsoup.parse(new URL(url).openStream(), charsetName, url);


        } catch (IOException e) {
            e.printStackTrace();
            try {
                doc = Jsoup.connect(url).timeout(5000000).get();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return doc;
    }


    /**
     * 根据元素属性获取某个元素内的elements列表
     * @param doc
     * @param className
     * @return
     */
    public static Elements getEleByClass(Document doc, String className) {
        //<tr class="provincetr">
        Elements elements = null;
        elements = doc.select(className); //     tr.provincetr
        return elements; //此处返回的就是所有的tr集合
    }
}
