package com.aguang.jinjuback.pojo;

import java.io.Serializable;

/**
 * 返回页面的美文评论Info
 */
public class MeiwenCommentInfo implements Serializable {

    // 金句类型
    private Integer id;

    // 美文id
    private Integer meiwenId;

    // 金句内容
    private String content;

    // 金句创建用户id
    private Integer userId;

    // 金句创建用户名
    private String username;

    /* 头像 */
    private String photoUrl;

    // 评论创建时间
    private long createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMeiwenId() {
        return meiwenId;
    }

    public void setMeiwenId(Integer meiwenId) {
        this.meiwenId = meiwenId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
