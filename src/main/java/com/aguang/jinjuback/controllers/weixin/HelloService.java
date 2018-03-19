package com.aguang.jinjuback.controllers.weixin;

import com.aguang.jinjuback.controllers.weixin.pojo.WxAccessToken;
import com.aguang.jinjuback.controllers.weixin.pojo.WxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {

    private String jsonObj = "{\n" +
            "    \"button\": [\n" +
            "        {\n" +
            "            \"name\": \"Test\", \n" +
            "            \"sub_button\": [\n" +
            "                {\n" +
            "                    \"type\": \"click\", \n" +
            "                    \"name\": \"Test\", \n" +
            "                    \"key\": \"super_rebate\"\n" +
            "                }, \n" +
            "                {\n" +
            "                    \"type\": \"click\", \n" +
            "                    \"name\": \"Test\", \n" +
            "                    \"key\": \"wei_shop\"\n" +
            "                }\n" +
            "            ]\n" +
            "        }, \n" +
            "        {\n" +
            "            \"name\": \"Test2\", \n" +
            "            \"sub_button\": [\n" +
            "                {\n" +
            "                    \"type\": \"view\", \n" +
            "                    \"name\": \"Test\", \n" +
            "                    \"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2287dbde2e640908&redirect_uri=http://bt18088883.iok.la/page/quan_tmallchaoshi&response_type=code&scope=snsapi_userinfo&state=12580&connect_redirect=1#wechat_redirect\"\n" +
            "                }, \n" +
            "                {\n" +
            "                    \"type\": \"click\", \n" +
            "                    \"name\": \"Test\", \n" +
            "                    \"key\": \"didi_discount_coupon\"\n" +
            "                }, \n" +
            "                {\n" +
            "                    \"type\": \"click\", \n" +
            "                    \"name\": \"Test5\", \n" +
            "                    \"key\": \"jd_discount_coupon\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"type\": \"view\", \n" +
            "            \"name\": \"我的\", \n" +
            "            \"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2287dbde2e640908&redirect_uri=http://47.100.177.183/&response_type=code&scope=snsapi_userinfo&state=12580&connect_redirect=1#wechat_redirect\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    @Autowired
    RestTemplate restTemplate;

    private String appID = "wx2287dbde2e640908";
    private String appsecret = "95f8e3156184945420033ed8889ca37c";

    /**
     * 获取token
     * @return
     */
    public String getToken() {
        WxAccessToken token = restTemplate.getForObject("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appID + "&secret=" + appsecret, WxAccessToken.class);
        System.out.println(token);
        return token.getAccess_token();
    }

    /**
     * 创建菜单
     * @return
     */
    public WxResult createMenu() {

        String accessToken = getToken();

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

//        JSONObject jsonObj = JSONObject.fromObject(params);
//        HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);

        HttpEntity<String> formEntity = new HttpEntity<>(jsonObj, headers);

        WxResult wxResult = restTemplate.postForObject("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken, formEntity, WxResult.class);
        return wxResult;
    }

    /**
     * 获取菜单
     * @return
     */
    public String getMenu() {
        String menu = restTemplate.getForObject("https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + getToken(), String.class);
        System.out.println(menu);
        return menu;
    }

}