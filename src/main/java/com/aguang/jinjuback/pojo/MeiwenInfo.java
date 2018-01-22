package com.aguang.jinjuback.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 返回页面的美文Info
 */
public class MeiwenInfo implements Serializable {

    /* 主键 */
    private Integer meiwenId;

    /* 类型 */
    private Integer type;

    /* 标题 */
    private String title;

    /* 摘要 */
    private String summary;

    /* 封页图片 */
    private String coverPhoto;

    /* 内容 */
    private String content;

    /* 浏览总数 */
    private Integer browseCount;

    /* 收藏总数 */
    private Integer collectCount;

    /* 评论总数 */
    private Integer commentCount;

    /* 是否收藏 */
    @JSONField(name = "isCollect")
    private boolean isCollect;

    /* 金句创建用户id */
    private Integer userId;

    /* 金句创建用户名 */
    private String username;

    /* 头像 */
    private String photoUrl;

    /* 创建时间 */
    private long createTime;

    /* 更新时间 */
    private long updateTime;

    public Integer getMeiwenId() {
        return meiwenId;
    }

    public void setMeiwenId(Integer meiwenId) {
        this.meiwenId = meiwenId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(Integer browseCount) {
        this.browseCount = browseCount;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
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

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
