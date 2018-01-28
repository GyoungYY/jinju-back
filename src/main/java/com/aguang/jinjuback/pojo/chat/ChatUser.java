package com.aguang.jinjuback.pojo.chat;

import com.alibaba.fastjson.annotation.JSONField;

public class ChatUser {

    /* 用户id： 如果是游客，id由系统自动生成 */
    private String userId;

    /* 区分是否是游客登录 */
    @JSONField(name = "isVisitor")
    private Boolean isVisitor;

    /* 用户名 */
    private String username;

    /* 头像 */
    private String photoUrl;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getIsVisitor() {
        return isVisitor;
    }

    public void setIsVisitor(Boolean visitor) {
        isVisitor = visitor;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
