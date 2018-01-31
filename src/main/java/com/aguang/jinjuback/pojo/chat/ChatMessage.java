package com.aguang.jinjuback.pojo.chat;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class ChatMessage {

    private Integer id;

    private String userId;

    private String username;

    private String photoUrl;

    private String message;

    /* 消息类型:  1：聊天消息  2：进入、退出提示消息 */
    private Integer type;

    /* 区分是否是游客 */
    @JSONField(name = "isVisitor")
    private Boolean isVisitor;

    /* 区分是否需要显示时间 */
    @JSONField(name = "isShowTime")
    private Boolean isShowTime;

    private Long createTime;

    /* 在线用户列表 */
    private List<ChatUser> userList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getIsVisitor() {
        return isVisitor;
    }

    public void setIsVisitor(Boolean visitor) {
        isVisitor = visitor;
    }

    public Boolean getIsShowTime() {
        return isShowTime;
    }

    public void setIsShowTime(Boolean isShowTime) {
        this.isShowTime = isShowTime;
    }

    public List<ChatUser> getUserList() {
        return userList;
    }

    public void setUserList(List<ChatUser> userList) {
        this.userList = userList;
    }
}
